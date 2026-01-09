package de.doppelkool.itemforgegui.API;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditFinishListener implements Listener {

	@EventHandler
	public void editFinish(InventoryCloseEvent e) {
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility((Player) e.getPlayer());
		playerMenuUtility.getAPICallback().ifPresent(cb -> {
			Bukkit.getLogger().finest("API Callback onEditFinish called");
			cb.onEditFinish(playerMenuUtility.getItemInHand().get());
		});
	}

}
