package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus.ModifyAttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.GlobalAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems.ValuesItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus.ModifyAttributeModifierMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditValueMenu extends ConfirmableMenu {

	private static final List<SlotItemWrapper.SlotItemOperationValueEdit> SLOT_ITEMS = List.of(
		new SlotItemWrapper.SlotItemOperationValueEdit(9, GlobalAttributeModifierItems.minus20,AttributeModifier.Operation.ADD_NUMBER , -20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(10, GlobalAttributeModifierItems.minus10, AttributeModifier.Operation.ADD_NUMBER, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(11, GlobalAttributeModifierItems.minus1,AttributeModifier.Operation.ADD_NUMBER, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(12, GlobalAttributeModifierItems.minusPoint1,AttributeModifier.Operation.ADD_NUMBER, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(14, GlobalAttributeModifierItems.plusPoint1,AttributeModifier.Operation.ADD_NUMBER, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(15, GlobalAttributeModifierItems.plus1,AttributeModifier.Operation.ADD_NUMBER, 1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(16, GlobalAttributeModifierItems.plus10,AttributeModifier.Operation.ADD_NUMBER, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(17, GlobalAttributeModifierItems.plus20,AttributeModifier.Operation.ADD_NUMBER, 20.0),

		new SlotItemWrapper.SlotItemOperationValueEdit(18, GlobalAttributeModifierItems.minus20,AttributeModifier.Operation.ADD_SCALAR, -20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(19, GlobalAttributeModifierItems.minus10,AttributeModifier.Operation.ADD_SCALAR, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(20, GlobalAttributeModifierItems.minus1,AttributeModifier.Operation.ADD_SCALAR, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(21, GlobalAttributeModifierItems.minusPoint1,AttributeModifier.Operation.ADD_SCALAR, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(23, GlobalAttributeModifierItems.plusPoint1,AttributeModifier.Operation.ADD_SCALAR, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(24, GlobalAttributeModifierItems.plus1,AttributeModifier.Operation.ADD_SCALAR, 1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(25, GlobalAttributeModifierItems.plus10,AttributeModifier.Operation.ADD_SCALAR, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(26, GlobalAttributeModifierItems.plus20,AttributeModifier.Operation.ADD_SCALAR, 20.0),

		new SlotItemWrapper.SlotItemOperationValueEdit(27, GlobalAttributeModifierItems.minus20,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -20.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(28, GlobalAttributeModifierItems.minus10,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(29, GlobalAttributeModifierItems.minus1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(30, GlobalAttributeModifierItems.minusPoint1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, -0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(32, GlobalAttributeModifierItems.plusPoint1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 0.1),
		new SlotItemWrapper.SlotItemOperationValueEdit(33, GlobalAttributeModifierItems.plus1,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 1.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(34, GlobalAttributeModifierItems.plus10,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 10.0),
		new SlotItemWrapper.SlotItemOperationValueEdit(35, GlobalAttributeModifierItems.plus20,AttributeModifier.Operation.MULTIPLY_SCALAR_1, 20.0)
	);

	private static final List<SlotItemWrapper.SlotItemOperationExplanationValueEdit> INFO_BOOKS = List.of(
		new SlotItemWrapper.SlotItemOperationExplanationValueEdit(13,
			ValuesItems.infoBook,
			AttributeModifier.Operation.ADD_NUMBER,
			ValuesItems::getAddNumberExplanationLoreString,
			null),
		new SlotItemWrapper.SlotItemOperationExplanationValueEdit(22,
			ValuesItems.infoBook,
			AttributeModifier.Operation.ADD_SCALAR,
			ValuesItems::getAddScalarExplanationLoreString,
			null),
		new SlotItemWrapper.SlotItemOperationExplanationValueEdit(31,
			ValuesItems.infoBook,
			AttributeModifier.Operation.MULTIPLY_SCALAR_1,
			ValuesItems::getMultiplyScalar1ExplanationLoreString,
			null)
	);

	public EditValueMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 53);
	}

	@Override
	public String getMenuName() {
		return "Edit Values: " + ItemStackModifyHelper.formatTranslationalNames(this.playerMenuUtility.getModifyAttributeStorage().getAttribute().getTranslationKey());
	}

	@Override
	public int getSlots() {
		return 9 * 6;
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
		Optional<SlotItemWrapper.SlotItemOperationExplanationValueEdit> clickedInfoBook = INFO_BOOKS.stream()
			.filter(ib -> ib.slot() == e.getSlot())
			.findFirst();

		if(clickedInfoBook.isPresent()) {
			infoBookClicked(clickedInfoBook.get(), e.getClick());
			return;
		}

		slotItemsClicked(e.getSlot());
		setMenuItems();
	}

	private void slotItemsClicked(int clickedSlot) {
		SlotItemWrapper.SlotItemOperationValueEdit slotItemOperationValueEdit = SLOT_ITEMS.stream()
			.filter(si -> si.slot() == clickedSlot)
			.findFirst()
			.orElseThrow();
		EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues();
		double oldValue = operationDoubleValues.getOrDefault(slotItemOperationValueEdit.operation(), 0d);

		BigDecimal newValue = BigDecimal.valueOf(oldValue)
			.add(BigDecimal.valueOf(slotItemOperationValueEdit.valueEdit()))
			.setScale(3, RoundingMode.HALF_UP);

		if(newValue.doubleValue() == 0.000) {
			operationDoubleValues.remove(slotItemOperationValueEdit.operation());
		} else {
			operationDoubleValues.put(slotItemOperationValueEdit.operation(), newValue.doubleValue());
		}
	}

	/**
	 * If the book was clicked with
	 * - any form of left click: the plugin will provide a way to enter the value manually
	 * - any form of right click: the value for the clicked operation will be set to zero
	 * */
	private void infoBookClicked(SlotItemWrapper.SlotItemOperationExplanationValueEdit clickedInfoBook, ClickType clickType) {
		if (isLeft(clickType)) {
			EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues();
			double valueToManualEdit = operationDoubleValues.getOrDefault(clickedInfoBook.operation(), 0d);

			playerMenuUtility.setMenuTransitioning(true);
			playerMenuUtility.getOwner().closeInventory();

			playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
				.editAttributeModifierValue(valueToManualEdit)
				.setReturnInventory(EditValueMenu.class)
				.setTargetOperation(clickedInfoBook.operation())
				.openSign());
			MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_ATTRIBUTE_MODIFIER_INFORMATION);
			return;
		}

		if (isRight(clickType)) {
			EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues();
			operationDoubleValues.remove(clickedInfoBook.operation());
			setMenuItems();
		}
	}

	private boolean isLeft(ClickType type) {
		return type == ClickType.LEFT || type == ClickType.SHIFT_LEFT || type == ClickType.WINDOW_BORDER_LEFT;
	}

	private boolean isRight(ClickType type) {
		return type == ClickType.RIGHT || type == ClickType.SHIFT_RIGHT || type == ClickType.WINDOW_BORDER_RIGHT;
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		for (SlotItemWrapper.SlotItemOperationValueEdit slotItem : SLOT_ITEMS) {
			Double currentValueForOperation = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues().get(slotItem.operation());
			ItemStack itemToSet = ItemStackCreateHelper.fillEditValueItemWithLoreValues(slotItem, currentValueForOperation);
			this.inventory.setItem(slotItem.slot(), itemToSet);
		}

		for (SlotItemWrapper.SlotItemOperationExplanationValueEdit infoBook : INFO_BOOKS) {
			Double currentValueForOperation = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues().get(infoBook.operation());
			ItemStack itemToSet = ItemStackCreateHelper.fillInfoBookItemWithValues(infoBook, currentValueForOperation);
			this.inventory.setItem(infoBook.slot(), itemToSet);
		}

		updateConfirmSlot();
		setFillerGlass();
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return GlobalAttributeModifierItems.confirmValues.clone();
	}
}
