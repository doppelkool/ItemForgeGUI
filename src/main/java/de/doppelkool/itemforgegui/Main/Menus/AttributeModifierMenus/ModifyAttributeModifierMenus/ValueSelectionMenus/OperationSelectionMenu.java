package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus;

import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.OperationSelectionMenu.OperationSelectionItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.CreateAttributeModifierMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.AttributeSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.SlotSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus.AddNumberOperationMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus.AddScalarOperationMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus.MultiplyScalar1OperationMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus.ValueSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyCurrentValueVariableInLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class OperationSelectionMenu extends ConfirmableMenu {

	private final List<SlotItemWrapper.SlotItemExecute> SLOT_TO_ITEMS;

	public OperationSelectionMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);

		SLOT_TO_ITEMS = List.of(
			new SlotItemWrapper.SlotItemExecute(11, addValueOperation(), () -> new AddNumberOperationMenu(playerMenuUtility).open()),
			new SlotItemWrapper.SlotItemExecute(13, addMultipliedBase(), () -> new AddScalarOperationMenu(playerMenuUtility).open()),
			new SlotItemWrapper.SlotItemExecute(15, addMultipliedTotal(), () -> new MultiplyScalar1OperationMenu(playerMenuUtility).open())
		);
	}

	private ItemStack addValueOperation() {
		return prepareOperationItem(
			OperationSelectionItems.operationAddNumber.clone(),
			AttributeModifier.Operation.ADD_NUMBER);
	}

	private ItemStack addMultipliedBase() {
		return prepareOperationItem(
			OperationSelectionItems.operationAddScalar.clone(),
			AttributeModifier.Operation.ADD_SCALAR);
	}

	private ItemStack addMultipliedTotal() {
		return prepareOperationItem(
			OperationSelectionItems.operationMultiplyScalarOne.clone(),
			AttributeModifier.Operation.MULTIPLY_SCALAR_1);
	}

	private ItemStack prepareOperationItem(ItemStack operationItem, AttributeModifier.Operation operation) {
		Map<AttributeModifier.Operation, Double> modifierValues = this.playerMenuUtility.getAttributeStorage().getModifierValues();

		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();
		String loreStringForAttribute =
			attribute != null
				? ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey())
				: "-";
		String loreStringForValue =
			modifierValues.containsKey(operation)
				? modifierValues.get(operation).toString()
				: "-";

		modifyCurrentValueVariableInLore(operationItem,ItemStackCreateHelper.LoreVariable.CURRENT_ATTRIBUTE,loreStringForAttribute);
		modifyCurrentValueVariableInLore(operationItem,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE,loreStringForValue);

		return operationItem;
	}

	@Override
	public String getMenuName() {
		return "Operation Selection";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
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
			//TODO After all operation menus maybe need another action here
			null,
			CreateAttributeModifierMenu::new)) {
			return;
		}

		SLOT_TO_ITEMS.stream()
			.filter(slotItemExecute -> slotItemExecute.slot() == e.getSlot())
			.findAny()
			.orElseThrow()
			.runnable()
			.run();
	}

	@Override
	protected boolean isConfirmable() {
		return !this.playerMenuUtility.getAttributeStorage().getModifierValues().isEmpty();
	}

	@Override
	protected ItemStack getConfirmableItem() {
		Logger.log(this.playerMenuUtility.getAttributeStorage().getModifierValues());

		return isConfirmable()
			? OperationSelectionItems.confirmAddNewAttributeModifierValue_Activated.clone()
			//TODO Replace valueMissing variable
			: OperationSelectionItems.confirmAddNewAttributeModifierValue_Deactivated.clone();
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		SLOT_TO_ITEMS.forEach((slotItem) -> this.inventory.setItem(slotItem.slot(), slotItem.item()));
		updateConfirmSlot();
		setFillerGlass();
	}
}
