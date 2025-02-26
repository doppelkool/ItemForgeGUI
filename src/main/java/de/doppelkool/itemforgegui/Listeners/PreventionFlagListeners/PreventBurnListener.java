package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventBurnListener implements Listener {

	@EventHandler
	public void onEntityCombust(EntityCombustEvent e) {
		if (!(e.getEntity() instanceof Item)) {
			return;
		}

		if (!DisallowedActionsManager.isActionPrevented(((Item) e.getEntity()).getItemStack(), ForgeAction.BURN)) {
			return;
		}

		e.setCancelled(true);
	}

	@EventHandler
	public void preventPFItemDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Item)) {
			return;
		}

		if (!DisallowedActionsManager.isActionPrevented(((Item) e.getEntity()).getItemStack(), ForgeAction.BURN)) {
			return;
		}

		e.setCancelled(true);
	}
}
