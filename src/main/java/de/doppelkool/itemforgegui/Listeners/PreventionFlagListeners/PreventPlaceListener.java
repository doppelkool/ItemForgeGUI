package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventPlaceListener implements Listener {

	@EventHandler
	public void preventPlace(BlockPlaceEvent e) {
		ItemStack item = e.getItemInHand();
		Logger.log(item);

		if (!DisallowedActionsManager.isActionPrevented(item, ForgeAction.PLACE)) {
			return;
		}

		e.getBlockPlaced().setType(Material.AIR);
		e.setCancelled(true);
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	@EventHandler
	public void preventPlace(EntityPlaceEvent e) {
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		Logger.log(item);

		if (!DisallowedActionsManager.isActionPrevented(item, ForgeAction.PLACE)) {
			return;
		}

		e.getEntity().remove();
		e.setCancelled(true);
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}

}
