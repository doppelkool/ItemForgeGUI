package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class EditNumberMenu extends Menu {

	protected List<SlotItemAction> SLOT_ITEMS = List.of(
		new SlotItemAction(9, ItemStacks.toMin, e -> handleToZero()),
		new SlotItemAction(17, ItemStacks.toMax, e -> handleToMax()),
		new SlotItemAction(10, ItemStacks.minus100, e -> handleMinus100()),
		new SlotItemAction(11, ItemStacks.minus10, e -> handleMinus10()),
		new SlotItemAction(12, ItemStacks.minus1, e -> handleMinus1()),
		new SlotItemAction(14, ItemStacks.plus1, e -> handlePlus1()),
		new SlotItemAction(15, ItemStacks.plus10, e -> handlePlus10()),
		new SlotItemAction(16, ItemStacks.plus100, e -> handlePlus100())
		//new SlotItemAction(22, ItemStacks.customValue, e -> handleCustomNumber())
	);

	public EditNumberMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
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
		SLOT_ITEMS.stream()
			.filter(si -> si.slot() == e.getSlot())
			.findFirst()
			.orElseThrow()
			.onClick()
			.accept(e);

		onCustomItemClick(e);
	}


	@Override
	public void setMenuItems() {
		addMenuBorder();
		for(SlotItemAction item : SLOT_ITEMS)
			this.inventory.setItem(item.slot(), item.item());
		setFillerGlass();
	}

	protected abstract void handleToZero();
	protected abstract void handleToMax();
	protected abstract void handleMinus100();
	protected abstract void handleMinus10();
	protected abstract void handleMinus1();
	protected abstract void handlePlus1();
	protected abstract void handlePlus10();
	protected abstract void handlePlus100();
	//ToDo
	protected int handleCustomNumber() {
		return 0;
	}
	protected void onCustomItemClick(InventoryClickEvent e) {}
}
