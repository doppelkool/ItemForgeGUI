package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to change the amount of the item
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AmountMenu extends EditNumberMenu {

	private final ItemStack item;

	public AmountMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		this.item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
	}

	@Override
	public void setMenuItems() {
		super.setMenuItems();

		//Custom Item for default stack size
		if (this.item.getMaxStackSize() != 1) {
			ItemStack toDefault = ItemStacks.toDefault.clone();
			toDefault.setAmount(this.item.getMaxStackSize());
			this.inventory.setItem(13, toDefault);
		}
	}

	@Override
	public String getMenuName() {
		return "Edit Amount";
	}

	@Override
	//Swapped with toOne
	protected void handleToZero() {
		this.item.setAmount(1);
	}

	@Override
	protected void handleToMax() {
		this.item.setAmount(99);
	}

	@Override
	protected void handleMinus100() {
	}

	@Override
	protected void handleMinus10() {
		int targetAmount = Math.max(this.item.getAmount() - 10, 1);
		this.item.setAmount(targetAmount);
	}

	@Override
	protected void handleMinus1() {
		int targetAmount = Math.max(this.item.getAmount() - 1, 1);
		this.item.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus1() {
		int targetAmount = this.item.getAmount() + 1;
		this.item.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus10() {
		int targetAmount = this.item.getAmount() + 10;
		this.item.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus100() {
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editAmount(this.item.getAmount())
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_AMOUNT_INFORMATION);
	}

	@Override
	protected boolean onCustomItemClick(InventoryClickEvent e) {
		if (e.getSlot() == 13) {
			this.item.setAmount(this.item.getMaxStackSize());
			return true;
		}
		return false;
	}
}
