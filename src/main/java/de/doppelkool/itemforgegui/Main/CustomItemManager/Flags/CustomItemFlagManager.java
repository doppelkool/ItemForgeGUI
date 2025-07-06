package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagManager extends CustomFlagManager<CustomItemFlag> {
	protected static CustomItemFlagManager instance;

	public CustomItemFlagManager() {
		slotToFlag.put(11, new Pair<>(CustomItemFlag.HIDE_ITEM_FLAGS, ItemStacks.itemFlagHide));
		slotToFlag.put(13, new Pair<>(CustomItemFlag.HIDE_PREVENTION_FLAGS, ItemStacks.preventionFlagHide));
		slotToFlag.put(15, new Pair<>(CustomItemFlag.HIDE_ARMOR_EFFECTS, ItemStacks.armorEffectHide));
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

	public void initCustomItemFlags(ItemStack itemInMainHand) {
		boolean activateHideItemFlags = !ConfigManager.getInstance().isShowMinecraftItemFlags();
		boolean activateHideArmorEffects = !ConfigManager.getInstance().isShowCustomArmorEffects();
		boolean activateHidePreventionFlags = !ConfigManager.getInstance().isShowCustomPreventionFlags();

		this.toggleItemFlag(itemInMainHand,CustomItemFlag.HIDE_ITEM_FLAGS, activateHideItemFlags);
		this.toggleItemFlag(itemInMainHand,CustomItemFlag.HIDE_ARMOR_EFFECTS, activateHideArmorEffects);
		this.toggleItemFlag(itemInMainHand,CustomItemFlag.HIDE_PREVENTION_FLAGS, activateHidePreventionFlags);
	}
}
