package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditFinishListener implements Listener {

	@EventHandler
	public void editFinish(InventoryCloseEvent e) {
		if (!(e.getInventory().getHolder() instanceof Menu)) {
			return;
		}

		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility((Player) e.getPlayer());
		if(playerMenuUtility.getCurrentMenu() == null || playerMenuUtility.isMenuTransitioning()) {
			return;
		}

		playerMenuUtility.setCurrentMenu(null);

		playerMenuUtility.getAPICallback().ifPresent(cb -> {
			ItemStack itemStack = playerMenuUtility.getItemInHand().get();

			if (!playerMenuUtility.getItemstackClone().get().equals(itemStack)) {
				UniqueItemIdentifierManager.ensureUID(itemStack);
			}

			Bukkit.getLogger().finest("API Callback onEditFinish called");
			cb.onEditFinish(itemStack);
			MenuManager.removePlayerMenuUtility(playerMenuUtility.getOwner());
		});
	}
}
