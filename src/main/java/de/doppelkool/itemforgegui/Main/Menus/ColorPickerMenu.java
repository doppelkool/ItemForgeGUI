package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ColorPickerMenu extends Menu {

	private static final List<Integer> slots = List.of(
		10,   12,13,14,15,16,
		21,22,23,24,25,
		29,30,31,32,33,34
	);

	private static final ItemStack[] targetItems = new ItemStack[]{
		ItemStacks.DEFAULT_dye,
		ItemStacks.WHITE_dye,
		ItemStacks.LIGHT_GRAY_dye,
		ItemStacks.GRAY_dye,
		ItemStacks.BLACK_dye,
		ItemStacks.BROWN_dye,
		ItemStacks.RED_dye,
		ItemStacks.ORANGE_dye,
		ItemStacks.YELLOW_dye,
		ItemStacks.LIME_dye,
		ItemStacks.GREEN_dye,
		ItemStacks.CYAN_dye,
		ItemStacks.LIGHT_BLUE_dye,
		ItemStacks.BLUE_dye,
		ItemStacks.PURPLE_dye,
		ItemStacks.MAGENTA_dye,
		ItemStacks.PINK_dye,
	};

	public ColorPickerMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Color Picker: " + ItemStackHelper.formatCAPSName(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType().getTranslationKey());
	}

	@Override
	public int getSlots() {
		return 9*5;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {

		if (e.getSlot() == 36) {
			this.playerMenuUtility.getOwner().closeInventory();
			return;
		}
		if (e.getSlot() == 37) {
			new ItemEditMenu(this.playerMenuUtility)
				.open();
			return;
		}

		Material clickedItemType = e.getCurrentItem().getType();
		if(clickedItemType == Material.BARRIER) {
			return;
		}

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		//Default-Colored target item is already in players hand
		if(e.getSlot() == 10
			&& ItemStackHelper.aNoDyedVariationExist.contains(itemInMainHand.getType())) {
			return;
		}

		//Target item is Colored
		String colorByColoredItem = getColorStringByDye(clickedItemType);

		Material typeInMainHand = itemInMainHand.getType();
		String baseItem = getDefaultColoredItemByUnkownItem(typeInMainHand);

		//Item with same color already in main hand
		if(getColorFromItem(typeInMainHand, baseItem).equals(colorByColoredItem)) {
			return;
		}

		Material futureColoredItemType;
		if(colorByColoredItem != null) {
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
	 *
	 * */
	private String getDefaultColoredItemByUnkownItem(Material material) {
		for(DyeColor dyeColor : DyeColor.values()) {
			if(!(material.name().startsWith(dyeColor.name()))) continue;
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

		this.inventory.setItem(36, ItemStacks.closeInventory);
		this.inventory.setItem(37, ItemStacks.backInventory);

		Material typeInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType();

		int i = 0;
		for (int slot : slots) {
			if(i == 0 && !doesDefaultColoredItemExist(typeInMainHand)) {
				this.inventory.setItem(slot, notAvailable(targetItems[0]));
				i++;
				continue;
			}
			this.inventory.setItem(slot, targetItems[i++]);
		}
		setFillerGlass();
	}
}
