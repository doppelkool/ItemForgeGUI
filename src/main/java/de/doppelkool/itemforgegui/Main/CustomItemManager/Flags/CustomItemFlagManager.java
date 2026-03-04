package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.CustomItemFlagMenu.CustomItemFlagItems;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagManager extends CustomFlagManager<CustomItemFlag> {
	protected static CustomItemFlagManager instance;

	public CustomItemFlagManager() {
		slotToFlag.put(11, new Pair<>(CustomItemFlag.HIDE_ITEM_FLAGS, CustomItemFlagItems.itemFlagHide));
		slotToFlag.put(13, new Pair<>(CustomItemFlag.HIDE_PREVENTION_FLAGS, CustomItemFlagItems.preventionFlagHide));
		slotToFlag.put(15, new Pair<>(CustomItemFlag.HIDE_ARMOR_EFFECTS, CustomItemFlagItems.armorEffectHide));
	}

	@Override
	protected NamespacedKey getNameSpacedKey() {
		return Main.getPlugin().getCustomTagCustomItemFlags();
	}

	@Override
	protected Class<CustomItemFlag> getDeclaringClass() {
		return CustomItemFlag.class;
	}

	public static CustomItemFlagManager getInstance() {
		if (instance == null) {
			instance = new CustomItemFlagManager();
		}
		return instance;
	}

	public void initShowMinecraftItemFlagsFlag(ItemStack itemInMainHand) {
		if (!isFlagApplied(itemInMainHand, CustomItemFlag.HIDE_ITEM_FLAGS)) {
			boolean activateHideItemFlags = !ConfigManager.getInstance().isShowMinecraftItemFlags();
			this.toggleItemFlag(itemInMainHand, CustomItemFlag.HIDE_ITEM_FLAGS, activateHideItemFlags);
		}
	}

	public void initShowArmorEffectsFlag(ItemStack itemInMainHand) {
		if (!isFlagApplied(itemInMainHand, CustomItemFlag.HIDE_ARMOR_EFFECTS)) {
			boolean activateHideArmorEffects = !ConfigManager.getInstance().isShowCustomArmorEffects();
			this.toggleItemFlag(itemInMainHand, CustomItemFlag.HIDE_ARMOR_EFFECTS, activateHideArmorEffects);
		}
	}

	public void initShowPreventionFlagsFlag(ItemStack itemInMainHand) {
		if (!isFlagApplied(itemInMainHand, CustomItemFlag.HIDE_PREVENTION_FLAGS)) {
			boolean activateHidePreventionFlags = !ConfigManager.getInstance().isShowCustomPreventionFlags();
			this.toggleItemFlag(itemInMainHand, CustomItemFlag.HIDE_PREVENTION_FLAGS, activateHidePreventionFlags);
		}
	}
}
