package de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * A relay interface to couple all custom behaviour on called events
 *
 * @author doppelkool | github.com/doppelkool
 */
public interface MenuRelay {

	/**
	 * @return boolean -> Whether the event is continued and passed down to Menus
	 */
	default boolean shouldForwardClickEvent(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();

		if (item == null) {
			return false;
		}

		boolean isAPICallBackEvent = MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked())
			.getAPICallback()
			.isPresent();
		boolean isSpecificSlot = ConfigManager.getInstance().getApiCallbackItemSlot() == e.getSlot();

		/*
		 * Event discontinued if:
		 * - Item is FILLER_GLASS
		 * - Item has NOT_AVAILABLE as tag in its data container
		 * - Clicked Inventory is the inventory a player sees with another open Inventory
		 * - Event is caused by double click
		 * - API Callback is present (so if the menu was opened via API) AND the slot, in which the item-to-be-edited is placed, was clicked
		 * */
		return
			!(item.equals(GlobalItems.FILLER_GLASS)
				|| (item.hasItemMeta() &&
				item.getItemMeta().getPersistentDataContainer().has(Main.getPlugin().getCustomNotAvailableStackIDKey()))
				|| e.getClickedInventory().getType() == InventoryType.PLAYER
				|| e.getClick() == ClickType.DOUBLE_CLICK
				|| (isAPICallBackEvent && isSpecificSlot)
			);
	}
}
