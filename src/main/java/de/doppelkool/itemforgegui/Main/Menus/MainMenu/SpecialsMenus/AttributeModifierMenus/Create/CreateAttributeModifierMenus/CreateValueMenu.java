package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.GlobalAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems.EditValuesItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.ChatColor;
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
public class CreateValueMenu extends ConfirmableMenu {

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

	private static final List<SlotItemWrapper.SlotItemOperationValueEdit> INFO_BOOKS = List.of(
		new SlotItemWrapper.SlotItemOperationValueEdit(13, EditValuesItems.infoBook,AttributeModifier.Operation.ADD_NUMBER, null),
		new SlotItemWrapper.SlotItemOperationValueEdit(22, EditValuesItems.infoBook,AttributeModifier.Operation.ADD_SCALAR, null),
		new SlotItemWrapper.SlotItemOperationValueEdit(31, EditValuesItems.infoBook,AttributeModifier.Operation.MULTIPLY_SCALAR_1, null)
	);

	public CreateValueMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 53);
	}

	@Override
	public String getMenuName() {
		return "Edit Values: " + ItemStackModifyHelper.formatTranslationalNames(this.playerMenuUtility.getAttributeStorage().getAttribute().getTranslationKey());
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
			CreateAttributeModifierMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			null,
			CreateAttributeModifierMenu::new)) {
			return;
		}

		Optional<SlotItemWrapper.SlotItemOperationValueEdit> clickedInfoBook = INFO_BOOKS.stream()
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
		EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getAttributeStorage().getOperationDoubleValues();
		double oldValue = operationDoubleValues.getOrDefault(slotItemOperationValueEdit.operation(), 0d);

		BigDecimal newValue = BigDecimal.valueOf(oldValue)
			.add(BigDecimal.valueOf(slotItemOperationValueEdit.valueEdit()))
			.setScale(3, RoundingMode.HALF_UP);

		if(newValue.equals(BigDecimal.ZERO)) {
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
	private void infoBookClicked(SlotItemWrapper.SlotItemOperationValueEdit clickedInfoBook, ClickType clickType) {
		if (isLeft(clickType)) {
			EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getAttributeStorage().getOperationDoubleValues();
			double valueToManualEdit = operationDoubleValues.getOrDefault(clickedInfoBook.operation(), 0d);

			playerMenuUtility.setMenuTransitioning(true);
			playerMenuUtility.getOwner().closeInventory();

			playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
				.editAttributeModifierValue(valueToManualEdit)
				.setReturnInventory(CreateValueMenu.class)
				.setTargetOperation(clickedInfoBook.operation())
				.openSign());
			MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_ATTRIBUTE_MODIFIER_INFORMATION);
		} else if (isRight(clickType)) {
			EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = this.playerMenuUtility.getAttributeStorage().getOperationDoubleValues();
			operationDoubleValues.remove(clickedInfoBook.operation());
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

		//ToDo Enhance the items description to a point where it explains it better
		for (SlotItemWrapper.SlotItemOperationValueEdit slotItem : SLOT_ITEMS) {
			ItemStack itemToSet = fillEditValueItemWithLoreValues(slotItem);
			this.inventory.setItem(slotItem.slot(), itemToSet);
		}

		for (SlotItemWrapper.SlotItemOperationValueEdit infoBook : INFO_BOOKS) {
			ItemStack itemToSet = fillInfoBookItemWithValues(infoBook);
			this.inventory.setItem(infoBook.slot(), itemToSet);
		}

		updateConfirmSlot();
		setFillerGlass();
	}

	private ItemStack fillEditValueItemWithLoreValues(SlotItemWrapper.SlotItemOperationValueEdit slotItem) {
		ItemStack item = slotItem.item().clone();
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE, "" + slotItem.valueEdit());
		return item;
	}

	private ItemStack fillInfoBookItemWithValues(SlotItemWrapper.SlotItemOperationValueEdit infoBook) {
		ItemStack item = infoBook.item().clone();

		AttributeModifier.Operation operation = infoBook.operation();
		String operationValueRepresent = ItemStackModifyHelper.formatCAPSNames(operation.name());
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_OPERATION, operationValueRepresent);

		Double currentValueForOperation = this.playerMenuUtility.getAttributeStorage()
			.getOperationDoubleValues()
			.get(operation);
		String loreValueRepresent;
		if (currentValueForOperation != null) {
			loreValueRepresent = currentValueForOperation.toString();
			ItemStackModifyHelper.setGlow(item, true);
		} else {
			loreValueRepresent = ChatColor.GRAY + "-";
		}
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE, loreValueRepresent);

		return item;
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return GlobalAttributeModifierItems.confirmValues.clone();
	}
}
