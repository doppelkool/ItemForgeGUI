package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.PreventionFlagMenu.PreventionFlagItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.HashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventionFlagManager extends CustomFlagManager<ForgeAction> {
	protected static PreventionFlagManager instance;

	public static final HashMap<Integer, Pair<ForgeAction, ItemStack>> SLOT_TO_ACTION = new HashMap<>();

	public PreventionFlagManager() {
		SLOT_TO_ACTION.put(10, new Pair<>(ForgeAction.DROP, PreventionFlagItems.itemDrop));
		SLOT_TO_ACTION.put(11, new Pair<>(ForgeAction.ITEM_FRAME_PLACE, PreventionFlagItems.itemFramePlace));
		SLOT_TO_ACTION.put(12, new Pair<>(ForgeAction.DESTROY, PreventionFlagItems.destroyItem));
		SLOT_TO_ACTION.put(13, new Pair<>(ForgeAction.CRAFT, PreventionFlagItems.itemCraft));
		SLOT_TO_ACTION.put(14, new Pair<>(ForgeAction.RENAME, PreventionFlagItems.renameItem));

		SLOT_TO_ACTION.put(19, new Pair<>(ForgeAction.SMELT, PreventionFlagItems.itemSmelt));
		SLOT_TO_ACTION.put(20, new Pair<>(ForgeAction.LAUNCH, PreventionFlagItems.throwItem));
		SLOT_TO_ACTION.put(21, new Pair<>(ForgeAction.EAT, PreventionFlagItems.eatItem));
		SLOT_TO_ACTION.put(22, new Pair<>(ForgeAction.PLACE, PreventionFlagItems.placeItem));
		SLOT_TO_ACTION.put(23, new Pair<>(ForgeAction.EQUIP, PreventionFlagItems.equipItem));
		SLOT_TO_ACTION.put(24, new Pair<>(ForgeAction.REPAIR, PreventionFlagItems.repairItem));
		SLOT_TO_ACTION.put(25, new Pair<>(ForgeAction.ENCHANT, PreventionFlagItems.enchantItem));
		SLOT_TO_ACTION.put(30, new Pair<>(ForgeAction.DISENCHANT, PreventionFlagItems.disenchantItem));
		SLOT_TO_ACTION.put(31, new Pair<>(ForgeAction.UPGRADE, PreventionFlagItems.upgradeItem));
	}

	@Override
	protected NamespacedKey getNameSpacedKey() {
		return Main.getPlugin().getCustomTagItemNotAllowedForgeActions();
	}

	@Override
	protected Class<ForgeAction> getDeclaringClass() {
		return ForgeAction.class;
	}

	public static PreventionFlagManager getInstance() {
		if (instance == null) {
			instance = new PreventionFlagManager();
		}
		return instance;
	}

	public CraftingPreventionFlag getActiveCraftingPrevention(ItemStack currentItem) {
		PersistentDataContainer dataContainer = currentItem.getItemMeta().getPersistentDataContainer();
		if (!dataContainer.has(Main.getPlugin().getCustomTagItemCraftPrevention(), PersistentDataType.STRING)) {
			return null;
		}

		return CraftingPreventionFlag.valueOf(dataContainer.get(Main.getPlugin().getCustomTagItemCraftPrevention(), PersistentDataType.STRING));
	}

	public void updateCraftPreventionType(ItemStack itemStack, @Nullable PreventionFlagManager.CraftingPreventionFlag cycle) {
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
	@AllArgsConstructor
	public enum CraftingPreventionFlag {
		ALL("All Crafting Recipes"),
		DEFAULT_RECIPES("Default Minecraft Crafting Recipes"),
		CUSTOM_RECIPES("Custom Crafting Recipes"),
		;

		private final String itemDescription;

		public CraftingPreventionFlag cycle() {
			CraftingPreventionFlag[] values = CraftingPreventionFlag.values();
			int nextOrdinal = this.ordinal() + 1;
			return nextOrdinal < values.length ? values[nextOrdinal] : null;
		}
	}
}
