package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.Resources;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.notAvailable;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to apply the non-RGB variants of the minecraft default {@link org.bukkit.Color} as item variation.
 * Including items such as beds, wool and bundles, etc.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ColorPickerMenu extends Menu {

	private static final List<SlotItemWrapper.SlotItem> SLOT_TO_ITEMS = List.of(
		new SlotItemWrapper.SlotItem(10, ItemStacks.DEFAULT_dye),
		new SlotItemWrapper.SlotItem(12, ItemStacks.WHITE_dye),
		new SlotItemWrapper.SlotItem(13, ItemStacks.LIGHT_GRAY_dye),
		new SlotItemWrapper.SlotItem(14, ItemStacks.GRAY_dye),
		new SlotItemWrapper.SlotItem(15, ItemStacks.BLACK_dye),
		new SlotItemWrapper.SlotItem(16, ItemStacks.BROWN_dye),
		new SlotItemWrapper.SlotItem(21, ItemStacks.RED_dye),
		new SlotItemWrapper.SlotItem(22, ItemStacks.ORANGE_dye),
		new SlotItemWrapper.SlotItem(23, ItemStacks.YELLOW_dye),
		new SlotItemWrapper.SlotItem(24, ItemStacks.LIME_dye),
		new SlotItemWrapper.SlotItem(25, ItemStacks.GREEN_dye),
		new SlotItemWrapper.SlotItem(29, ItemStacks.CYAN_dye),
		new SlotItemWrapper.SlotItem(30, ItemStacks.LIGHT_BLUE_dye),
		new SlotItemWrapper.SlotItem(31, ItemStacks.BLUE_dye),
		new SlotItemWrapper.SlotItem(32, ItemStacks.PURPLE_dye),
		new SlotItemWrapper.SlotItem(33, ItemStacks.MAGENTA_dye),
		new SlotItemWrapper.SlotItem(34, ItemStacks.PINK_dye)
	);

	public ColorPickerMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Color Picker: " + ItemStackHelper.formatCAPSName(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType().getTranslationKey());
	}

	@Override
	public int getSlots() {
		return 9 * 5;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
			return;
		}

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		//Default-Colored target item is already in players hand
		if (e.getSlot() == 10
			&& Resources.A_NO_DYED_VARIATION_EXIST.contains(itemInMainHand.getType())) {
			return;
		}

		//Target item is Colored
		Material clickedItemType = e.getCurrentItem().getType();
		String colorByColoredItem = getColorStringByDye(clickedItemType);

		Material typeInMainHand = itemInMainHand.getType();
		String baseItem = getDefaultColoredItemByUnkownItem(typeInMainHand);

		//Item with same color already in main hand
		if (getColorFromItem(typeInMainHand, baseItem).equals(colorByColoredItem)) {
			return;
		}

		Material futureColoredItemType;
		if (colorByColoredItem != null) {
			futureColoredItemType = Material.valueOf(
				colorByColoredItem + "_" + baseItem
			);
		} else {
			futureColoredItemType = Material.valueOf(
				baseItem
			);
		}
		itemInMainHand.setType(futureColoredItemType);
	}

	@Nullable
	private String getColorStringByDye(Material dyeClicked) {
		return dyeClicked == Material.PAPER
			? null
			: dyeClicked.name().replace("_DYE", "");
	}

	/**
	 * @return Either
	 * an existing default-colored item-name (f.e. TERRACOTTA)
	 * or
	 * a not existing name for a default-colored item (f.e. CONCRETE)
	 */
	private String getDefaultColoredItemByUnkownItem(Material material) {
		for (DyeColor dyeColor : DyeColor.values()) {
			if (!(material.name().startsWith(dyeColor.name()))) continue;
			return material.name().replace(dyeColor.name() + "_", "");
		}
		return material.name();
	}

	private boolean doesDefaultColoredItemExist(Material colorable) {
		try {
			Material.valueOf(getDefaultColoredItemByUnkownItem(colorable));
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private String getColorFromItem(Material itemToGetColor, String defaultColorItem) {
		return itemToGetColor.name().replace("_" + defaultColorItem, "");
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		Material typeInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType();

		for (SlotItemWrapper.SlotItem slotItem : SLOT_TO_ITEMS) {
			if (slotItem == SLOT_TO_ITEMS.getFirst()
				&& !doesDefaultColoredItemExist(typeInMainHand)) {
				this.inventory.setItem(slotItem.slot(), notAvailable(slotItem.item()));
			} else {
				this.inventory.setItem(slotItem.slot(), slotItem.item());
			}
		}
		setFillerGlass();
	}
}
