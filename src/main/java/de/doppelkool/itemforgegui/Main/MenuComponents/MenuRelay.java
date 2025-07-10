package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
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

		/*
		 * Event discontinued if:
		 * - Item is FILLER_GLASS
		 * - Item has NOT_AVAILABLE as tag in its data container
		 * - Clicked Inventory is the inventory a player sees with another open Inventory
		 * - Event is caused by double click
		 * */
		return
			!(item.equals(GlobalItems.FILLER_GLASS)
				|| (item.hasItemMeta() &&
				item.getItemMeta().getPersistentDataContainer().has(Main.getPlugin().getCustomNotAvailableStackIDKey()))
				|| e.getClickedInventory().getType() == InventoryType.PLAYER
				|| e.getClick() == ClickType.DOUBLE_CLICK
			);
	}
}
