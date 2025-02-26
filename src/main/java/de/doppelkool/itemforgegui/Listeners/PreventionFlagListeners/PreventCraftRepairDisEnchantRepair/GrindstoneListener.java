package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class GrindstoneListener extends DuplicateEventManager<PrepareGrindstoneEvent> implements Listener {

	//Event can be fired for repair and unenchanting
	@EventHandler
	public void preventRepairDisenchant(PrepareGrindstoneEvent e) {
		duplicateExecutionSafeProcess(e.getView().getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareGrindstoneEvent event) {
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		//Next tick, remove the inventory again
		ItemStack itemUp = event.getInventory().getItem(0);
		ItemStack itemDown = event.getInventory().getItem(1);

		// Exit early if no items are provided
		if (itemUp == null && itemDown == null) {
			return false;
		}

		boolean preventDisenchantAny =
			DisallowedActionsManager.isActionPrevented(itemUp, ForgeAction.DISENCHANT)
				|| DisallowedActionsManager.isActionPrevented(itemDown, ForgeAction.DISENCHANT);
		boolean preventRepairAny =
			DisallowedActionsManager.isActionPrevented(itemUp, ForgeAction.REPAIR)
				|| DisallowedActionsManager.isActionPrevented(itemDown, ForgeAction.REPAIR);

		// If neither prevention is active, no need to process further
		if (!preventDisenchantAny && !preventRepairAny) {
			return false;
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

			return true;
		}

		return false;
	}

	@Override
	protected void customCancelLogic(PrepareGrindstoneEvent event) {
		//Setting the result does not work in seemingly random occasions, so set the result item manually to null as well
		event.setResult(null);
		event.getInventory().setItem(2, null);
	}
}