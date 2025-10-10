package de.doppelkool.itemforgegui.Main.MenuComponents;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public abstract class ConfirmableMenu extends Menu {

	protected int confirmationItemSlot;

	public ConfirmableMenu(PlayerMenuUtility playerMenuUtility, int confirmationItemSlot) {
		super(playerMenuUtility);
		this.confirmationItemSlot = confirmationItemSlot;
	}

	protected abstract boolean isConfirmable();

	protected abstract ItemStack getConfirmableItem();

	protected void updateConfirmSlot() {
		this.inventory.setItem(confirmationItemSlot, getConfirmableItem());
	}

	protected boolean handleConfirm(int slot, @Nullable Runnable effect, @NotNull Function<PlayerMenuUtility, Menu> newMenu) {
		if (slot == confirmationItemSlot && isConfirmable()) {
			if (effect != null) effect.run();
			newMenu.apply(playerMenuUtility).open();
			return true;
		}
		return false;
	}
}