package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class LoreBookListeners implements Listener {
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

		new ItemInfoManager(tempStoredItem)
			.setItemLore(List.of(formattedString.split("\n")))
			.updateItemInfo();
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

	@EventHandler
	public void preventItemDrop(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();

		if (itemStack.getType() == Material.WRITABLE_BOOK
			&& PreventionFlagManager.isActionPrevented(itemStack, ForgeAction.DROP)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
		}
	}
}
