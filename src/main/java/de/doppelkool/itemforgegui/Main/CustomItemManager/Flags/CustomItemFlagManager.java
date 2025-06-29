package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagManager extends CustomFlagManager<CustomItemFlag> {
	protected static CustomItemFlagManager instance;

	public CustomItemFlagManager() {
		slotToFlag.put(25, new Pair<>(CustomItemFlag.HIDE, ItemStacks.itemFlagHide));
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

	public HideFlag getActiveHideFlag(ItemStack currentItem) {
		PersistentDataContainer dataContainer = currentItem.getItemMeta().getPersistentDataContainer();
		if (!dataContainer.has(Main.getPlugin().getCustomTagCustomHideFlag(), PersistentDataType.STRING)) {
			return null;
		}

		return HideFlag.valueOf(dataContainer.get(Main.getPlugin().getCustomTagCustomHideFlag(), PersistentDataType.STRING));
	}

	public void updateActiveHideFlag(ItemStack itemStack, @Nullable HideFlag cycle) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (cycle != null) {
			itemMeta.getPersistentDataContainer()
				.set(Main.getPlugin().getCustomTagCustomHideFlag(),
					PersistentDataType.STRING,
					cycle.name()
				);
		} else {
			itemMeta.getPersistentDataContainer()
				.remove(Main.getPlugin().getCustomTagCustomHideFlag());
		}

		itemStack.setItemMeta(itemMeta);
	}

	@Getter
	@AllArgsConstructor
	public enum HideFlag {
		HIDE_ITEM_FLAGS("Item Flags"),
		HIDE_PREVENTION_FLAGS("Prevention Flags"),
		HIDE_ARMOR_EFFECTS("Armor Effects"),
		HIDE_ALL("All of the above"),
		;

		private final String itemDescription;

		public HideFlag cycle() {
			HideFlag[] values = HideFlag.values();
			int nextOrdinal = this.ordinal() + 1;
			return nextOrdinal < values.length ? values[nextOrdinal] : null;
		}
	}
}
