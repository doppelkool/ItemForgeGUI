package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventToolUseListener implements Listener {

	@EventHandler
	public void onToolUse(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		if (item == null) {
			return;
		}

		if (!DisallowedActionsManager.isActionPrevented(item, ForgeAction.USE_TOOL)) {
			return;
		}

		event.setCancelled(true);
		Player player = event.getPlayer();
		player.sendMessage("You are not allowed to use this tool!");
	}

}
