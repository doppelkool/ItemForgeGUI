package de.doppelkool.itemforgegui.Listeners;

import com.google.common.collect.Lists;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuServices.LoreProcessManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ObservableObject;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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

import java.util.Arrays;
import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class LoreBookListeners implements Listener {
	@EventHandler
	public void onPlayerEditLoreBook(PlayerEditBookEvent e) {
		Player pl = e.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		if (!e.getPlayer()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getPersistentDataContainer()
			.has(Main.getPlugin().getCustomLoreEditBookKey())) {
			return;
		}

		String parsedNewLore = parseNewLore(e.getNewBookMeta());
		applyNewLore(playerMenuUtility.getItemInHand(), parsedNewLore);
		replaceBackPreviousItem(playerMenuUtility);
	}

	private String parseNewLore(BookMeta newBookMeta) {
		String content = String.join("\n", newBookMeta.getPages());
		return ChatColor.translateAlternateColorCodes('&', content);
	}

	private void applyNewLore(ObservableObject<ItemStack> itemInHand, String parsedNewLore) {
		ItemStack item = itemInHand.get().clone();
		new ItemInfoManager(item)
			.setItemLore(List.of(parsedNewLore.split("\n")))
			.updateItemInfo();
		itemInHand.set(item);
	}

	private void replaceBackPreviousItem(PlayerMenuUtility playerMenuUtility) {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {

			playerMenuUtility.getAPICallback().ifPresentOrElse((apiCallback) -> {

				ItemStack previousItem = playerMenuUtility.getLoreProcessStorage().getItemToReplaceBack().clone();
				playerMenuUtility.getOwner().getInventory().setItem(playerMenuUtility.getLoreProcessStorage().getSlotToEditLoreOn(), previousItem);
			}, () -> {
				playerMenuUtility.getItemInHand().set(playerMenuUtility.getItemInHand().get().clone());
			});

			playerMenuUtility.setLoreProcessStorage(null);
			new ItemEditMenu(playerMenuUtility)
				.open();

		//Make sure server does finish book editing process and it replacing the book with new contents in hand,
		//so we can replace edited book with the item *after* said process.
		}, 2L);
	}

	@EventHandler
	public void preventItemDrop(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();

		if (itemStack.getType() == Material.WRITABLE_BOOK
			&& PreventionFlagManager.getInstance().isFlagApplied(itemStack, ForgeAction.DROP)) {
			e.setCancelled(true);
			MessageManager.message(e.getPlayer(), Messages.BOOK_EDITOR_LORE_BOOK_DROP_DISALLOWED);
		}
	}
}
