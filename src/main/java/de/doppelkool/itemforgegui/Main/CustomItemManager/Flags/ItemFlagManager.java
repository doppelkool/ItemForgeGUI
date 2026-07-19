package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.MinecraftItemFlagMenu.MinecraftItemFlagItems;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Pair;
import lombok.Getter;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

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
		ItemFlag storedEnchantmentsFlag;

		// Spigots experimental HIDE_STORED_ENCHANTMENTS is equivalent to Papers stable HIDE_STORED_ENCHANTS. Since Papers is not known to Spigot, we use a workaround to load it onDemand.
		if (isPaper()) {
			storedEnchantmentsFlag = ItemFlag.valueOf("HIDE_STORED_ENCHANTS");
		} else {
			storedEnchantmentsFlag = ItemFlag.HIDE_STORED_ENCHANTMENTS;
		}

		slotToFlag.put(11, new Pair<>(ItemFlag.HIDE_ENCHANTS, MinecraftItemFlagItems.hideEnchantments));
		slotToFlag.put(12, new Pair<>(storedEnchantmentsFlag, MinecraftItemFlagItems.hideStoredEnchantments));
		slotToFlag.put(13, new Pair<>(ItemFlag.HIDE_ATTRIBUTES, MinecraftItemFlagItems.hideAttributes));
		slotToFlag.put(14, new Pair<>(ItemFlag.HIDE_UNBREAKABLE, MinecraftItemFlagItems.hideUnbreakable));
		slotToFlag.put(15, new Pair<>(ItemFlag.HIDE_DESTROYS, MinecraftItemFlagItems.hideDestroys));
		slotToFlag.put(21, new Pair<>(ItemFlag.HIDE_PLACED_ON, MinecraftItemFlagItems.hidePlacedOn));
		slotToFlag.put(22, new Pair<>(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, MinecraftItemFlagItems.hideAdditionalToolTip));
		slotToFlag.put(23, new Pair<>(ItemFlag.HIDE_DYE, MinecraftItemFlagItems.hideDye));
		slotToFlag.put(24, new Pair<>(ItemFlag.HIDE_ARMOR_TRIM, MinecraftItemFlagItems.hideArmorTrim));
	}

	public static ItemFlagManager getInstance() {
		if (instance == null) {
			instance = new ItemFlagManager();
		}
		return instance;
	}

	private static boolean isPaper() {
		try {
			Class.forName("io.papermc.paper.configuration.Configuration");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}