package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Resources;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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
		if (!Resources.VANILLA_ENCHANTABLE_MATERIALS.contains(leftItem.getType())) {
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
			return Resources.WOODEN_PLANKS.contains(rightType);
		}
		Material requiredIngot = Resources.REPAIR_INGOT_MAP.get(leftType);
		return requiredIngot != null && rightType == requiredIngot;
	}

	public enum PrepareAnvilResult {
		ALLOW,
		DENY,
		DENY_WITHOUT_MSG,

		;
	}
}
