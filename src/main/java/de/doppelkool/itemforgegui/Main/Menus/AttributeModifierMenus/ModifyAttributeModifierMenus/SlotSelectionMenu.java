package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.SlotSelectionMenu.SlotSelectionItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.CreateAttributeModifierMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SlotSelectionMenu extends ConfirmableMenu {

	private final Map<SlotItemWrapper.SlotItem, EquipmentSlot> slotItemToEquipmentSlot = Map.of(
		new SlotItemWrapper.SlotItem(10, SlotSelectionItems.headSlot), EquipmentSlot.HEAD,
		new SlotItemWrapper.SlotItem(11, SlotSelectionItems.chestSlot), EquipmentSlot.CHEST,
		new SlotItemWrapper.SlotItem(12, SlotSelectionItems.legSlot), EquipmentSlot.LEGS,
		new SlotItemWrapper.SlotItem(13, SlotSelectionItems.bootSlot), EquipmentSlot.FEET,
		new SlotItemWrapper.SlotItem(15, SlotSelectionItems.mainHandSlot), EquipmentSlot.HAND,
		new SlotItemWrapper.SlotItem(16, SlotSelectionItems.offHandSlot), EquipmentSlot.OFF_HAND
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

		slotItemToEquipmentSlot.forEach((key, value) -> {
			ItemStack equipmentSlotItem = key.item().clone();
			boolean isActivated = this.playerMenuUtility.getAttributeStorage().getSlotMap().get(value);

			ItemStackModifyHelper.setGlow(equipmentSlotItem, isActivated);
			this.inventory.setItem(key.slot(), equipmentSlotItem);
		});

		updateConfirmSlot();
		setFillerGlass();
	}
}
