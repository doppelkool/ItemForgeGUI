package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventItemAlteringListeners implements Listener {

	@EventHandler
	public void preventItemInDisallowedInvs(InventoryClickEvent e) {
		ItemStack currentItem = e.getCurrentItem();

		if (currentItem == null
			|| currentItem.getType() == Material.AIR /*Possible Material*/) {
			return;
		}

		if (!UniqueItemIdentifierManager.isUniqueItem(currentItem.getItemMeta())) {
			return;
		}

		// Single ignored inventorytypes
		if(loadDisabledInventoryTypes(currentItem).contains(e.getClickedInventory().getType())) {
			e.setCancelled(true);
			e.getWhoClicked().sendMessage(Main.prefix + "You are not allowed to do this!");
			return;
		}
	}

	private List<InventoryType> loadDisabledInventoryTypes(ItemStack currentItem) {
		return UniqueItemIdentifierManager.mapNotAllowedInventoryTypes(currentItem);
	}

	@EventHandler
	public void preventPuttingItemInItemFrame(PlayerInteractEvent e) {
		// Check if the player is trying to place item in an item frame
		if (!(e.getClickedBlock() instanceof ItemFrame)
			|| e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		// Check if the player is trying to place a plugins item in an item frame
		ItemStack itemStack = e.getItem();
		if (itemStack == null
			|| itemStack.getType() == Material.AIR //Possible Material
			|| !UniqueItemIdentifierManager.isUniqueItem(itemStack.getItemMeta())) {
			return;
		}

		if (UniqueItemIdentifierManager.isActionPrevented(itemStack, ForgeAction.ITEM_FRAME_PLACE)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
		}
	}

	@EventHandler
	public void preventItemDrop(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();

		if (!UniqueItemIdentifierManager.isUniqueItem(itemStack.getItemMeta())) {
			return;
		}

		if (UniqueItemIdentifierManager.isActionPrevented(itemStack, ForgeAction.ITEM_DROP)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
		}
	}
}