package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ActivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.DeactivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditSingleEnchantmentStrengthBookListener implements Listener {
	@EventHandler
	public void onPlayerEditSingleEnchantmentStrengthBook(PlayerEditBookEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		ItemStack tempStoredItem = playerMenuUtility.getTempStoredItem();
		if (tempStoredItem == null || !event.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomEnchantmentBookKey())) {
			return;
		}
		BookMeta newBookMeta = event.getNewBookMeta();
		List<String> pages = newBookMeta.getPages();
		event.setCancelled(true);

		String content = pages.getFirst().trim();

		try {
			int amount = Integer.parseInt(content);
			if(amount < 1) {
				tempStoredItem.removeEnchantment(playerMenuUtility.getTargetEnchantment());
			} else {
				tempStoredItem.addUnsafeEnchantment(playerMenuUtility.getTargetEnchantment(), amount);
			}
			endProcess(playerMenuUtility);
		} catch (NumberFormatException e) {
			pl.sendMessage(Main.prefix + "The given format for the enchantment strength is incorrect. Given input was: " + content);
			Bukkit.getLogger().info("Enchantment_Strength book-NFException-" + pl.getName() + ": " + content + " is not an int.");
			Bukkit.getLogger().info("Enchantment_Strength book-NFException-" + pl.getName() + ": " + e.getMessage());
			endProcess(playerMenuUtility);
		}
	}

	private void endProcess(PlayerMenuUtility util) {
		Player pl = util.getOwner();
		pl.getInventory().setItem(util.getStoredSlot(), util.getTempStoredItem());


		if (util.getTempStoredItem().getItemMeta().hasEnchants()) {
			new ActivatedEnchantmentsMenu(util)
				.open();
		} else {
			new DeactivatedEnchantmentsMenu(util)
				.open();
		}

		util.setTempStoredItem(null);
		util.setStoredSlot(-1);
	}
}
