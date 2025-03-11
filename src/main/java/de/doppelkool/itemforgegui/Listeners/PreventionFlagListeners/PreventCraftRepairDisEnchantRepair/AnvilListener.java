package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;

import java.util.Collections;
import java.util.HashSet;
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

		// Vanilla: Both items are empty, Nothing to process
		if (leftItem == null && rightItem == null) {
			return false;
		}

		// Vanilla: Only right item is filled, Nothing to process
		if(leftItem == null) {
			return false;
		}

		return shouldCancelEvent(event.getView().getRenameText(), leftItem, rightItem);
	}

	public boolean shouldCancelEvent(String renameText, ItemStack leftItem, ItemStack rightItem) {
		// Use your custom renaming method to determine if the player is actually changing the name.
		boolean isRenamingAttempt = isRenaming(leftItem, renameText, rightItem);

		// Check if the left item is repairable and damaged.
		boolean leftItemRepairableAndDamaged = isItemRepairableAndDamaged(leftItem);

		if (rightItem != null) {
			// Branch 1: Repairing Attempt.
			// A repair attempt is detected if the right item is either:
			//    a) the matching repair ingot (per your helper), or
			//    b) of the same type as the left item (typical combine repair)
			// AND the left item is damageable and actually damaged.
			boolean isRepairAttempt = (isMatchingRepairIngot(leftItem, rightItem)
				|| (rightItem.getType() == leftItem.getType()))
				&& leftItemRepairableAndDamaged;
			if (isRepairAttempt) {
				// Only run our prevention logic if vanilla would allow this repair operation.
				if (isVanillaRepairable(leftItem, rightItem)) {
					// If a renaming attempt is happening during repair, then also check rename prevention.
					if (isRenamingAttempt && PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)) {
						return true;
					}
					if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.REPAIR)) {
						return true;
					}
				}
				// If vanilla wouldn’t allow it, let vanilla handle it.
				return false;
			}
			// Branch 2: Enchanting Attempt.
			// This branch is triggered when the right item is an enchanted book.
			else if (rightItem.getType() == Material.ENCHANTED_BOOK) {
				// IMPORTANT CHANGE:
				// If the left item is not normally enchantable by vanilla, cancel the event.
				if (!isVanillaEnchantable(leftItem)) {
					this.cancelString = null;
					return true;
				}
				// Otherwise, if it is vanilla-enchantable, proceed with our prevention checks.
				if (isRenamingAttempt) {
					if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME) ||
						PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.ENCHANT) ||
						PreventionFlagManager.isActionPrevented(rightItem, ForgeAction.ENCHANT)) {
						return true;
					}
				} else {
					if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.ENCHANT) ||
						PreventionFlagManager.isActionPrevented(rightItem, ForgeAction.ENCHANT)) {
						return true;
					}
				}
			}
			// Branch 3: Renaming Attempt (with two items) for non-enchanted book cases.
			else if (isRenamingAttempt) {
				if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME) ||
					PreventionFlagManager.isActionPrevented(rightItem, ForgeAction.RENAME)) {
					return true;
				}
			}
			// In any other case, allow the event.
		} else {
			// Single-item scenario: Pure renaming.
			if (isRenamingAttempt && PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Helper method to check if the item is damageable and currently damaged.
	 * Uses the Spigot 1.21.4 API (Damageable interface via ItemMeta).
	 */
	private boolean isItemRepairableAndDamaged(ItemStack item) {
		if (item == null || !item.hasItemMeta()) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		if (!(meta instanceof Damageable)) {
			return false;
		}
		Damageable damageable = (Damageable) meta;
		return damageable.getDamage() > 0;
	}

	/**
	 * Checks if the left item is normally enchantable by vanilla.
	 * Instead of a large switch statement, this method uses the keySet of the REPAIR_INGOT_MAP.
	 * It then excludes BOW, CROSSBOW, and BOOK.
	 */
	private boolean isVanillaEnchantable(ItemStack item) {
		if (item == null) return false;
		return VANILLA_ENCHANTABLE_MATERIALS.contains(item.getType());
	}

	/**
	 * Checks if the combination of left and right items is a valid repair operation according to vanilla.
	 * Typically, vanilla allows repair on damageable items if the right item is either the appropriate repair material
	 * (determined by isMatchingRepairIngot) or another instance of the same item type.
	 */
	private boolean isVanillaRepairable(ItemStack leftItem, ItemStack rightItem) {
		if (leftItem == null) return false;
		// Only damageable items can be repaired.
		if (!isItemRepairableAndDamaged(leftItem)) return false;
		// For vanilla repair, the right item must either be the correct repair material or the same type.
		return isMatchingRepairIngot(leftItem, rightItem) || (rightItem.getType() == leftItem.getType());
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

	private static final Set<Material> VANILLA_ENCHANTABLE_MATERIALS;
	static {
		Set<Material> set = new HashSet<>(REPAIR_INGOT_MAP.keySet());
		set.add(Material.BOW);
		set.add(Material.CROSSBOW);
		set.add(Material.BOOK);
		set.add(Material.ENCHANTED_BOOK);
		VANILLA_ENCHANTABLE_MATERIALS = Collections.unmodifiableSet(set);
	}

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
		if (!(event.getInventory() instanceof AnvilInventory
			&& event.getView() instanceof AnvilView anvilView)) {
			return;
		}

		// Check if the player clicked on the anvil's upper inventory and specifically the result slot (slot 2)
		if (event.getRawSlot() != 2) {
			return;
		}

		Inventory inv = event.getInventory();
		ItemStack leftItem = inv.getItem(0);
		ItemStack rightItem = inv.getItem(1);
		String renameText = anvilView.getRenameText();

		ItemStack resultItem = event.getCurrentItem();
		if (leftItem == null || resultItem == null) {
			return;
		}

		if (shouldCancelEvent(renameText, leftItem, rightItem)) {
			resetEvent(event);
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
	 * Determines if the player is performing a renaming action.
	 * It compares the new name (renameText) to the current display name of the left item (or right item if left is null).
	 * If the names differ, then the player has changed the name.
	 */
	private boolean isRenaming(ItemStack leftItem, String renameText, ItemStack rightItem) {
		String currentName = (leftItem != null && leftItem.hasItemMeta() && leftItem.getItemMeta().hasDisplayName())
			? leftItem.getItemMeta().getDisplayName()
			: (rightItem != null && rightItem.hasItemMeta() && rightItem.getItemMeta().hasDisplayName())
			? rightItem.getItemMeta().getDisplayName()
			: "";
		return !currentName.equals(renameText);
	}
}