package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
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
	public void onFurnaceBurn(FurnaceBurnEvent e) {
		if (!(e.getBlock().getState() instanceof Furnace)) return;
		processFurnaceEvent((Furnace) e.getBlock().getState(), e);
	}

	@EventHandler
	public void onFurnaceSmelt(FurnaceSmeltEvent e) {
		processFurnaceEvent((Furnace) e.getBlock().getState(), e);
	}

	private void processFurnaceEvent(Furnace furnace, Cancellable cancellable) {
		FurnaceInventory inv = furnace.getInventory();

		if (!PreventionFlagManager.getInstance().isFlagApplied(inv.getItem(0), ForgeAction.SMELT)
			&& !PreventionFlagManager.getInstance().isFlagApplied(inv.getItem(1), ForgeAction.SMELT)) {
			return;
		}
		cancellable.setCancelled(true);
	}

}
