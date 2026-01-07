package de.doppelkool.itemforgegui.API;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ObservableObject;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.ItemEditMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * The plugins API to trigger edits and wait for callbacks
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemForgeGuiAPI {

	/**
	 * Opens the edit menu on the given player with the given item.
	 */
	public static void openEditor(Player pl, ItemStack itemToEdit, ItemForgeCallback callback) {
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		playerMenuUtility.setItemInHand(new ObservableObject<>(itemToEdit));
		playerMenuUtility.setAPICallback(Optional.of(callback));
		new ItemEditMenu(playerMenuUtility)
			.open();
	}

	public interface ItemForgeCallback {
		void onItemEdited(ItemStack result);
	}

}