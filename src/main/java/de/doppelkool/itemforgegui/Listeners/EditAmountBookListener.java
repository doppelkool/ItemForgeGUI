package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.AmountMenu;
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
public class EditAmountBookListener implements Listener {
	@EventHandler
	public void onPlayerEditAmountBook(PlayerEditBookEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		ItemStack tempStoredItem = playerMenuUtility.getTempStoredItem();
		if (tempStoredItem == null || !event.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomAmountBookKey())) {
			return;
		}
		BookMeta newBookMeta = event.getNewBookMeta();
		List<String> pages = newBookMeta.getPages();
		event.setCancelled(true);

		String content = pages.getFirst().trim();

		try {
			int amount = Integer.max(Integer.parseInt(content), 1);
			tempStoredItem.setAmount(amount);
			endProcess(playerMenuUtility);
		} catch (NumberFormatException e) {
			pl.sendMessage(Main.prefix + "The given format for the items amount is incorrect. Given input was: " + content);
			Bukkit.getLogger().info("Amount book-NFException-" + pl.getName() + ": " + content + " is not an int.");
			Bukkit.getLogger().info("Amount book-NFException-" + pl.getName() + ": " + e.getMessage());
			endProcess(playerMenuUtility);
		}
	}

	private void endProcess(PlayerMenuUtility util) {
		Player pl = util.getOwner();
		pl.getInventory().setItem(util.getStoredSlot(), util.getTempStoredItem());

		util.setTempStoredItem(null);
		util.setStoredSlot(-1);

		new AmountMenu(util)
			.open();
	}
}
