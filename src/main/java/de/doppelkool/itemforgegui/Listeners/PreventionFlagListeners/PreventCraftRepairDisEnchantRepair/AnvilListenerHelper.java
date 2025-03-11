package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AnvilListenerHelper {

	public static PrepareAnvilResult shouldCancelEvent(String renameText, ItemStack leftItem, ItemStack rightItem) {
		boolean isRenamingAttempt = isRenaming(leftItem, renameText, rightItem);
		boolean leftItemRepairableAndDamaged = isItemRepairableAndDamaged(leftItem);

		if (rightItem == null) {
			// == One input item (aka. renaming action) ==

			return isRenamingAttempt && PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)
				? PrepareAnvilResult.DENY
				: PrepareAnvilResult.ALLOW;
		}

		// == Two input items ==

		// Branch 1: Repairing Attempt.
		if (isRepairAttempt(leftItem, rightItem, leftItemRepairableAndDamaged)) {
			return checkRepairPrevention(leftItem, rightItem, isRenamingAttempt);
		}
		// Branch 2: Enchanting Attempt.
		else if (isEnchantAttempt(rightItem)) {
			return checkEnchantPrevention(leftItem, isRenamingAttempt);
		}
		// Branch 3: Renaming Attempt (for two-item cases).
		else if (isRenamingAttempt) {
			return checkRenamingPrevention(leftItem, rightItem);
		}

		return PrepareAnvilResult.ALLOW;
	}

	/* --- Helper Methods --- */


	/**
	 * Determines if a renaming operation is taking place.
	 * Compares the current display name of the left item (or right if left is absent) with the new rename text.
	 */
	private static boolean isRenaming(ItemStack leftItem, String renameText, ItemStack rightItem) {
		String currentName = "";
		if (leftItem != null && leftItem.hasItemMeta() && leftItem.getItemMeta().hasDisplayName()) {
			currentName = leftItem.getItemMeta().getDisplayName();
		} else if (rightItem != null && rightItem.hasItemMeta() && rightItem.getItemMeta().hasDisplayName()) {
			currentName = rightItem.getItemMeta().getDisplayName();
		}
		return !currentName.equals(renameText);
	}

	/**
	 * Determines if an item is damageable and currently damaged.
	 */
	private static boolean isItemRepairableAndDamaged(ItemStack item) {
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
	 * Determines whether this is a repair attempt.
	 * A repair attempt occurs if the right item is either the matching repair ingot or of the same type as the left item,
	 * and the left item is both damageable and actually damaged.
	 */
	private static boolean isRepairAttempt(ItemStack leftItem, ItemStack rightItem, boolean leftItemRepairableAndDamaged) {
		return (isMatchingRepairIngot(leftItem, rightItem) || (rightItem.getType() == leftItem.getType()))
			&& leftItemRepairableAndDamaged;
	}

	/**
	 * Checks prevention logic for repair operations.
	 * This method only applies if vanilla would allow the repair. It then checks if a renaming attempt is present
	 * and whether the left item is prevented from renaming or repairing.
	 */
	private static PrepareAnvilResult checkRepairPrevention(ItemStack leftItem, ItemStack rightItem, boolean isRenamingAttempt) {
		if (isVanillaRepairable(leftItem, rightItem)) {
			if (isRenamingAttempt && PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)) {
				return PrepareAnvilResult.DENY;
			}
			if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.REPAIR)) {
				return PrepareAnvilResult.DENY;
			}
		}
		// If vanilla wouldn’t allow repair, we let vanilla handle it (i.e. do not cancel here).
		return PrepareAnvilResult.ALLOW;
	}

	/**
	 * Checks if the combination of left and right items is a valid repair operation according to vanilla.
	 */
	private static boolean isVanillaRepairable(ItemStack leftItem, ItemStack rightItem) {
		if (leftItem == null) return false;
		if (!isItemRepairableAndDamaged(leftItem)) return false;
		return isMatchingRepairIngot(leftItem, rightItem) || (rightItem.getType() == leftItem.getType());
	}

	/**
	 * Determines whether the operation is an enchanting attempt.
	 * In our logic, an enchanting attempt is when the right item is an enchanted book.
	 */
	private static boolean isEnchantAttempt(ItemStack rightItem) {
		return rightItem.getType() == Material.ENCHANTED_BOOK;
	}

	/**
	 * Checks prevention logic for enchanting operations.
	 * If the left item is not normally enchantable by vanilla, cancel the event.
	 * Otherwise, only the left item's prevention flags are checked because the enchanted book is consumed.
	 */
	private static PrepareAnvilResult checkEnchantPrevention(ItemStack leftItem, boolean isRenamingAttempt) {
		if (!isVanillaEnchantable(leftItem)) {
			// In this case, we cancel the event so that impossible actions (e.g. enchanting an ingot) are blocked.
			return PrepareAnvilResult.DENY_WITHOUT_MSG;
		}
		if (isRenamingAttempt) {
			if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)
				|| PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.ENCHANT)) {
				return PrepareAnvilResult.DENY;
			}
		} else {
			if (PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.ENCHANT)) {
				return PrepareAnvilResult.DENY;
			}
		}
		return PrepareAnvilResult.ALLOW;
	}

	/**
	 * Checks if the left item is normally enchantable by vanilla.
	 * This method uses the VANILLA_ENCHANTABLE_MATERIALS set.
	 */
	private static boolean isVanillaEnchantable(ItemStack item) {
		if (item == null) return false;
		return VANILLA_ENCHANTABLE_MATERIALS.contains(item.getType());
	}

	/**
	 * Checks prevention logic for renaming operations when two items are involved.
	 */
	private static PrepareAnvilResult checkRenamingPrevention(ItemStack leftItem, ItemStack rightItem) {
		boolean renamePreventionActiveOnBothItems =
				PreventionFlagManager.isActionPrevented(leftItem, ForgeAction.RENAME)
					|| PreventionFlagManager.isActionPrevented(rightItem, ForgeAction.RENAME);

		return renamePreventionActiveOnBothItems
			? PrepareAnvilResult.DENY
			: PrepareAnvilResult.ALLOW;
	}

	/**
	 * Determines whether the right item is the matching repair ingot for the left item.
	 */
	private static boolean isMatchingRepairIngot(ItemStack left, ItemStack right) {
		Material leftType = left.getType();
		Material rightType = right.getType();
		// Special case for wooden items:
		if (leftType.name().startsWith("WOODEN_")) {
			return WOODEN_PLANKS.contains(rightType);
		}
		Material requiredIngot = REPAIR_INGOT_MAP.get(leftType);
		return requiredIngot != null && rightType == requiredIngot;
	}

	/* --- Constant Data --- */

	// Mapping of repairable items to their corresponding repair ingot/material.
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

	// Set of wooden planks used as repair material for wooden items.
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

	// Set of materials that are considered vanilla-enchantable.
	private static final Set<Material> VANILLA_ENCHANTABLE_MATERIALS;
	static {
		Set<Material> set = new HashSet<>(REPAIR_INGOT_MAP.keySet());
		set.remove(Material.BOW);
		set.remove(Material.CROSSBOW);
		set.remove(Material.BOOK);
		VANILLA_ENCHANTABLE_MATERIALS = Collections.unmodifiableSet(set);
	}

	public enum PrepareAnvilResult {
		ALLOW,
		DENY,
		DENY_WITHOUT_MSG,

		;
	}
}
