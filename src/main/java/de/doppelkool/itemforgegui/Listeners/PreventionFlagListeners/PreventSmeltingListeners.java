package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import org.bukkit.block.Furnace;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceInventory;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventSmeltingListeners implements Listener {

	@EventHandler
	public void onFurnaceBurn(FurnaceBurnEvent event) {
		if (!(event.getBlock().getState() instanceof Furnace)) return;
		processFurnaceEvent((Furnace)event.getBlock().getState(), event);
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent event) {
		processFurnaceEvent((Furnace)event.getBlock().getState(), event);
	}

	private void processFurnaceEvent(Furnace furnace, Cancellable cancellable) {
		FurnaceInventory inv = furnace.getInventory();

		if (!PreventionFlagManager.isActionPrevented(inv.getItem(0), ForgeAction.SMELT)
			&& !PreventionFlagManager.isActionPrevented(inv.getItem(1), ForgeAction.SMELT)) {
			return;
		}
		cancellable.setCancelled(true);
	}

}
