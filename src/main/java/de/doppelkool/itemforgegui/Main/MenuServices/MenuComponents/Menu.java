package de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class Menu implements InventoryHolder {

	protected PlayerMenuUtility playerMenuUtility;
	protected Inventory inventory;

	protected int closeInventorySlot;
	protected int backInventorySlot;

	//ToDo Set in inventorys where its not practicle or really ness* for example in Attribute Modifier submenus
	protected boolean showAPIItemInInventory = true;

	public Menu(PlayerMenuUtility playerMenuUtility) {
		this.playerMenuUtility = playerMenuUtility;

		closeInventorySlot = this.getSlots() - 9;
		backInventorySlot = this.getSlots() - 8;

		playerMenuUtility.setMenuTransitioning(true);
	}

	public abstract String getMenuName();

	public abstract int getSlots();

	public abstract void handleMenu(InventoryClickEvent e);

	public abstract void setMenuItems();

	public void open() {
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

		this.setMenuItems();
		this.setAPIItemInInventory();

		playerMenuUtility.getOwner().openInventory(inventory);
		playerMenuUtility.setCurrentMenu(this);
		playerMenuUtility.setMenuTransitioning(false);
	}

	private void setAPIItemInInventory() {
		boolean isItemEditThroughAPI = this.playerMenuUtility.getAPICallback().isPresent();

		Bukkit.getLogger().info("isItemEditThroughAPI: " + isItemEditThroughAPI);
		Bukkit.getLogger().info("showAPIItemInInventory: " + showAPIItemInInventory);

		if(isItemEditThroughAPI && showAPIItemInInventory) {
			this.inventory.setItem(8, this.playerMenuUtility.getItemInHand().get());
		}
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}

	public void setFillerGlass() {
		for (int i = 0; i < getSlots(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, GlobalItems.FILLER_GLASS);
			}
		}
	}

	public void addMenuBorder() {
		for (int i = 0; i < 9; i++) {
			inventory.setItem(i, GlobalItems.FILLER_GLASS); // Top row
			inventory.setItem(getSlots() - 9 + i, GlobalItems.FILLER_GLASS); // Bottom row
		}

		// Left and right columns (excluding corners already set)
		for (int i = 9; i < getSlots() - 9; i += 9) {
			inventory.setItem(i, GlobalItems.FILLER_GLASS); // Left column
			inventory.setItem(i + 8, GlobalItems.FILLER_GLASS); // Right column
		}

		this.inventory.setItem(closeInventorySlot, GlobalItems.closeInventory);
		this.inventory.setItem(backInventorySlot, GlobalItems.backInventory);
	}

	protected boolean handleClose(int slot) {
		if (slot == closeInventorySlot) {
			this.playerMenuUtility.getOwner().closeInventory();
			return true;
		}
		return false;
	}

	protected void handleBack() {
		new ItemEditMenu(playerMenuUtility)
			.open();
	}

	protected boolean handleBack(int slot) {
		if (slot == backInventorySlot) {
			handleBack();
			return true;
		}
		return false;
	}

	protected boolean handleBack(int slot, @Nullable Runnable effect, @NotNull Function<PlayerMenuUtility, Menu> newMenu) {
		if (slot == backInventorySlot) {
			if (effect != null) effect.run();
			newMenu.apply(playerMenuUtility).open();
			return true;
		}
		return false;
	}

}