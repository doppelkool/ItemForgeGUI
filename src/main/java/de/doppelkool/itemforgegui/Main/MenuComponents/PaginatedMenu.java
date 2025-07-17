package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.Getter;

import java.util.Arrays;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class PaginatedMenu extends Menu {
	protected final IntSet fillingGlassSlots = new IntOpenHashSet();
	protected final IntSet emptyInvSpace = new IntOpenHashSet();

	protected int paginatedMenuLeftSlot;
	protected int paginatedMenuRightSlot;

	@Getter
	private final int slots = 9 * 6;
	protected int page = 0;
	protected int maxItemsPerPage = 7 * 3;

	protected PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);

		fillingGlassSlots.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 47, 49, 51, 52, 53));
		emptyInvSpace.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34));

		paginatedMenuLeftSlot = this.slots - 6;
		paginatedMenuRightSlot = this.slots - 4;
	}

	protected void addPaginatedItems() {
		inventory.setItem(closeInventorySlot, GlobalItems.closeInventory);
		inventory.setItem(backInventorySlot, GlobalItems.backInventory);
		inventory.setItem(paginatedMenuLeftSlot, GlobalItems.paginatedMenuLeft);
		inventory.setItem(paginatedMenuRightSlot, GlobalItems.paginatedMenuRight);
	}

	//For Enchantments and ArmorEffects
	protected void addCustomMenuFillingForEffects() {
		for (Integer i : fillingGlassSlots) {
			this.inventory.setItem(i, GlobalItems.FILLER_GLASS);
		}
	}

	protected int getInventorySlot(int slotIndex) {
		int row = slotIndex / 7; // Calculate row within the 7x4 grid
		int column = slotIndex % 7; // Calculate column within the 7x4 grid
		return (row + 1) * 9 + (column + 1);
	}

	protected boolean pageBack(int clickedSlot) {
		if (clickedSlot != paginatedMenuLeftSlot) {
			return false;
		}

		if (page != 0) {
			page--;
			this.open();
		}
		return true;
	}

	protected boolean pageForward(int clickedSlot, int collectionDisplayedSize) {
		if (clickedSlot != paginatedMenuRightSlot) {
			return false;
		}

		int nextPageStartIndex = (page + 1) * this.maxItemsPerPage;
		if (nextPageStartIndex < collectionDisplayedSize) {
			page++;
			super.open();
		}
		return true;
	}
}