package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenu;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditValueMenu extends ConfirmableMenu {

	private static final List<SlotItemWrapper.SlotItemOperationValueEdit> SLOT_ITEMS = List.of(
		new SlotItemWrapper.SlotItemOperationValueEdit(9, ModifyAttributeModifierItems.minus20,AttributeModifier.Operation.ADD_NUMBER , 20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(10, ModifyAttributeModifierItems.minus10, AttributeModifier.Operation.ADD_NUMBER, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(11, ModifyAttributeModifierItems.minus1,AttributeModifier.Operation.ADD_NUMBER, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(12, ModifyAttributeModifierItems.minusPoint1,AttributeModifier.Operation.ADD_NUMBER, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(14, ModifyAttributeModifierItems.plusPoint1,AttributeModifier.Operation.ADD_NUMBER, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(15, ModifyAttributeModifierItems.plus1,AttributeModifier.Operation.ADD_NUMBER, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(16, ModifyAttributeModifierItems.plus10,AttributeModifier.Operation.ADD_NUMBER, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(17, ModifyAttributeModifierItems.plus20,AttributeModifier.Operation.ADD_NUMBER, -20.0),

		new SlotItemWrapper.SlotItemOperationValueEdit(18, ModifyAttributeModifierItems.minus20,AttributeModifier.Operation.ADD_SCALAR, 20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(19, ModifyAttributeModifierItems.minus10,AttributeModifier.Operation.ADD_SCALAR, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(20, ModifyAttributeModifierItems.minus1,AttributeModifier.Operation.ADD_SCALAR, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(21, ModifyAttributeModifierItems.minusPoint1,AttributeModifier.Operation.ADD_SCALAR, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(23, ModifyAttributeModifierItems.plusPoint1,AttributeModifier.Operation.ADD_SCALAR, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(24, ModifyAttributeModifierItems.plus1,AttributeModifier.Operation.ADD_SCALAR, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(25, ModifyAttributeModifierItems.plus10,AttributeModifier.Operation.ADD_SCALAR, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(26, ModifyAttributeModifierItems.plus20,AttributeModifier.Operation.ADD_SCALAR, -20.0),

		new SlotItemWrapper.SlotItemOperationValueEdit(27, ModifyAttributeModifierItems.minus20,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(28, ModifyAttributeModifierItems.minus10,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(29, ModifyAttributeModifierItems.minus1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(30, ModifyAttributeModifierItems.minusPoint1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(32, ModifyAttributeModifierItems.plusPoint1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(33, ModifyAttributeModifierItems.plus1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(34, ModifyAttributeModifierItems.plus10,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(35, ModifyAttributeModifierItems.plus20,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -20.0)
	);

	public EditValueMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 44);
	}

	@Override
	public String getMenuName() {
		return "Edit Values: " + ItemStackModifyHelper.formatTranslationalNames(this.playerMenuUtility.getModifyAttributeStorage().getAttribute().getTranslationKey());
	}

	@Override
	public int getSlots() {
		return 9 * 5;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			this.playerMenuUtility.setAttributeStorage(null);
			return;
		}
		if (super.handleBack(e.getSlot(),
			null,
			ModifyAttributeModifierMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			null,
			ModifyAttributeModifierMenu::new)) {
			return;
		}

		SlotItemWrapper.SlotItemOperationValueEdit slotItemOperationValueEdit = SLOT_ITEMS.stream()
			.filter(si -> si.slot() == e.getSlot())
			.findFirst()
			.orElseThrow();
		EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues();
		Double oldValue = operationDoubleValues.getOrDefault(slotItemOperationValueEdit.operation(), 0d);
		double newValue = oldValue + slotItemOperationValueEdit.valueEdit();

		newValue = Math.round(newValue * 10) / 10.0;

		if(newValue == 0) {
			operationDoubleValues.remove(slotItemOperationValueEdit.operation());
		} else {
			operationDoubleValues.put(slotItemOperationValueEdit.operation(), newValue);
		}

		setMenuItems();
	}

	@Override
	public void setMenuItems() {
		//ToDo Enhance the items description to a point where it explains it better
		for (SlotItemWrapper.SlotItemOperationValueEdit slotItem : SLOT_ITEMS) {
			ItemStack itemToSet = fillEditValueItemWithLoreValues(slotItem);
			this.inventory.setItem(slotItem.slot(), itemToSet);
		}
		updateConfirmSlot();
		setFillerGlass();
	}

	private ItemStack fillEditValueItemWithLoreValues(SlotItemWrapper.SlotItemOperationValueEdit slotItem) {
		ItemStack item = slotItem.item().clone();
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE, "" + slotItem.valueEdit());
		return item;
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return ModifyAttributeModifierItems.confirmValues.clone();
	}
}
