package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.EditValueMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ModifyAttributeModifierMenu extends ConfirmableMenu {

	private final Map<SlotItemWrapper.SlotItem, EquipmentSlotGroup> slotItemToEquipmentSlot = Map.of(
		new SlotItemWrapper.SlotItem(10, ModifyAttributeModifierItems.headSlot), EquipmentSlotGroup.HEAD,
		new SlotItemWrapper.SlotItem(11, ModifyAttributeModifierItems.chestSlot), EquipmentSlotGroup.CHEST,
		new SlotItemWrapper.SlotItem(12, ModifyAttributeModifierItems.legSlot), EquipmentSlotGroup.LEGS,
		new SlotItemWrapper.SlotItem(13, ModifyAttributeModifierItems.bootSlot), EquipmentSlotGroup.FEET,
		new SlotItemWrapper.SlotItem(15, ModifyAttributeModifierItems.mainHandSlot), EquipmentSlotGroup.MAINHAND,
		new SlotItemWrapper.SlotItem(16, ModifyAttributeModifierItems.offHandSlot), EquipmentSlotGroup.OFFHAND
	);

	public ModifyAttributeModifierMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);
		if (this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues() != null) {
			computeValuesFromSubMenuIntoStorage();
		} else {
			loadValuesFromItemIntoStorage();
		}
	}

	private void computeValuesFromSubMenuIntoStorage() {
		EquipmentSlotGroup selectedSlotGroup = this.playerMenuUtility.getModifyAttributeStorage().getSelectedSlotGroup();
		EnumMap<AttributeModifier.Operation, Double> valuesToCompute = this.playerMenuUtility.getModifyAttributeStorage().getOperationDoubleValues();

		this.playerMenuUtility.getModifyAttributeStorage()
			.getModifierValues()
			.computeIfAbsent(selectedSlotGroup, k -> new EnumMap<>(AttributeModifier.Operation.class))
			.putAll(valuesToCompute);

		this.playerMenuUtility.getModifyAttributeStorage().setOperationDoubleValues(null);
	}

	private void loadValuesFromItemIntoStorage() {
		boolean msgNoMultipleSameModifiersAllowed = false;
		Attribute byAttribute = this.playerMenuUtility.getModifyAttributeStorage().getAttribute();
		Collection<AttributeModifier> activeAttributeModifiersByAttribute = getActiveAttributeModifiersByAttribute(byAttribute);

		Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> modifierValues = this.playerMenuUtility.getModifyAttributeStorage().getModifierValues();
		for (AttributeModifier attributeModifier : activeAttributeModifiersByAttribute) {
			Map<AttributeModifier.Operation, Double> operationDoubleMap = modifierValues.get(attributeModifier.getSlotGroup());

			//Warning for not supported feature (multiple modifiers identified by identical keys)
			if(operationDoubleMap.get(attributeModifier.getOperation()) != null) {
				msgNoMultipleSameModifiersAllowed = true;
			}

			operationDoubleMap.put(attributeModifier.getOperation(), attributeModifier.getAmount());
		}

		if(msgNoMultipleSameModifiersAllowed) {
			this.playerMenuUtility.getOwner().sendMessage(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_MANUAL_BUTTON, true));
		}

		this.playerMenuUtility.getModifyAttributeStorage().setModifierValues(modifierValues);
	}

	private Collection<AttributeModifier> getActiveAttributeModifiersByAttribute(Attribute attribute) {
		return this.playerMenuUtility.getOwner()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getAttributeModifiers(attribute);
	}

	@Override
	public String getMenuName() {
		return "Modify Attribute Modifier";
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
			ActiveAttributeModifersMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			this::applyAttributeModifiersOnItem,
			ActiveAttributeModifersMenu::new)) {
			return;
		}

		EquipmentSlotGroup clickedSlotGroup = slotItemToEquipmentSlot.entrySet().stream()
			.filter(si -> si.getKey().slot() == e.getSlot())
			.findFirst()
			.orElseThrow()
			.getValue();
		this.playerMenuUtility.getModifyAttributeStorage().setSelectedSlotGroup(clickedSlotGroup);
		this.playerMenuUtility.getModifyAttributeStorage().setOperationDoubleValues(
			this.playerMenuUtility.getModifyAttributeStorage().getModifierValues().get(clickedSlotGroup)
		);

		new EditValueMenu(this.playerMenuUtility)
			.open();
	}

	private void applyAttributeModifiersOnItem() {
		ItemStackModifyHelper.addAttributeModifierToItem(
			this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
			this.playerMenuUtility.getModifyAttributeStorage());
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		Attribute attribute = this.playerMenuUtility.getModifyAttributeStorage().getAttribute();
		Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> activeAttributeModifiersByAttributeAndSlot = this.playerMenuUtility.getModifyAttributeStorage().getModifierValues();

		slotItemToEquipmentSlot.forEach((key, value) -> {

			ItemStack equipmentSlotItem = key.item().clone();

			ItemStackCreateHelper.modifyCurrentValueVariableInLore(
				equipmentSlotItem,
				ItemStackCreateHelper.LoreVariable.CURRENT_ATTRIBUTE,
				ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey())
			);

			Map<AttributeModifier.Operation, ItemStackCreateHelper.LoreVariable> operationToLoreVariable = Map.of(
				AttributeModifier.Operation.ADD_NUMBER, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__ADD_NUMBER,
				AttributeModifier.Operation.ADD_SCALAR, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__ADD_SCALAR,
				AttributeModifier.Operation.MULTIPLY_SCALAR_1, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE__MULTIPLY_SCALAR_1
			);


			for (AttributeModifier.Operation operation : AttributeModifier.Operation.values()) {
				Double v = activeAttributeModifiersByAttributeAndSlot.get(value).get(operation);
				String valueToReplace = v == null ? "-" : String.valueOf(v);

				ItemStackCreateHelper.modifyCurrentValueVariableInLore(
					equipmentSlotItem,
					operationToLoreVariable.get(operation),
					valueToReplace
				);
			}

			this.inventory.setItem(key.slot(), equipmentSlotItem);
		});

		updateConfirmSlot();
		setFillerGlass();
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return AttributeSelectionItems.confirmSlots.clone();
	}
}
