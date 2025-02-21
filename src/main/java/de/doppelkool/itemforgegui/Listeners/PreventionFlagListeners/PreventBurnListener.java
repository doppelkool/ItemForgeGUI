package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventBurnListener implements Listener {

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {

		if (!(event.getEntity() instanceof Item)) {
			return;
		}

		// When the global flag is active, we want to deny the placement.
		if (!DisallowedActionsManager.isActionPrevented(event.getEntity(), ForgeAction.BURN)) {
			return;
		}

		event.setCancelled(true);
	}
}
