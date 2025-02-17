package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Map;
import java.util.Set;

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
		boolean isRepair = false;
		if (leftItem != null && rightItem != null) {
			if (leftItem.getType() == rightItem.getType()) {
				// Standard repair combining two of the same item.
				isRepair = true;
			} else {
				// Check if the right item is a valid repair material (ingot, membrane, etc.)
				if (isMatchingRepairIngot(leftItem, rightItem)) {
					isRepair = true;
				}
			}
		}

		/*
		 * Prevention logic:
		 * 1. If the operation is clearly an enchantment (non-repair) and the enchant prevention flag is active, cancel.
		 * 2. If the operation is clearly a repair (non-enchant) and the repair prevention flag is active, cancel.
		 * 3. If the operation is ambiguous (both or neither clear), cancel the operation.
		 */
		if ((isEnchant && !isRepair && preventEnchantAny) ||
			(isRepair && !isEnchant && preventRepairAny) ||
			((isEnchant && isRepair) || (!isEnchant && !isRepair))) {

			return true;
		}

		return false;
	}

	private static final Set<Material> WOODEN_PLANKS = Set.of(
		Material.OAK_PLANKS,
		Material.SPRUCE_PLANKS,
		Material.BIRCH_PLANKS,
		Material.JUNGLE_PLANKS,
		Material.ACACIA_PLANKS,
		Material.CHERRY_PLANKS,
		Material.DARK_OAK_PLANKS,
		Material.PALE_OAK_PLANKS,
		Material.MANGROVE_PLANKS,
		Material.BAMBOO_PLANKS,
		Material.CRIMSON_PLANKS,
		Material.WARPED_PLANKS
	);

	private static final Map<Material, Material> REPAIR_INGOT_MAP = Map.ofEntries(
		// Netherite tools and armor
		Map.entry(Material.NETHERITE_SWORD, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_AXE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_PICKAXE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_SHOVEL, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_HOE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_HELMET, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_CHESTPLATE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_LEGGINGS, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_BOOTS, Material.NETHERITE_INGOT),

		// Diamond tools and armor
		Map.entry(Material.DIAMOND_SWORD, Material.DIAMOND),
		Map.entry(Material.DIAMOND_AXE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_PICKAXE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_SHOVEL, Material.DIAMOND),
		Map.entry(Material.DIAMOND_HOE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_HELMET, Material.DIAMOND),
		Map.entry(Material.DIAMOND_CHESTPLATE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_LEGGINGS, Material.DIAMOND),
		Map.entry(Material.DIAMOND_BOOTS, Material.DIAMOND),

		// Golden tools and armor
		Map.entry(Material.GOLDEN_SWORD, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_AXE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_PICKAXE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_SHOVEL, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_HOE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_HELMET, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_CHESTPLATE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_LEGGINGS, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_BOOTS, Material.GOLD_INGOT),

		// Iron tools and armor
		Map.entry(Material.IRON_SWORD, Material.IRON_INGOT),
		Map.entry(Material.IRON_AXE, Material.IRON_INGOT),
		Map.entry(Material.IRON_PICKAXE, Material.IRON_INGOT),
		Map.entry(Material.IRON_SHOVEL, Material.IRON_INGOT),
		Map.entry(Material.IRON_HOE, Material.IRON_INGOT),
		Map.entry(Material.IRON_HELMET, Material.IRON_INGOT),
		Map.entry(Material.IRON_CHESTPLATE, Material.IRON_INGOT),
		Map.entry(Material.IRON_LEGGINGS, Material.IRON_INGOT),
		Map.entry(Material.IRON_BOOTS, Material.IRON_INGOT),

		// Stone tools (using cobblestone)
		Map.entry(Material.STONE_SWORD, Material.COBBLESTONE),
		Map.entry(Material.STONE_AXE, Material.COBBLESTONE),
		Map.entry(Material.STONE_PICKAXE, Material.COBBLESTONE),
		Map.entry(Material.STONE_SHOVEL, Material.COBBLESTONE),
		Map.entry(Material.STONE_HOE, Material.COBBLESTONE),

		// Leather armor (using leather)
		Map.entry(Material.LEATHER_HELMET, Material.LEATHER),
		Map.entry(Material.LEATHER_CHESTPLATE, Material.LEATHER),
		Map.entry(Material.LEATHER_LEGGINGS, Material.LEATHER),
		Map.entry(Material.LEATHER_BOOTS, Material.LEATHER),

		// Chainmail armor (using iron)
		Map.entry(Material.CHAINMAIL_HELMET, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_CHESTPLATE, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_LEGGINGS, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_BOOTS, Material.IRON_INGOT),

		// Elytra repair (using phantom membrane)
		Map.entry(Material.ELYTRA, Material.PHANTOM_MEMBRANE),
		Map.entry(Material.TURTLE_HELMET, Material.TURTLE_SCUTE)
	);

	private boolean isMatchingRepairIngot(ItemStack left, ItemStack right) {
		Material leftType = left.getType();
		Material rightType = right.getType();

		if (leftType.name().startsWith("WOODEN_")) {
			return WOODEN_PLANKS.contains(rightType);
		}

		// Look up the required ingot/material for repair
		Material requiredIngot = REPAIR_INGOT_MAP.get(leftType);
		return requiredIngot != null && rightType == requiredIngot;
	}


	@Override
	protected void customCancelLogic(PrepareAnvilEvent event) {
		event.setResult(null);
		event.getInventory().setItem(2, null);
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
		boolean isRepair = false;
		if (leftItem != null && rightItem != null) {
			if (leftItem.getType() == rightItem.getType()) {
				// Standard repair combining two of the same item.
				isRepair = true;
			} else {
				// Check if the right item is a valid repair material (ingot, membrane, etc.)
				if (isMatchingRepairIngot(leftItem, rightItem)) {
					isRepair = true;
				}
			}
		}

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