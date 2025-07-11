package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class EditNumberMenu extends Menu {

	protected List<SlotItemWrapper.SlotItemEvent> SLOT_ITEMS = List.of(
		new SlotItemWrapper.SlotItemEvent(9, ItemStacks.toMin, e -> handleToZero()),
		new SlotItemWrapper.SlotItemEvent(17, ItemStacks.toMax, e -> handleToMax()),
		new SlotItemWrapper.SlotItemEvent(10, ItemStacks.minus100, e -> handleMinus100()),
		new SlotItemWrapper.SlotItemEvent(11, ItemStacks.minus10, e -> handleMinus10()),
		new SlotItemWrapper.SlotItemEvent(12, ItemStacks.minus1, e -> handleMinus1()),
		new SlotItemWrapper.SlotItemEvent(14, ItemStacks.plus1, e -> handlePlus1()),
		new SlotItemWrapper.SlotItemEvent(15, ItemStacks.plus10, e -> handlePlus10()),
		new SlotItemWrapper.SlotItemEvent(16, ItemStacks.plus100, e -> handlePlus100()),
		new SlotItemWrapper.SlotItemEvent(22, ItemStacks.customValue, e -> handleCustomNumber(e))
	);

	public EditNumberMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public int getSlots() {
		return 9 * 4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
			return;
		}

		if (onCustomItemClick(e)) {
			return;
		}

		SLOT_ITEMS.stream()
			.filter(si -> si.slot() == e.getSlot())
			.findFirst()
			.orElseThrow()
			.onClick()
			.accept(e);

	}


	@Override
	public void setMenuItems() {
		addMenuBorder();
		for (SlotItemWrapper.SlotItemEvent item : SLOT_ITEMS)
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

	protected abstract void handleCustomNumber(InventoryClickEvent e);

	protected boolean onCustomItemClick(InventoryClickEvent e) {
		return false;
	}
}
