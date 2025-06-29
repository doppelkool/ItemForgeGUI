package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventDestroyListener implements Listener {

	@EventHandler
	public void onEntityCombust(EntityCombustEvent e) {
		processEntityEvent(e.getEntity(), e);
	}

	@EventHandler
	public void preventPFItemDamage(EntityDamageEvent e) {
		processEntityEvent(e.getEntity(), e);
	}

	private void processEntityEvent(Entity e, Cancellable cancellable) {
		if (!(e instanceof Item item)) {
			return;
		}

		if (!PreventionFlagManager.getInstance().isFlagApplied(item.getItemStack(), ForgeAction.DESTROY)) {
			return;
		}

		cancellable.setCancelled(true);
	}

}
