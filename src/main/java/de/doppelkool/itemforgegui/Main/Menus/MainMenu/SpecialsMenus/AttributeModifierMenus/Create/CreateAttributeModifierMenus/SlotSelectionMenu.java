package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.SlotSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SlotSelectionMenu extends ConfirmableMenu {

	private final Map<SlotItemWrapper.SlotItem, EquipmentSlot> slotItemToEquipmentSlot = Map.of(
		new SlotItemWrapper.SlotItem(10, SlotSelectionItems.mainHandSlot), EquipmentSlot.HAND,
		new SlotItemWrapper.SlotItem(11, SlotSelectionItems.offHandSlot), EquipmentSlot.OFF_HAND,

		new SlotItemWrapper.SlotItem(13, SlotSelectionItems.headSlot), EquipmentSlot.HEAD,
		new SlotItemWrapper.SlotItem(14, SlotSelectionItems.chestSlot), EquipmentSlot.CHEST,
		new SlotItemWrapper.SlotItem(15, SlotSelectionItems.legSlot), EquipmentSlot.LEGS,
		new SlotItemWrapper.SlotItem(16, SlotSelectionItems.bootSlot), EquipmentSlot.FEET
	);

	public SlotSelectionMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);
	}

	@Override
	public String getMenuName() {
		return "Slot Selection";
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
		if (super.handleBack(e.getSlot(), null, CreateAttributeModifierMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(), null, CreateAttributeModifierMenu::new)) {
			return;
		}

		ItemStack clickedItem = this.inventory.getItem(e.getSlot());

		boolean currentlyActivated = ItemStackModifyHelper.hasGlow(clickedItem);
		ItemStackModifyHelper.setGlow(clickedItem, !currentlyActivated);

		extractAndSaveToggledEquipmentSlots();
		updateConfirmSlot();
	}

	private void extractAndSaveToggledEquipmentSlots() {
		slotItemToEquipmentSlot.forEach((key, value) -> playerMenuUtility
			.getAttributeStorage()
			.getSlotMap()
			.put(value, ItemStackModifyHelper.hasGlow(inventory.getItem(key.slot()))));
	}

	@Override
	public boolean isConfirmable() {
		return playerMenuUtility.getAttributeStorage().getSlotMap().containsValue(true);
	}

	@Override
	public ItemStack getConfirmableItem() {
		return isConfirmable()
			? SlotSelectionItems.confirmSlots.clone()
			: SlotSelectionItems.confirmSlots_deactivated.clone();
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		boolean validateAttributeSlotGroups = ConfigManager.getInstance().isValidateAttributeSlotGroups();
		Set<EquipmentSlotGroup> equippableSlotGroups = ItemStackCreateHelper.getEquippableSlotGroups(this.playerMenuUtility.getItemInHand().get());

		slotItemToEquipmentSlot.forEach((key, value) -> {
			ItemStack equipmentSlotItem = key.item().clone();
			boolean isActivated = this.playerMenuUtility.getAttributeStorage().getSlotMap().get(value);

			if (validateAttributeSlotGroups && !equippableSlotGroups.contains(value.getGroup())) {
				equipmentSlotItem = ItemStackCreateHelper.notAvailable(equipmentSlotItem);
			} else {
				ItemStackModifyHelper.setGlow(equipmentSlotItem, isActivated);
			}

			this.inventory.setItem(key.slot(), equipmentSlotItem);
		});

		updateConfirmSlot();
		setFillerGlass();
	}
}
