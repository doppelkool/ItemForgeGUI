package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.EditNumberMenu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
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
	protected void handleMinus100() {}

	@Override
	protected void handleMinus10() {
		int targetLevel = Math.max(this.item.getAmount() - 10, 1);
		this.item.setAmount(targetLevel);
	}

	@Override
	protected void handleMinus1() {
		int targetLevel = Math.max(this.item.getAmount() - 1, 1);
		this.item.setAmount(targetLevel);
	}

	@Override
	protected void handlePlus1() {
		int targetLevel = this.item.getAmount() + 1;
		this.item.setAmount(targetLevel);
	}

	@Override
	protected void handlePlus10() {
		int targetLevel = this.item.getAmount() + 10;
		this.item.setAmount(targetLevel);
	}

	@Override
	protected void handlePlus100() {}

	@Override
	protected void onCustomItemClick(InventoryClickEvent e) {
		if(e.getSlot() == 13) {
			this.item.setAmount(this.item.getMaxStackSize());
		}
	}
}
