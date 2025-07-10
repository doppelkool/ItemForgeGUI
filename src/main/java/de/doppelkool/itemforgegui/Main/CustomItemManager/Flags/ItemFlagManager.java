package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.MinecraftItemFlagMenu.MinecraftItemFlagItems;
import lombok.Getter;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemFlagManager {
	protected static ItemFlagManager instance;

	@Getter
	protected final Map<Integer, Pair<ItemFlag, ItemStack>> slotToFlag = new HashMap<>();

	public ItemFlagManager() {
		slotToFlag.put(11, new Pair<>(ItemFlag.HIDE_ENCHANTS, MinecraftItemFlagItems.hideEnchantments));
		slotToFlag.put(12, new Pair<>(ItemFlag.HIDE_ATTRIBUTES, MinecraftItemFlagItems.hideAttributes));
		slotToFlag.put(13, new Pair<>(ItemFlag.HIDE_UNBREAKABLE, MinecraftItemFlagItems.hideUnbreakable));
		slotToFlag.put(14, new Pair<>(ItemFlag.HIDE_DESTROYS, MinecraftItemFlagItems.hideDestroys));
		slotToFlag.put(15, new Pair<>(ItemFlag.HIDE_PLACED_ON, MinecraftItemFlagItems.hidePlacedOn));
		slotToFlag.put(21, new Pair<>(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, MinecraftItemFlagItems.hideAdditionalToolTip));
		slotToFlag.put(22, new Pair<>(ItemFlag.HIDE_DYE, MinecraftItemFlagItems.hideDye));
		slotToFlag.put(23, new Pair<>(ItemFlag.HIDE_ARMOR_TRIM, MinecraftItemFlagItems.hideArmorTrim));
	}

	public static ItemFlagManager getInstance() {
		if (instance == null) {
			instance = new ItemFlagManager();
		}
		return instance;
	}
}