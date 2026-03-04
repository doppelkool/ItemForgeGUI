package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.AttributeModifierManager;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems.ModifyAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ObservableObject;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifersMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifiersMenus.ModifyAttributeModifierMenus.EditValueMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ModifyAttributeModifierMenu extends ConfirmableMenu {

	private final Map<SlotItemWrapper.SlotItem, EquipmentSlotGroup> slotItemToEquipmentSlot = Map.of(
		new SlotItemWrapper.SlotItem(10, ModifyAttributeModifierItems.mainHandSlot), EquipmentSlotGroup.MAINHAND,
		new SlotItemWrapper.SlotItem(11, ModifyAttributeModifierItems.offHandSlot), EquipmentSlotGroup.OFFHAND,

		new SlotItemWrapper.SlotItem(13, ModifyAttributeModifierItems.headSlot), EquipmentSlotGroup.HEAD,
		new SlotItemWrapper.SlotItem(14, ModifyAttributeModifierItems.chestSlot), EquipmentSlotGroup.CHEST,
		new SlotItemWrapper.SlotItem(15, ModifyAttributeModifierItems.legSlot), EquipmentSlotGroup.LEGS,
		new SlotItemWrapper.SlotItem(16, ModifyAttributeModifierItems.bootSlot), EquipmentSlotGroup.FEET
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
		ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
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
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		ItemStackModifyHelper.addAttributeModifierToItem(
			itemStack,
			this.playerMenuUtility.getModifyAttributeStorage());
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		boolean validateAttributeSlotGroups = ConfigManager.getInstance().isValidateAttributeSlotGroups();

		ObservableObject<ItemStack> itemInHand = this.playerMenuUtility.getItemInHand();
		Set<EquipmentSlotGroup> equippableSlotGroups = ItemStackCreateHelper.getEquippableSlotGroups(itemInHand.get());

		Attribute attribute = this.playerMenuUtility.getModifyAttributeStorage().getAttribute();
		String formattedAttributeKey = ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey());

		Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> modifierValues = this.playerMenuUtility.getModifyAttributeStorage().getModifierValues();

		for (Map.Entry<SlotItemWrapper.SlotItem, EquipmentSlotGroup> entry : slotItemToEquipmentSlot.entrySet()) {
			ItemStack item = entry.getKey().item().clone();
			EquipmentSlotGroup value = entry.getValue();

			if (validateAttributeSlotGroups && !equippableSlotGroups.contains(value)) {
				item = ItemStackCreateHelper.notAvailable(item);
			}

			prepareSlotItem(item, formattedAttributeKey, modifierValues, attribute, value);
			this.inventory.setItem(entry.getKey().slot(), item);
		}

		updateConfirmSlot();
		setFillerGlass();
	}

	private void prepareSlotItem(ItemStack slotItem, String formattedAttributeKey, Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> modifierValues, Attribute attribute, EquipmentSlotGroup value) {
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(
			slotItem,
			ItemStackCreateHelper.LoreVariable.CURRENT_ATTRIBUTE,
			formattedAttributeKey
		);

		EnumMap<AttributeModifier.Operation, Double> operationDoubleEnumMap = modifierValues.get(value);
		AttributeModifierManager.applyAttributeModifierValuesLore(slotItem, attribute, operationDoubleEnumMap);
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return AttributeSelectionItems.confirmSlots.clone();
	}
}
