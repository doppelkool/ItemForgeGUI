package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DrinkMilkListener implements Listener {
	@EventHandler
	public void reApplyArmorEffectWhenMilkIsDrunk(PlayerItemConsumeEvent event) {
		if (event.getItem().getType() != Material.MILK_BUCKET) {
			return;
		}

		// Delay one tick so that milk clears all effects first.
		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> ArmorEffectManager.reapplyArmorEffects(event.getPlayer()));
	}
}
