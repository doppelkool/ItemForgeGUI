package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventAlteringListeners implements Listener {

	@EventHandler
	public void preventItemInDisallowedInvs(InventoryClickEvent e) {
		ItemStack currentItem = e.getCurrentItem();

		if (currentItem == null
			|| currentItem.getType() == Material.AIR /*Possible Material*/) {
			return;
		}

		if (!UniqueItemIdentifierManager.isUniqueItem(currentItem)) {
			return;
		}

		// Single ignored inventorytypes
		if (!loadDisabledInventoryTypes(currentItem).contains(e.getClickedInventory().getType())) {
			return;
		}

		e.setCancelled(true);
		e.getWhoClicked().sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	private List<InventoryType> loadDisabledInventoryTypes(ItemStack currentItem) {
		return DisallowedActionsManager.mapNotAllowedInventoryTypes(currentItem);
	}

	@EventHandler
	public void preventItemDrop(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();

		if (!UniqueItemIdentifierManager.isUniqueItem(itemStack)) {
			return;
		}

		if (!DisallowedActionsManager.isActionPrevented(itemStack, ForgeAction.DROP)) {
			return;
		}

		e.setCancelled(true);
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}
}