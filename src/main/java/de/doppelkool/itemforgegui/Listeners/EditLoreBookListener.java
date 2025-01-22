package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
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
	public void onPlayerEditBook(PlayerEditBookEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		ItemStack tempStoredItem = playerMenuUtility.getTempStoredItem();
		if(tempStoredItem == null) {
			return;
		}

		BookMeta newBookMeta = event.getNewBookMeta();
		String content = String.join("\n", newBookMeta.getPages());
		String formattedString = ChatColor.translateAlternateColorCodes('&', content);

		playerMenuUtility.setTempStoredItem(null);
		playerMenuUtility.setStoredSlot(-1);

		if (!event.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomLoreEditBookKey())) {
			return;
		}

		ItemMeta itemMeta = tempStoredItem.getItemMeta();
		itemMeta.setLore(
			List.of(formattedString.split("\n"))
		);
		tempStoredItem.setItemMeta(itemMeta);
		pl.getInventory().setItemInMainHand(tempStoredItem);

		new ItemEditMenu(playerMenuUtility)
			.open();
	}
}
