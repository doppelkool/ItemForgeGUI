package de.doppelkool.itemforgegui.Main.Menus.MainMenu;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to change the amount of the item
 *
 * @author doppelkool | github.com/doppelkool
 */
@Slf4j
public class AmountMenu extends EditNumberMenu {

	public AmountMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public void setMenuItems() {
		super.setMenuItems();

		//Custom Item for default stack size
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		if (itemStack.getMaxStackSize() != 1) {
			ItemStack toDefault = GlobalItems.toDefault.clone();
			toDefault.setAmount(itemStack.getMaxStackSize());
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
		this.playerMenuUtility.getItemInHand().get().setAmount(1);
	}

	@Override
	protected void handleToMax() {
		this.playerMenuUtility.getItemInHand().get().setAmount(99);
	}

	@Override
	protected void handleMinus100() {
	}

	@Override
	protected void handleMinus10() {
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		int targetAmount = Math.max(itemStack.getAmount() - 10, 1);
		itemStack.setAmount(targetAmount);
	}

	@Override
	protected void handleMinus1() {
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		int targetAmount = Math.max(itemStack.getAmount() - 1, 1);
		itemStack.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus1() {
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		int targetAmount = itemStack.getAmount() + 1;
		itemStack.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus10() {
		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		int targetAmount = itemStack.getAmount() + 10;
		itemStack.setAmount(targetAmount);
	}

	@Override
	protected void handlePlus100() {
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editAmount(this.playerMenuUtility.getItemInHand().get().getAmount())
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_AMOUNT_INFORMATION);
	}

	@Override
	protected boolean onCustomItemClick(InventoryClickEvent e) {
		if (e.getSlot() == 13) {
			this.playerMenuUtility.getItemInHand().get().setAmount(this.playerMenuUtility.getItemInHand().get().getMaxStackSize());
			return true;
		}
		return false;
	}
}
