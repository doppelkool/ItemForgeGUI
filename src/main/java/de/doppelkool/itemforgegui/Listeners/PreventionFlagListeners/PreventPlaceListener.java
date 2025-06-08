package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.PLACE)) {
			return;
		}

		e.getBlockPlaced().setType(Material.AIR);
		e.setCancelled(true);
		MessageManager.message(e.getPlayer(), Messages.ACTION_PREVENTED_BLOCK_PLACE);
	}

	@EventHandler
	public void preventPlace(EntityPlaceEvent e) {
		Player pl = e.getPlayer();
		ItemStack item = pl.getInventory().getItemInMainHand();

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.PLACE)) {
			return;
		}

		e.getEntity().remove();
		e.setCancelled(true);
		MessageManager.message(pl, Messages.ACTION_PREVENTED_ENTITY_PLACE);
	}

}
