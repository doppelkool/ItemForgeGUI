package de.doppelkool.itemforgegui.API;

import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
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
		playerMenuUtility.setItemstackClone(Optional.of(itemToEdit.clone()));
		playerMenuUtility.setAPICallback(Optional.of(callback));
		new ItemEditMenu(playerMenuUtility)
			.open();
	}

	public static String getUID(ItemStack itemStack) {
		return UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(itemStack);
	}

	public static void setRandomUID(ItemStack itemStack) {
		UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(itemStack);
	}

	public static void setUID(ItemStack itemStack, String specificUID) {
		UniqueItemIdentifierManager.setUniqueItemIdentifier(itemStack, specificUID);
	}

	public interface ItemForgeCallback {
		void onEditFinish(ItemStack result);
	}

}