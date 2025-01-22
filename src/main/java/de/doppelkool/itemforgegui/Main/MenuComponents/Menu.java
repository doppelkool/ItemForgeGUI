package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.FILLER_GLASS;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class Menu implements InventoryHolder {

	protected PlayerMenuUtility playerMenuUtility;
	protected Inventory inventory;

	public Menu(PlayerMenuUtility playerMenuUtility) {
		this.playerMenuUtility = playerMenuUtility;
	}

	public abstract String getMenuName();

	public abstract int getSlots();

	public abstract void handleMenu(InventoryClickEvent e);

	public abstract void setMenuItems();

	public void open() {
		inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

		this.setMenuItems();

		playerMenuUtility.getOwner().openInventory(inventory);
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}

	public void setFillerGlass() {
		for (int i = 0; i < getSlots(); i++) {
			if (inventory.getItem(i) == null) {
				inventory.setItem(i, FILLER_GLASS);
			}
		}
	}

	public void addMenuBorder() {
		for (int i = 0; i < 9; i++) {
			inventory.setItem(i, ItemStacks.FILLER_GLASS); // Top row
			inventory.setItem(getSlots() - 9 + i, ItemStacks.FILLER_GLASS); // Bottom row
		}

		// Left and right columns (excluding corners already set)
		for (int i = 9; i < getSlots() - 9; i += 9) {
			inventory.setItem(i, ItemStacks.FILLER_GLASS); // Left column
			inventory.setItem(i + 8, ItemStacks.FILLER_GLASS); // Right column
		}
	}

}