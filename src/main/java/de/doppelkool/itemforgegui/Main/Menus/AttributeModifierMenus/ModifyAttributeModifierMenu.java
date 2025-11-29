package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AddAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.AttributeSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.SlotSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.OperationSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyCurrentValueVariableInLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ModifyAttributeModifierMenu extends ConfirmableMenu {
	public ModifyAttributeModifierMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);
	}

	@Override
	public String getMenuName() {
		return "Modify Attribute Modifier";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	private boolean isValueSelectionActivated() {
		return this.playerMenuUtility.getAttributeStorage().getAttribute() != null;
	}

	private boolean isSlotSelectionActivated() {
		return this.playerMenuUtility.getAttributeStorage().getAttribute() != null;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			this.playerMenuUtility.setAttributeStorage(null);
			return;
		}
		if (super.handleBack(e.getSlot(),
			null,
			ActiveAttributeModifersMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			() -> {
				ItemStackModifyHelper.modifyAttributeModifierOnItem(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(), this.playerMenuUtility.getAttributeStorage());
				this.playerMenuUtility.setAttributeStorage(null);
			},
			ActiveAttributeModifersMenu::new)) {
			return;
		}

		//TODO slots in setting and executing
		if(e.getSlot() == 11) {
			new AttributeSelectionMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (isValueSelectionActivated() && e.getSlot() == 13) {
			new OperationSelectionMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (isSlotSelectionActivated() && e.getSlot() == 15) {
			new SlotSelectionMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	private ItemStack attributeSelection() {
		ItemStack attributeSelection = AddAttributeModifierItems.attributeSelection.clone();

		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();
		String loreStringForAttribute = attribute != null ? ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey()) : "-";
		modifyCurrentValueVariableInLore(attributeSelection, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE,loreStringForAttribute);

		return attributeSelection;
	}

	private ItemStack valueSelection() {
		if(!isValueSelectionActivated()) {
			return AddAttributeModifierItems.valueSelection_deactivated.clone();
		}

		EnumMap<AttributeModifier.Operation, Double> modifierValues = this.playerMenuUtility.getAttributeStorage().getModifierValues();
		Double operationAddNumber = modifierValues.get(AttributeModifier.Operation.ADD_NUMBER);
		Double operationAddScalar = modifierValues.get(AttributeModifier.Operation.ADD_SCALAR);
		Double operationMultiplyScalar1 = modifierValues.get(AttributeModifier.Operation.MULTIPLY_SCALAR_1);

		String loreStringForAttributeAddNumber = operationAddNumber != null ? String.valueOf(operationAddNumber) : "-";
		String loreStringForAttributeAddScalar = operationAddScalar != null ? String.valueOf(operationAddScalar) : "-";
		String loreStringForAttributeMultiplyScalar1 = operationMultiplyScalar1 != null ? String.valueOf(operationMultiplyScalar1) : "-";

		ItemStack valueSelection = AddAttributeModifierItems.valueSelection.clone();

		modifyCurrentValueVariableInLore(valueSelection,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__ADD_NUMBER,loreStringForAttributeAddNumber);
		modifyCurrentValueVariableInLore(valueSelection,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__ADD_SCALAR,loreStringForAttributeAddScalar);
		modifyCurrentValueVariableInLore(valueSelection,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__MULTIPLY_SCALAR_1,loreStringForAttributeMultiplyScalar1);

		return valueSelection;
	}

	private ItemStack slotSelection() {
		if(!isSlotSelectionActivated()) {
			return AddAttributeModifierItems.slotSelection_deactivated.clone();
		}

		ItemStack slotSelection = AddAttributeModifierItems.slotSelection.clone();
		Map<EquipmentSlot, Boolean> slotMap = this.playerMenuUtility.getAttributeStorage().getSlotMap();

		String slotListString = slotMap.entrySet().stream()
			.filter(Map.Entry::getValue)
			.map(e -> ItemStackModifyHelper.formatCAPSNames(e.getKey().name()))
			.collect(Collectors.collectingAndThen(
				Collectors.joining(", "),
				s -> s.isEmpty() ? "-" : s
			));

		modifyCurrentValueVariableInLore(slotSelection,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE,slotListString);

		return slotSelection;
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		this.inventory.setItem(11, attributeSelection());
		this.inventory.setItem(13, valueSelection());
		this.inventory.setItem(15, slotSelection());
		updateConfirmSlot();
		setFillerGlass();
	}

	@Override
	protected boolean isConfirmable() {
		//TODO combine literally all isConfirmable in the 3 submenus together instead of retyping the criteria here
		PlayerMenuUtility.AttributeStorage attributeStorage = this.playerMenuUtility.getAttributeStorage();
		return attributeStorage.getAttribute() != null &&
			attributeStorage.getSlotMap().containsValue(true) &&
			!attributeStorage.getModifierValues().isEmpty();
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return isConfirmable()
			? AttributeSelectionItems.confirmSlots.clone()
			: AttributeSelectionItems.confirmSlots_deactivated.clone();
	}
}
