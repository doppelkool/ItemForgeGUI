package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
		Inventory anvilInventory = event.getInventory();

		// Get the two input items: slot 0 (left) and slot 1 (right)
		ItemStack leftItem = anvilInventory.getItem(0);
		ItemStack rightItem = anvilInventory.getItem(1);

		// If both inputs are null, nothing to process.
		if (leftItem == null && rightItem == null) {
			return false;
		}

		// Check for renaming via display names on the items.
		if (shouldCancelRename(leftItem, rightItem)) {
			return true;
		}

		// Combine prevention flags for enchant and repair operations.
		boolean preventEnchantAny =
			DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.ENCHANT)
				|| DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.ENCHANT);
		boolean preventRepairAny =
			DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.REPAIR)
				|| DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.REPAIR);

		// If neither prevention is active, allow the operation.
		if (!preventEnchantAny && !preventRepairAny) {
			return false;
		}

		// Determine if either input is enchanted (indicating an enchanting operation).
		boolean leftEnchanted = leftItem != null && !leftItem.getEnchantments().isEmpty();
		boolean rightEnchanted = rightItem != null && !rightItem.getEnchantments().isEmpty();
		boolean isEnchant = leftEnchanted || rightEnchanted;

		// Determine if this is a repair operation: both inputs exist and are of the same type.
		boolean isRepair = leftItem != null && rightItem != null && leftItem.getType() == rightItem.getType();

		/*
		 * Prevention logic:
		 * 1. If the operation is clearly an enchantment (non-repair) and the enchant prevention flag is active, cancel.
		 * 2. If the operation is clearly a repair (non-enchant) and the repair prevention flag is active, cancel.
		 * 3. If the operation is ambiguous (both or neither clear), cancel the operation.
		 */
		if ((isEnchant && !isRepair && preventEnchantAny) ||
			(isRepair && !isEnchant && preventRepairAny) ||
			((isEnchant && isRepair) || (!isEnchant && !isRepair))) {

			// Setting the result does not work reliably, so we manually clear the result slot.
			return true;
		}

		return false;
	}

	@Override
	protected void customCancelLogic(PrepareAnvilEvent event) {
		event.setResult(null);
		event.getInventory().setItem(2, null);
		event.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	@EventHandler
	public void onAnvilResultClick(InventoryClickEvent event) {
		// Ensure the inventory is an anvil (or you could also check using InventoryType.ANVIL)
		if (!(event.getInventory() instanceof AnvilInventory)) {
			return;
		}

		// Check if the player clicked on the anvil's upper inventory and specifically the result slot (slot 2)
		if (event.getRawSlot() != 2) {
			return;
		}

		// Retrieve the left input item (slot 0) and the result item (slot 2)
		ItemStack leftItem = event.getInventory().getItem(0);
		ItemStack resultItem = event.getCurrentItem();
		if (leftItem == null || resultItem == null) {
			return;
		}

		// Retrieve the right input item from slot 1 (may be null)
		ItemStack rightItem = event.getInventory().getItem(1);

		// First, check for a renaming operation.
		if (shouldCancelRename(leftItem, rightItem)) {
			resetEvent(event);
			return;
		}

		// Combine prevention flags for enchant and repair operations.
		boolean preventEnchantAny = DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.ENCHANT)
			|| (DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.ENCHANT));
		boolean preventRepairAny = DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.REPAIR)
			|| (DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.REPAIR));

		// If neither prevention is active, allow the operation.
		if (!preventEnchantAny && !preventRepairAny) {
			return;
		}

		// Determine if either input is enchanted (indicating an enchanting operation).
		boolean leftEnchanted = !leftItem.getEnchantments().isEmpty();
		boolean rightEnchanted = rightItem != null && !rightItem.getEnchantments().isEmpty();
		boolean isEnchant = leftEnchanted || rightEnchanted;

		// Determine if this is a repair operation: both inputs exist and are of the same type.
		boolean isRepair = rightItem != null && leftItem.getType() == rightItem.getType();

		/*
		 * Prevention logic:
		 * 1. If the operation is clearly an enchantment (non-repair) and the enchant prevention flag is active, cancel.
		 * 2. If the operation is clearly a repair (non-enchant) and the repair prevention flag is active, cancel.
		 * 3. If the operation is ambiguous (both or neither clear), cancel the operation.
		 */
		if ((isEnchant && !isRepair && preventEnchantAny) ||
			(isRepair && !isEnchant && preventRepairAny) ||
			((isEnchant && isRepair) || (!isEnchant && !isRepair))) {
			resetEvent(event);
			return;
		}
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

		Player player = (Player) event.getWhoClicked();
		player.sendMessage(Main.prefix + "You are not allowed to craft that item.");
	}

	/**
	 * Checks whether a rename operation should be canceled.
	 * <p>
	 * It compares the display names of the left and right items. If the left item is using Minecraft’s default
	 * (i.e. no custom display name) and the right item has a custom name, or if both items have custom names
	 * that differ, then a rename is detected. It then checks if renaming is disallowed.
	 * </p>
	 *
	 * @param leftItem  the item in slot 0 (original item)
	 * @param rightItem the item in slot 1 (input for the rename operation)
	 * @return true if the rename is detected and should be canceled; false otherwise.
	 */
	private boolean shouldCancelRename(ItemStack leftItem, ItemStack rightItem) {
		String leftDisplay = null;
		if (leftItem != null && leftItem.getItemMeta() != null && leftItem.getItemMeta().hasDisplayName()) {
			leftDisplay = leftItem.getItemMeta().getDisplayName();
		}
		String rightDisplay = null;
		if (rightItem != null && rightItem.getItemMeta() != null && rightItem.getItemMeta().hasDisplayName()) {
			rightDisplay = rightItem.getItemMeta().getDisplayName();
		}

		boolean isRename = false;
		// If the left item has no custom display name but the right does, consider it a rename.
		if (leftDisplay == null && rightDisplay != null) {
			isRename = true;
		}
		// If both items have a custom name, and they differ, consider it a rename.
		else if (leftDisplay != null && rightDisplay != null && !leftDisplay.equals(rightDisplay)) {
			isRename = true;
		}

		// Check if renaming is disallowed via your DisallowedActionsManager.
		boolean preventRename = DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.RENAME)
			|| DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.RENAME);

		return isRename && preventRename;
	}
}