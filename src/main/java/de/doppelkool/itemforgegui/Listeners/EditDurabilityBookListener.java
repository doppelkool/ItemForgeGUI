package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.DurabilityMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditDurabilityBookListener implements Listener {
	@EventHandler
	public void onPlayerEditDurabilityBook(PlayerEditBookEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		ItemStack tempStoredItem = playerMenuUtility.getTempStoredItem();
		if (tempStoredItem == null || !event.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomDurabilityBookKey())) {
			return;
		}

		BookMeta newBookMeta = event.getNewBookMeta();
		List<String> pages = newBookMeta.getPages();
		event.setCancelled(true);

		String[] content = pages.getFirst().trim().split("/");

		try {
			int ofMax = Integer.parseInt(content[0]);
			short maxDurability = tempStoredItem.getType().getMaxDurability();

			if(ofMax > maxDurability) {
				ofMax = maxDurability;
			}

			Damageable itemMeta = (Damageable)tempStoredItem.getItemMeta();
			itemMeta.setDamage(maxDurability - ofMax);
			tempStoredItem.setItemMeta(itemMeta);
			endProcess(playerMenuUtility);
		} catch (NumberFormatException e) {
			pl.sendMessage(Main.prefix + "The given format for the items durability is incorrect. Given input was: " + content[0]);
			Bukkit.getLogger().info("Durability book-NFException-" + pl.getName() + ": " + content[0] + " is not an int.");
			Bukkit.getLogger().info("Durability book-NFException-" + pl.getName() + ": " + e.getMessage());
			endProcess(playerMenuUtility);
		}
	}

	private void endProcess(PlayerMenuUtility util) {
		Player pl = util.getOwner();
		pl.getInventory().setItem(util.getStoredSlot(), util.getTempStoredItem());

		util.setTempStoredItem(null);
		util.setStoredSlot(-1);

		new DurabilityMenu(util)
			.open();
	}
}