package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class EditNumberMenu extends Menu {

	protected HashMap<Integer, ItemStack> inventoryItems = new HashMap<>();

	public EditNumberMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);

		inventoryItems.put(27, ItemStacks.closeInventory);
		inventoryItems.put(28, ItemStacks.backInventory);
		inventoryItems.put(9, ItemStacks.toMin);
		inventoryItems.put(17, ItemStacks.toMax);
		inventoryItems.put(10, ItemStacks.minus100);
		inventoryItems.put(11, ItemStacks.minus10);
		inventoryItems.put(12, ItemStacks.minus1);
		inventoryItems.put(14, ItemStacks.plus1);
		inventoryItems.put(15, ItemStacks.plus10);
		inventoryItems.put(16, ItemStacks.plus100);
		inventoryItems.put(22, ItemStacks.customValue);
	}

	@Override
	public int getSlots() {
		return 9*4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 27) {
			handleClose();
			return;
		}
		if (e.getSlot() == 28) {
			handleBack();
			return;
		}
		if(e.getSlot() == 9) {
			handleToZero();
			return;
		}
		if(e.getSlot() == 17) {
			handleToMax();
			return;
		}
		if(e.getSlot() == 10) {
			handleMinus100();
			return;
		}
		if(e.getSlot() == 11) {
			handleMinus10();
			return;
		}
		if(e.getSlot() == 12) {
			handleMinus1();
			return;
		}
		if(e.getSlot() == 14) {
			handlePlus1();
			return;
		}
		if(e.getSlot() == 15) {
			handlePlus10();
			return;
		}
		if(e.getSlot() == 16) {
			handlePlus100();
			return;
		}
		if(e.getSlot() == 22) {
			handleCustomNumber();
			return;
		}

		onCustomItemClick(e);
	}


	@Override
	public void setMenuItems() {
		addMenuBorder();
		for(Map.Entry<Integer,ItemStack> e : inventoryItems.entrySet())
			this.inventory.setItem(e.getKey(), e.getValue());
		setFillerGlass();
	}

	protected void handleClose() {
		this.playerMenuUtility.getOwner().closeInventory();
	}
	protected void handleBack() {
		new ItemEditMenu(this.playerMenuUtility)
			.open();
	}
	protected abstract void handleToZero();
	protected abstract void handleToMax();
	protected abstract void handleMinus100();
	protected abstract void handleMinus10();
	protected abstract void handleMinus1();
	protected abstract void handlePlus1();
	protected abstract void handlePlus10();
	protected abstract void handlePlus100();
	protected int handleCustomNumber() {
		return editCustomNumberDefault();
	}
	protected void onCustomItemClick(InventoryClickEvent e) {}

	//ToDo
	private int editCustomNumberDefault() {
		return 0;
	}
}
