package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuRelay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MenuListener implements Listener, MenuRelay {

	@EventHandler
	public void onMenuClick(InventoryClickEvent e) {

		InventoryHolder holder = e.getInventory().getHolder();
		if (holder instanceof Menu menu) {
			e.setCancelled(true);

			if (!shouldForwardClickEvent(e)) {
				return;
			}

			menu.handleMenu(e);
		}
	}
}

