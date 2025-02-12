package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditLoreBookListener implements Listener {
	@EventHandler
	public void onPlayerEditLoreBook(PlayerEditBookEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		ItemStack tempStoredItem = playerMenuUtility.getTempStoredItem();
		if (tempStoredItem == null || !event.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomLoreEditBookKey())) {
			return;
		}

		BookMeta newBookMeta = event.getNewBookMeta();
		String content = String.join("\n", newBookMeta.getPages());
		String formattedString = ChatColor.translateAlternateColorCodes('&', content);

		ItemMeta itemMeta = tempStoredItem.getItemMeta();
		itemMeta.setLore(
			List.of(formattedString.split("\n"))
		);
		tempStoredItem.setItemMeta(itemMeta);
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		Player pl = util.getOwner();

		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
			pl.getInventory().setItem(util.getStoredSlot(), util.getTempStoredItem());

			util.setTempStoredItem(null);
			util.setStoredSlot(-1);

			new ItemEditMenu(util)
				.open();
		//Make sure server does finish book editing process and replacing it with the current slot before,
		//so we can set the item *after* that process.
		}, 2L);
	}
}
