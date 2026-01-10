package de.doppelkool.itemforgegui.Main.Menus.MainMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import lombok.extern.slf4j.Slf4j;
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

	private ItemStack itemStack;

	public AmountMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		itemStack = this.playerMenuUtility.getItemInHand().get();
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
		itemStack.setAmount(1);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handleToMax() {
		itemStack.setAmount(99);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handleMinus100() {
	}

	@Override
	protected void handleMinus10() {
		int targetAmount = Math.max(itemStack.getAmount() - 10, 1);
		itemStack.setAmount(targetAmount);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handleMinus1() {
		int targetAmount = Math.max(itemStack.getAmount() - 1, 1);
		itemStack.setAmount(targetAmount);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handlePlus1() {
		int targetAmount = itemStack.getAmount() + 1;
		itemStack.setAmount(targetAmount);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handlePlus10() {
		int targetAmount = itemStack.getAmount() + 10;
		itemStack.setAmount(targetAmount);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	protected void handlePlus100() {
	}

	@Override
	protected void handleCustomNumber(InventoryClickEvent e) {
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editAmount(itemStack.getAmount())
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_AMOUNT_INFORMATION);
	}

	@Override
	protected boolean onCustomItemClick(InventoryClickEvent e) {
		if (e.getSlot() == 13) {
			itemStack.setAmount(itemStack.getMaxStackSize());
			playerMenuUtility.getItemInHand().set(itemStack);
			return true;
		}
		return false;
	}
}
