package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AddAttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeModifierItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.AttributeSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.SlotSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenu;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsPreventionFlagsMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyCurrentValueVariableInLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CreateAttributeModifierMenu extends Menu {

	private final List<SlotItemWrapper.SlotItemExecute> SLOT_TO_ITEMS;

	public CreateAttributeModifierMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);

		loadOrCreateAttributeStorage();

		SLOT_TO_ITEMS = List.of(
			new SlotItemWrapper.SlotItemExecute(11, attributeSelection(), () -> new AttributeSelectionMenu(playerMenuUtility).open()),
			new SlotItemWrapper.SlotItemExecute(13, valueSelection(), () -> new ValueSelectionMenu(playerMenuUtility).open()),
			new SlotItemWrapper.SlotItemExecute(15, slotSelection(), () -> new SlotSelectionMenu(playerMenuUtility).open()),
			new SlotItemWrapper.SlotItemExecute(26, AttributeModifierItems.confirmAddNewAttributeModifier_Activated, () -> new SpecialsPreventionFlagsMenu(playerMenuUtility).open())
		);
	}

	private ItemStack attributeSelection() {
		ItemStack attributeSelection = AddAttributeModifierItems.attributeSelection.clone();

		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();
		String loreStringForAttribute = attribute != null ? ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey()) : "-";
		modifyCurrentValueVariableInLore(attributeSelection, loreStringForAttribute);

		return attributeSelection;
	}

	private ItemStack valueSelection() {
		return AddAttributeModifierItems.valueSelection.clone();
	}

	private ItemStack slotSelection() {
		ItemStack slotSelection = AddAttributeModifierItems.slotSelection.clone();
		Map<EquipmentSlot, Boolean> slotMap = this.playerMenuUtility.getAttributeStorage().getSlotMap();

		String slotListString = slotMap.entrySet().stream()
			.filter(Map.Entry::getValue)
			.map(e -> ItemStackModifyHelper.formatCAPSNames(e.getKey().name()))
			.collect(Collectors.collectingAndThen(
				Collectors.joining(", "),
				s -> s.isEmpty() ? "-" : s
			));

		modifyCurrentValueVariableInLore(slotSelection, slotListString);

		return slotSelection;
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

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			this.playerMenuUtility.setAttributeStorage(null);
			return;
		}
		if (super.handleBack(e.getSlot(),
			() -> this.playerMenuUtility.setAttributeStorage(null),
			SpecialsMenu::new)) {
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
	public void setMenuItems() {
		addMenuBorder();
		SLOT_TO_ITEMS.forEach((slotItem) -> this.inventory.setItem(slotItem.slot(), slotItem.item()));
		setFillerGlass();
	}
}
