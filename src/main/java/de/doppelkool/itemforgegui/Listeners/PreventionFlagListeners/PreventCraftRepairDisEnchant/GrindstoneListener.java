package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class GrindstoneListener implements Listener {

	private final Map<Inventory, Boolean> pendingGrindstoneProcessing = new WeakHashMap<>();

	//Event can be fired for repair and unenchanting
	@EventHandler
	public void preventRepairDisenchant(PrepareGrindstoneEvent e) {
		Inventory grindstoneInventory = e.getInventory();

		if (pendingGrindstoneProcessing.containsKey(grindstoneInventory)) {
			return;
		}
		pendingGrindstoneProcessing.put(grindstoneInventory, true);

		//Next tick, remove the inventory again
		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
			ItemStack itemUp = e.getInventory().getItem(0);
			ItemStack itemDown = e.getInventory().getItem(1);

			// Exit early if no items are provided
			if (itemUp == null && itemDown == null) {
				removeGrindstoneInventory(grindstoneInventory);
				return;
			}

			boolean preventDisenchantAny =
					DisallowedActionsManager.isActionPrevented(itemUp, ForgeAction.DISENCHANT)
					|| DisallowedActionsManager.isActionPrevented(itemDown, ForgeAction.DISENCHANT);
			boolean preventRepairAny =
					DisallowedActionsManager.isActionPrevented(itemUp, ForgeAction.REPAIR)
					|| DisallowedActionsManager.isActionPrevented(itemDown, ForgeAction.REPAIR);

			// If neither prevention is active, no need to process further
			if (!preventDisenchantAny && !preventRepairAny) {
				removeGrindstoneInventory(grindstoneInventory);
				return;
			}

			// Determine if either input is enchanted
			boolean itemUpEnchanted = itemUp != null && !itemUp.getEnchantments().isEmpty();
			boolean itemDownEnchanted = itemDown != null && !itemDown.getEnchantments().isEmpty();
			boolean isDisenchant = itemUpEnchanted || itemDownEnchanted;

			// Determine if this is a repair action: both inputs exist and are of the same type
			boolean isRepair =
				itemUp != null
					&& itemDown != null
					&& itemUp.getType() == itemDown.getType();

			// Prevention logic:
			// - If clearly disenchanting and any disenchant prevention is active.
			// - If clearly repairing and any repair prevention is active.
			// - If ambiguous (both or neither clear) and any prevention flag is active.
			if (isDisenchant && !isRepair && preventDisenchantAny ||
				isRepair && !isDisenchant && preventRepairAny ||
				isDisenchant && isRepair ||
				!isDisenchant && !isRepair) {

				//Setting the result does not work in seemingly random occasions, so set the result item manually to null as well
				e.setResult(null);
				e.getInventory().setItem(2, null);

				e.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
			}

			removeGrindstoneInventory(grindstoneInventory);
		});
	}

	private void removeGrindstoneInventory(Inventory grindstoneInv) {
		pendingGrindstoneProcessing.remove(grindstoneInv);
	}
}
