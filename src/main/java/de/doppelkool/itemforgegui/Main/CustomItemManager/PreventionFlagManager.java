package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
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
public class PreventionFlagManager {

	public static final HashMap<Integer, Pair<ForgeAction, ItemStack>> SLOT_TO_ACTION = new HashMap<>();

	static {
		SLOT_TO_ACTION.put(10, new Pair<>(ForgeAction.DROP, ItemStacks.itemDrop));
		SLOT_TO_ACTION.put(11, new Pair<>(ForgeAction.ITEM_FRAME_PLACE, ItemStacks.itemFramePlace));
		SLOT_TO_ACTION.put(12, new Pair<>(ForgeAction.DESTROY, ItemStacks.destroyItem));
		SLOT_TO_ACTION.put(13, new Pair<>(ForgeAction.CRAFT, ItemStacks.itemCraft));
		SLOT_TO_ACTION.put(14, new Pair<>(ForgeAction.RENAME, ItemStacks.renameItem));

		SLOT_TO_ACTION.put(19, new Pair<>(ForgeAction.SMELT, ItemStacks.itemSmelt));
		SLOT_TO_ACTION.put(20, new Pair<>(ForgeAction.LAUNCH, ItemStacks.throwItem));
		SLOT_TO_ACTION.put(21, new Pair<>(ForgeAction.EAT, ItemStacks.eatItem));
		SLOT_TO_ACTION.put(22, new Pair<>(ForgeAction.PLACE, ItemStacks.placeItem));
		SLOT_TO_ACTION.put(23, new Pair<>(ForgeAction.EQUIP, ItemStacks.equipItem));
		SLOT_TO_ACTION.put(24, new Pair<>(ForgeAction.REPAIR, ItemStacks.repairItem));
		SLOT_TO_ACTION.put(25, new Pair<>(ForgeAction.ENCHANT, ItemStacks.enchantItem));
		SLOT_TO_ACTION.put(30, new Pair<>(ForgeAction.DISENCHANT, ItemStacks.disenchantItem));
		SLOT_TO_ACTION.put(31, new Pair<>(ForgeAction.UPGRADE, ItemStacks.upgradeItem));
	}

	public static ArrayList<ForgeAction> mapNotAllowedForgeActions(PersistentDataContainer dataContainer) {
		String notAllowedForgeActions = getNotAllowedForgeActions(dataContainer);

		if (notAllowedForgeActions.isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				notAllowedForgeActions.split(";"))
			.map(type -> ForgeAction.valueOf(type.trim()))
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void toggleAllowedAction(ItemStack itemStack, ForgeAction forgeAction, boolean newStatus) {
		ArrayList<ForgeAction> forgeActions = mapNotAllowedForgeActions(itemStack.getItemMeta().getPersistentDataContainer());

		//Already applied status
		boolean forgeActionAlreadyApplied = forgeActions.contains(forgeAction);
		if ((forgeActionAlreadyApplied && newStatus) || (!forgeActionAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			forgeActions.add(forgeAction);
		} else {
			forgeActions.remove(forgeAction);
		}

		applyActionsToItemStack(itemStack, forgeActions);
	}

	public static void toggleAllowedAction(Block block, ForgeAction forgeAction, boolean newStatus) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		ArrayList<ForgeAction> forgeActions = mapNotAllowedForgeActions(customBlockData);

		boolean forgeActionAlreadyApplied = forgeActions.contains(forgeAction);
		if ((forgeActionAlreadyApplied && newStatus) || (!forgeActionAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			forgeActions.add(forgeAction);
		} else {
			forgeActions.remove(forgeAction);
		}

		applyActionsToBlock(block, forgeActions);
	}

	private static void applyActionsToItemStack(ItemStack itemStack, List<ForgeAction> forgeActions) {
		ItemMeta itemMeta = itemStack
			.getItemMeta();
		itemMeta.getPersistentDataContainer()
			.set(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(),
				PersistentDataType.STRING,
				serializeForgeActions(forgeActions));
		itemStack.setItemMeta(itemMeta);
	}

	private static void applyActionsToBlock(Block block, List<ForgeAction> forgeActions) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		customBlockData
			.set(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(),
				PersistentDataType.STRING,
				serializeForgeActions(forgeActions));
	}

	private static String serializeForgeActions(List<ForgeAction> forgeActions) {
		return forgeActions
			.stream().map(ForgeAction::toString)
			.collect(Collectors.joining(";"));
	}

	public static String getNotAllowedForgeActions(PersistentDataContainer dataContainer) {
		if (!dataContainer.has(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING)) {
			return "";
		}

		String s = dataContainer.get(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static boolean isActionPrevented(@NotNull PersistentDataHolder dataHolder, @NotNull ForgeAction action) {
		return isActionPrevented(dataHolder.getPersistentDataContainer(), action);
	}

	public static boolean isActionPrevented(@NotNull PersistentDataContainer dataContainer, @NotNull ForgeAction action) {
		return mapNotAllowedForgeActions(dataContainer)
			.contains(action);
	}

	public static boolean isActionPrevented(@Nullable ItemStack item, @NotNull ForgeAction action) {
		return item != null
			&& item.getItemMeta() != null
			&& UniqueItemIdentifierManager.isUniqueItem(item)
			&& isActionPrevented(item.getItemMeta(), action);
	}

	public static CraftingPrevention getActiveCraftingPrevention(ItemStack currentItem) {
		PersistentDataContainer dataContainer = currentItem.getItemMeta().getPersistentDataContainer();
		if (!dataContainer.has(Main.getPlugin().getCustomTagItemCraftPrevention(), PersistentDataType.STRING)) {
			return null;
		}

		return CraftingPrevention.valueOf(dataContainer.get(Main.getPlugin().getCustomTagItemCraftPrevention(), PersistentDataType.STRING));
	}

	public static void updateCraftPreventionType(ItemStack itemStack, @Nullable CraftingPrevention cycle) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (cycle != null) {
			itemMeta.getPersistentDataContainer()
				.set(Main.getPlugin().getCustomTagItemCraftPrevention(),
					PersistentDataType.STRING,
					cycle.name()
				);
		} else {
			itemMeta.getPersistentDataContainer()
				.remove(Main.getPlugin().getCustomTagItemCraftPrevention());
		}

		itemStack.setItemMeta(itemMeta);
	}

	@Getter
	public enum CraftingPrevention {
		ALL(0, "All Crafting Recipes"),
		DEFAULT_RECIPES(1, "Default Minecraft Crafting Recipes"),
		CUSTOM_RECIPES(2, "Custom Crafting Recipes"),

		;

		private final int index;
		private final String itemDescription;

		CraftingPrevention(int index, String itemDescription) {
			this.index = index;
			this.itemDescription = itemDescription;
		}

		public CraftingPrevention cycle() {
			CraftingPrevention[] values = CraftingPrevention.values();
			int nextIndex = this.index + 1;
			if (nextIndex >= values.length) {
				return null;
			}
			return values[nextIndex];
		}

	}
}
