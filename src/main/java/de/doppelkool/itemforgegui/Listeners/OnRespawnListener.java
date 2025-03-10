package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class OnRespawnListener implements Listener {

	@EventHandler
	public void onRespawnReapplyArmorEffects(PlayerRespawnEvent e) {
		if (e.getRespawnReason() != PlayerRespawnEvent.RespawnReason.END_PORTAL) {
			Bukkit.getScheduler().runTask(Main.getPlugin(), () -> ArmorEffectManager.reapplyArmorEffects(e.getPlayer()));
		}
	}

}
