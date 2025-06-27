package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagManager {

	public static final HashMap<Integer, Pair<CustomItemFlag, ItemStack>> SLOT_TO_FLAG = new HashMap<>();

	static {
		SLOT_TO_FLAG.put(25, new Pair<>(CustomItemFlag.HIDE, ItemStacks.itemFlagHide));
	}

	public static ArrayList<CustomItemFlag> mapCustomItemFlags(PersistentDataContainer dataContainer) {
		String customItemFlags = getCustomItemFlags(dataContainer);

		if (customItemFlags.isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				customItemFlags.split(";"))
			.map(type -> CustomItemFlag.valueOf(type.trim()))
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void toggleCustomItemFlag(ItemStack itemStack, CustomItemFlag customItemFlag, boolean newStatus) {
		ArrayList<CustomItemFlag> customItemFlags = mapCustomItemFlags(itemStack.getItemMeta().getPersistentDataContainer());

		//Already applied status
		boolean customFlagAlreadyApplied = customItemFlags.contains(customItemFlag);
		if ((customFlagAlreadyApplied && newStatus) || (!customFlagAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			customItemFlags.add(customItemFlag);
		} else {
			customItemFlags.remove(customItemFlag);
		}

		applyFlagToItemStack(itemStack, customItemFlags);
	}

	public static void toggleCustomItemFlag(Block block, CustomItemFlag customItemFlag, boolean newStatus) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		ArrayList<CustomItemFlag> customItemFlags = mapCustomItemFlags(customBlockData);

		boolean forgeActionAlreadyApplied = customItemFlags.contains(customItemFlag);
		if ((forgeActionAlreadyApplied && newStatus) || (!forgeActionAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			customItemFlags.add(customItemFlag);
		} else {
			customItemFlags.remove(customItemFlag);
		}

		applyFlagToBlock(block, customItemFlags);
	}

	private static void applyFlagToItemStack(ItemStack itemStack, List<CustomItemFlag> customItemFlag) {
		ItemMeta itemMeta = itemStack
			.getItemMeta();
		itemMeta.getPersistentDataContainer()
			.set(Main.getPlugin().getCustomTagCustomItemFlags(),
				PersistentDataType.STRING,
				serializeCustomItemFlags(customItemFlag));
		itemStack.setItemMeta(itemMeta);
	}

	private static void applyFlagToBlock(Block block, List<CustomItemFlag> customItemFlag) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		customBlockData
			.set(Main.getPlugin().getCustomTagCustomItemFlags(),
				PersistentDataType.STRING,
				serializeCustomItemFlags(customItemFlag));
	}

	private static String serializeCustomItemFlags(List<CustomItemFlag> customItemFlag) {
		return customItemFlag
			.stream().map(CustomItemFlag::toString)
			.collect(Collectors.joining(";"));
	}

	public static String getCustomItemFlags(PersistentDataContainer dataContainer) {
		if (!dataContainer.has(Main.getPlugin().getCustomTagCustomItemFlags(), PersistentDataType.STRING)) {
			return "";
		}

		String s = dataContainer.get(Main.getPlugin().getCustomTagCustomItemFlags(), PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static boolean isCustomFlagApplied(@NotNull PersistentDataHolder dataHolder, @NotNull CustomItemFlag action) {
		return isCustomFlagApplied(dataHolder.getPersistentDataContainer(), action);
	}

	public static boolean isCustomFlagApplied(@NotNull PersistentDataContainer dataContainer, @NotNull CustomItemFlag action) {
		return mapCustomItemFlags(dataContainer)
			.contains(action);
	}

	public static boolean isCustomFlagApplied(@Nullable ItemStack item, @NotNull CustomItemFlag action) {
		return item != null
			&& item.getItemMeta() != null
			&& UniqueItemIdentifierManager.isUniqueItem(item)
			&& isCustomFlagApplied(item.getItemMeta(), action);
	}

	public static HideFlag getActiveHideFlag(ItemStack currentItem) {
		PersistentDataContainer dataContainer = currentItem.getItemMeta().getPersistentDataContainer();
		if (!dataContainer.has(Main.getPlugin().getCustomTagCustomHideFlag(), PersistentDataType.STRING)) {
			return null;
		}

		return HideFlag.valueOf(dataContainer.get(Main.getPlugin().getCustomTagCustomHideFlag(), PersistentDataType.STRING));
	}

	public static void updateActiveHideFlag(ItemStack itemStack, @Nullable HideFlag cycle) {
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
		HIDE_PREVENTION_FLAGS("Prevention Flags"),
		HIDE_ARMOR_EFFECTS("Armor Effects"),
		HIDE_BOTH("Both Prevention Flags and Armor Effects"),
		;

		private final String itemDescription;

		public HideFlag cycle() {
			HideFlag[] values = HideFlag.values();
			int nextOrdinal = this.ordinal() + 1;
			return nextOrdinal < values.length ? values[nextOrdinal] : null;
		}
	}
}
