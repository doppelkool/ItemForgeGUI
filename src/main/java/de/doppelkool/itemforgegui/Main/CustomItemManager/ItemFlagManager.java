package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.HashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemFlagManager {

	public static final HashMap<Integer, Pair<ItemFlag, ItemStack>> SLOT_TO_FLAG = new HashMap<>();

	static {
		SLOT_TO_FLAG.put(11, new Pair<>(ItemFlag.HIDE_ENCHANTS, ItemStacks.hideEnchantments));
		SLOT_TO_FLAG.put(12, new Pair<>(ItemFlag.HIDE_ATTRIBUTES, ItemStacks.hideAttributes));
		SLOT_TO_FLAG.put(13, new Pair<>(ItemFlag.HIDE_UNBREAKABLE, ItemStacks.hideUnbreakable));
		SLOT_TO_FLAG.put(14, new Pair<>(ItemFlag.HIDE_DESTROYS, ItemStacks.hideDestroys));
		SLOT_TO_FLAG.put(20, new Pair<>(ItemFlag.HIDE_PLACED_ON, ItemStacks.hidePlacedOn));
		SLOT_TO_FLAG.put(21, new Pair<>(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemStacks.hideAdditionalToolTip));
		SLOT_TO_FLAG.put(22, new Pair<>(ItemFlag.HIDE_DYE, ItemStacks.hideDye));
		SLOT_TO_FLAG.put(23, new Pair<>(ItemFlag.HIDE_ARMOR_TRIM, ItemStacks.hideArmorTrim));
	}
}