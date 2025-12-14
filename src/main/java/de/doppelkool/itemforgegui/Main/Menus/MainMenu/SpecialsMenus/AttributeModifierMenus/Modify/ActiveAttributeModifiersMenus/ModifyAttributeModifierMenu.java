package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.AttributeModifierManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems.ModifyAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifersMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus.ModifyAttributeModifierMenus.EditValueMenu;
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
		Attribute byAttribute = this.playerMenuUtility.getModifyAttributeStorage().getAttribute();
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner()
			.getInventory()
			.getItemInMainHand();
		Collection<AttributeModifier> activeAttributeModifiersByAttribute = AttributeModifierManager.getActiveAttributeModifiersByAttribute(itemInMainHand, byAttribute);

		Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> modifierValues = this.playerMenuUtility.getModifyAttributeStorage().getModifierValues();
		for (AttributeModifier attributeModifier : activeAttributeModifiersByAttribute) {
			Map<AttributeModifier.Operation, Double> operationDoubleMap = modifierValues.get(attributeModifier.getSlotGroup());
			operationDoubleMap.put(attributeModifier.getOperation(), attributeModifier.getAmount());
		}

		this.playerMenuUtility.getModifyAttributeStorage().setModifierValues(modifierValues);
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

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		Attribute attribute = this.playerMenuUtility.getModifyAttributeStorage().getAttribute();
		Collection<AttributeModifier> activeAttributeModifiersByAttribute = AttributeModifierManager.getActiveAttributeModifiersByAttribute(itemInMainHand, attribute);

		slotItemToEquipmentSlot.forEach((key, value) -> {

			ItemStack equipmentSlotItem = key.item().clone();

			ItemStackCreateHelper.modifyCurrentValueVariableInLore(
				equipmentSlotItem,
				ItemStackCreateHelper.LoreVariable.CURRENT_ATTRIBUTE,
				ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey())
			);

			Collection<AttributeModifier> activeAttributeModifiersByAttributeAndSlot = AttributeModifierManager.filterBySlot(activeAttributeModifiersByAttribute, value);
			if(!activeAttributeModifiersByAttributeAndSlot.isEmpty()) {
				AttributeModifierManager.applyAttributeLore(equipmentSlotItem, attribute, activeAttributeModifiersByAttribute);
			} else {
				//todo add empty line
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
