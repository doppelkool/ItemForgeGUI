package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;

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
	protected boolean eventLogic(PrepareAnvilEvent e) {
		//ToDo Split message for every PF possibility
		this.cancelString = MessageManager.format("action-prevented.anvil-usage");

		Inventory anvilInventory = e.getInventory();

		// Get the two input items: slot 0 (left) and slot 1 (right)
		ItemStack leftItem = anvilInventory.getItem(0);
		ItemStack rightItem = anvilInventory.getItem(1);

		// Vanilla: Both items are empty, Nothing to process
		if (leftItem == null && rightItem == null) {
			return false;
		}

		// Vanilla: Only right item is filled, Nothing to process
		if (leftItem == null) {
			return false;
		}

		AnvilListenerHelper.PrepareAnvilResult prepareAnvilResult = AnvilListenerHelper.shouldCancelEvent(
			e.getView().getRenameText(),
			leftItem,
			rightItem);

		if (prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.ALLOW) {
			return false;
		}

		if (prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.DENY_WITHOUT_MSG) {
			this.cancelString = null;
		}

		return true;
	}

	@Override
	protected void customCancelLogic(PrepareAnvilEvent e) {
		e.setResult(null);
		e.getInventory().setItem(2, null);
	}

	@EventHandler
	public void onAnvilResultClick(InventoryClickEvent e) {
		if (!(e.getInventory() instanceof AnvilInventory
			&& e.getView() instanceof AnvilView anvilView)) {
			return;
		}

		// Result item-slot clicked
		if (e.getRawSlot() != 2) {
			return;
		}

		Inventory inv = e.getInventory();
		ItemStack leftItem = inv.getItem(0);
		ItemStack rightItem = inv.getItem(1);
		ItemStack resultItem = e.getCurrentItem();

		if (leftItem == null || resultItem == null) {
			return;
		}

		AnvilListenerHelper.PrepareAnvilResult prepareAnvilResult = AnvilListenerHelper.shouldCancelEvent(
			anvilView.getRenameText(),
			leftItem,
			rightItem);

		if (prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.ALLOW) {
			return;
		}

		if (prepareAnvilResult == AnvilListenerHelper.PrepareAnvilResult.DENY_WITHOUT_MSG) {
			this.cancelString = null;
		}

		resetEvent(e);
	}

	/**
	 * Resets the event by canceling it and removing the result item so that the player cannot retrieve it.
	 */
	public void resetEvent(InventoryClickEvent e) {
		e.setCancelled(true);
		// Remove the result item so the player cannot retrieve it.
		e.getInventory().setItem(2, null);
		e.setCurrentItem(null);
		e.getWhoClicked().setItemOnCursor(null);
	}
}