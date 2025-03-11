package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AnvilListener extends DuplicateEventManager<PrepareAnvilEvent> implements Listener {

	@EventHandler
	public void onPrepareAnvil(PrepareAnvilEvent e) {
		duplicateExecutionSafeProcess(e.getView().getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareAnvilEvent event) {
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		Inventory anvilInventory = event.getInventory();

		// Get the two input items: slot 0 (left) and slot 1 (right)
		ItemStack leftItem = anvilInventory.getItem(0);
		ItemStack rightItem = anvilInventory.getItem(1);

		// Vanilla: Both items are empty, Nothing to process
		if (leftItem == null && rightItem == null) {
			return false;
		}

		// Vanilla: Only right item is filled, Nothing to process
		if(leftItem == null) {
			return false;
		}

		AnvilListenerHelper.PrepareAnvilResult prepareAnvilResult = AnvilListenerHelper.shouldCancelEvent(
			event.getView().getRenameText(),
			leftItem,
			rightItem);

		if(prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.ALLOW) {
			return false;
		}

		if(prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.DENY_WITHOUT_MSG) {
			this.cancelString = null;
		}

		return true;
	}

	@Override
	protected void customCancelLogic(PrepareAnvilEvent event) {
		event.setResult(null);
		event.getInventory().setItem(2, null);
	}

	@EventHandler
	public void onAnvilResultClick(InventoryClickEvent event) {
		if (!(event.getInventory() instanceof AnvilInventory
			&& event.getView() instanceof AnvilView anvilView)) {
			return;
		}

		// Result item-slot clicked
		if (event.getRawSlot() != 2) {
			return;
		}

		Inventory inv = event.getInventory();
		ItemStack leftItem = inv.getItem(0);
		ItemStack rightItem = inv.getItem(1);
		ItemStack resultItem = event.getCurrentItem();

		if (leftItem == null || resultItem == null) {
			return;
		}

		AnvilListenerHelper.PrepareAnvilResult prepareAnvilResult = AnvilListenerHelper.shouldCancelEvent(
			anvilView.getRenameText(),
			leftItem,
			rightItem);

		if(prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.ALLOW) {
			return;
		}

		if(prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.DENY_WITHOUT_MSG) {
			this.cancelString = null;
		}

		resetEvent(event);
	}

	/**
	 * Resets the event by canceling it and removing the result item so that the player cannot retrieve it.
	 */
	public void resetEvent(InventoryClickEvent event) {
		event.setCancelled(true);
		// Remove the result item so the player cannot retrieve it.
		event.getInventory().setItem(2, null);
		event.setCurrentItem(null);
		event.getWhoClicked().setItemOnCursor(null);
	}
}