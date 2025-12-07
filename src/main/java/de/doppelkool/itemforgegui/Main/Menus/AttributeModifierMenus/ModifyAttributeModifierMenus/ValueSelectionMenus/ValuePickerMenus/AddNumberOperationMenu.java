package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.OperationSelectionMenu.ValueSelectionMenu.ValuePickerMenu.OperationConfirmationItems;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.OperationSelectionMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AddNumberOperationMenu extends ConfirmableMenu {

	private double currentValue;

	protected List<SlotItemWrapper.SlotItemEvent> SLOT_ITEMS = List.of(
		new SlotItemWrapper.SlotItemEvent(9, GlobalItems.minus20, e -> handleMinus20()),
		new SlotItemWrapper.SlotItemEvent(10, GlobalItems.minus10, e -> handleMinus10()),
		new SlotItemWrapper.SlotItemEvent(11, GlobalItems.minus1, e -> handleMinus1()),
		new SlotItemWrapper.SlotItemEvent(12, GlobalItems.minusPoint1, e -> handleMinusPoint1()),
		new SlotItemWrapper.SlotItemEvent(14, GlobalItems.plusPoint1, e -> handlePlusPoint1()),
		new SlotItemWrapper.SlotItemEvent(15, GlobalItems.plus1, e -> handlePlus1()),
		new SlotItemWrapper.SlotItemEvent(16, GlobalItems.plus10, e -> handlePlus10()),
		new SlotItemWrapper.SlotItemEvent(17, GlobalItems.plus20, e -> handlePlus20()),
		new SlotItemWrapper.SlotItemEvent(22, GlobalItems.customValue, e -> handleCustomNumber(e))
	);

	public AddNumberOperationMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 35);
		Double currentValue = this.playerMenuUtility.getAttributeStorage().getModifierValues().get(AttributeModifier.Operation.ADD_NUMBER);
		this.currentValue = currentValue != null ? currentValue : 0.0;
	}

	@Override
	public String getMenuName() {
		return "Edit value | Add Number Operation";
	}

	@Override
	public int getSlots() {
		return 9 * 4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if(super.handleBack(e.getSlot(), null, OperationSelectionMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			() -> this.playerMenuUtility.getAttributeStorage().getModifierValues().put(AttributeModifier.Operation.ADD_NUMBER, this.currentValue),
			OperationSelectionMenu::new)) {
			return;
		}

		SLOT_ITEMS.stream()
			.filter(si -> si.slot() == e.getSlot())
			.findFirst()
			.orElseThrow()
			.onClick()
			.accept(e);
	}

	@Override
	protected boolean isConfirmable() {
		return this.currentValue != 0.0;
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return isConfirmable()
			? OperationConfirmationItems.confirmSelectedValue_Activated.clone()
			: OperationConfirmationItems.confirmSelectedValue_Deactivated.clone();
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		this.inventory.setItem(4, getInfoBook());
		for (SlotItemWrapper.SlotItemEvent item : SLOT_ITEMS)
			this.inventory.setItem(item.slot(), item.item());
		updateConfirmSlot();
		setFillerGlass();
	}

	private ItemStack getInfoBook() {
		ItemStack infoCurrentValuesBook = OperationConfirmationItems.infoCurrentValuesBook.clone();
		ItemStackCreateHelper.modifyLore(infoCurrentValuesBook,
			ChatColor.GRAY + "-> " + ChatColor.YELLOW + "Attribute: " + ItemStackModifyHelper.formatTranslationalNames(this.playerMenuUtility.getAttributeStorage().getAttribute().getTranslationKey()),
			ChatColor.GRAY + "-> " + ChatColor.YELLOW + "Operation: Add Number Operation",
			ChatColor.GRAY + "-> " + ChatColor.YELLOW + "Value: " + this.currentValue
		);
		return infoCurrentValuesBook;
	}

	private void handleMinus20() {
		roundUpAndUpdate(-20);
		this.reloadMenu();
	}
	private void handleMinus10() {
		roundUpAndUpdate(-10);
		this.reloadMenu();
	}
	private void handleMinus1() {
		roundUpAndUpdate(-1);
		this.reloadMenu();
	}
	private void handleMinusPoint1() {
		roundUpAndUpdate(-0.1);
		this.reloadMenu();
	}
	private void handlePlusPoint1() {
		roundUpAndUpdate(0.1);
		this.reloadMenu();
	}
	private void handlePlus1() {
		roundUpAndUpdate(1);
		this.reloadMenu();
	}
	private void handlePlus10() {
		roundUpAndUpdate(10);
		this.reloadMenu();
	}
	private void handlePlus20() {
		roundUpAndUpdate(20);
		this.reloadMenu();
	}

	private void roundUpAndUpdate(double change) {
		this.currentValue = Math.ceil((this.currentValue + change) * 10) / 10.0;
	}

	private void reloadMenu() {
		this.inventory.clear();
		this.setMenuItems();
	}

	private void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editAttributeModifierValue(this.currentValue)
			.setTargetOperation(AttributeModifier.Operation.ADD_NUMBER)
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_ATTRIBUTE_MODIFIER_INFORMATION);
	}
}
