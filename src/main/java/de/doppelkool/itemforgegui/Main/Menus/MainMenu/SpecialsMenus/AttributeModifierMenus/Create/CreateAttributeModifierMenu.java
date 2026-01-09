package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.CustomItemManager.AttributeModifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AddAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus.AttributeSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus.CreateValueMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus.SlotSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifersMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyCurrentValueVariableInLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CreateAttributeModifierMenu extends ConfirmableMenu {
	public CreateAttributeModifierMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);

		loadOrCreateAttributeStorage();
	}

	private void loadOrCreateAttributeStorage() {
		PlayerMenuUtility.AttributeStorage attributeStorage = this.playerMenuUtility.getAttributeStorage();
		if(attributeStorage == null) {
			this.playerMenuUtility.setAttributeStorage(new PlayerMenuUtility.AttributeStorage());
		}
	}

	@Override
	public String getMenuName() {
		return "Create Attribute Modifier";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	private boolean isSlotSelectionActivated() {
		return this.playerMenuUtility.getAttributeStorage().getAttribute() != null;
	}

	private boolean isAreSlotsSelected() {
		EnumMap<EquipmentSlot, Boolean> slotMap = this.playerMenuUtility.getAttributeStorage().getSlotMap();
		return isSlotSelectionActivated() && slotMap != null && slotMap.containsValue(Boolean.TRUE);
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			this.playerMenuUtility.setAttributeStorage(null);
			return;
		}
		if (super.handleBack(e.getSlot(),
			() -> this.playerMenuUtility.setAttributeStorage(null),
			this::determineBackMenu)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			() -> {
				ItemStackModifyHelper.addAttributeModifierToItem(this.playerMenuUtility.getItemInHand().get(), this.playerMenuUtility.getAttributeStorage());
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
		if (isSlotSelectionActivated() && e.getSlot() == 13) {
			new SlotSelectionMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (isAreSlotsSelected() && e.getSlot() == 15) {
			new CreateValueMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	private Menu determineBackMenu(PlayerMenuUtility playerMenuUtility) {
		Multimap<Attribute, AttributeModifier> attributeModifiers = playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta().getAttributeModifiers();
		if(attributeModifiers == null || attributeModifiers.isEmpty()) {
			return new SpecialsMenu(this.playerMenuUtility);
		} else {
			return new ActiveAttributeModifersMenu(this.playerMenuUtility);
		}
	}

	private ItemStack attributeSelection() {
		ItemStack attributeSelection = AddAttributeModifierItems.attributeSelection.clone();

		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();
		String loreStringForAttribute = attribute != null ? ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey()) : "-";
		modifyCurrentValueVariableInLore(attributeSelection,ItemStackCreateHelper.LoreVariable.CURRENT_VALUE,loreStringForAttribute);

		return attributeSelection;
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

	private ItemStack valueSelection() {
		if(!isAreSlotsSelected()) {
			return AddAttributeModifierItems.valueSelection_deactivated.clone();
		}

		EnumMap<AttributeModifier.Operation, Double> modifierValues = this.playerMenuUtility.getAttributeStorage().getOperationDoubleValues();
		Collection<AttributeModifier> attributeModifiers = modifierValues.entrySet().stream()
			.map(entry ->
				new AttributeModifier(
					Main.getPlugin().getRandomKey(),
					entry.getValue(),
					entry.getKey(),
					EquipmentSlotGroup.ANY))
			.toList();

		ItemStack valueSelection = AddAttributeModifierItems.valueSelection.clone();
		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();

		if(!modifierValues.isEmpty()) {
			AttributeModifierManager.applyAttributeModifierValuesLore(valueSelection, attribute, attributeModifiers);
		} else {
			//todo add empty line
		}

		ItemStackCreateHelper.modifyCurrentValueVariableInLore(
			valueSelection,
			ItemStackCreateHelper.LoreVariable.CURRENT_ATTRIBUTE,
			ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey())
		);

		return valueSelection;
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		this.inventory.setItem(11, attributeSelection());
		this.inventory.setItem(13, slotSelection());
		this.inventory.setItem(15, valueSelection());
		updateConfirmSlot();
		setFillerGlass();
	}

	@Override
	protected boolean isConfirmable() {
		//TODO combine literally all isConfirmable in the 3 submenus together instead of retyping the criteria here
		PlayerMenuUtility.AttributeStorage attributeStorage = this.playerMenuUtility.getAttributeStorage();
		return attributeStorage.getAttribute() != null &&
			attributeStorage.getSlotMap().containsValue(true) &&
			!attributeStorage.getOperationDoubleValues().isEmpty();
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return isConfirmable()
			? AttributeSelectionItems.confirmSlots.clone()
			: AttributeSelectionItems.confirmSlots_deactivated.clone();
	}
}
