package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SmithingTableListener extends DuplicateEventManager<PrepareSmithingEvent> implements Listener {

	@EventHandler
	public void preventUpgading(PrepareSmithingEvent e) {
		duplicateExecutionSafeProcess(e.getView().getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareSmithingEvent e) {
		this.cancelString = MessageManager.format("action-prevented.item-upgrade");

		ItemStack item = e.getInventory().getItem(1);

		if (item == null) {
			return false;
		}

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.UPGRADE)) {
			return false;
		}

		return true;
	}

	@Override
	protected void customCancelLogic(PrepareSmithingEvent e) {
		//Setting the result does not work in seemingly random occasions, so set the result item manually to null as well
		e.setResult(null);
		e.getInventory().setItem(3, null);
	}
}