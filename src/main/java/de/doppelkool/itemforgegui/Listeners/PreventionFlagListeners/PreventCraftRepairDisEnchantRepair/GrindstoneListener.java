package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
	protected boolean eventLogic(PrepareGrindstoneEvent e) {
		this.cancelString = MessageManager.format(Messages.ACTION_PREVENTED_GRINDSTONE_USAGE);

		//Next tick, remove the inventory again
		ItemStack itemUp = e.getInventory().getItem(0);
		ItemStack itemDown = e.getInventory().getItem(1);

		// Exit early if no items are provided
		if (itemUp == null && itemDown == null) {
			return false;
		}

		boolean preventDisenchantAny =
			PreventionFlagManager.getInstance().isFlagApplied(itemUp, ForgeAction.DISENCHANT)
				|| PreventionFlagManager.getInstance().isFlagApplied(itemDown, ForgeAction.DISENCHANT);
		boolean preventRepairAny =
			PreventionFlagManager.getInstance().isFlagApplied(itemUp, ForgeAction.REPAIR)
				|| PreventionFlagManager.getInstance().isFlagApplied(itemDown, ForgeAction.REPAIR);

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
	protected void customCancelLogic(PrepareGrindstoneEvent e) {
		//Setting the result does not work in seemingly random occasions, so set the result item manually to null as well
		e.setResult(null);
		e.getInventory().setItem(2, null);
	}
}