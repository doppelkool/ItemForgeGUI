package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.MenuManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class OnQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		MenuManager.removePlayerMenuUtility(e.getPlayer());
	}

}
