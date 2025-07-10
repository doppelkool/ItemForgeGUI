package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class PaginatedMenu extends Menu {
	protected HashSet<Integer> fillingGlassSlots = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 47, 49, 51, 52, 53));
	protected HashSet<Integer> emptyInvSpace = new HashSet<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));

	protected int page = 0;
	@Getter
	protected int maxItemsPerPage = 7 * 3;

	public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	public void addPaginatedItems() {
		inventory.setItem(45, GlobalItems.closeInventory);
		inventory.setItem(46, GlobalItems.backInventory);
		inventory.setItem(48, GlobalItems.paginatedMenuLeft);
		inventory.setItem(50, GlobalItems.paginatedMenuRight);
	}

	//For Enchantments and ArmorEffects
	public void addCustomMenuFillingForEffects() {
		for (Integer i : fillingGlassSlots) {
			this.inventory.setItem(i, GlobalItems.FILLER_GLASS);
		}
	}

	protected int getInventorySlot(int slotIndex) {
		int row = slotIndex / 7; // Calculate row within the 7x4 grid
		int column = slotIndex % 7; // Calculate column within the 7x4 grid
		return (row + 1) * 9 + (column + 1);
	}
}

