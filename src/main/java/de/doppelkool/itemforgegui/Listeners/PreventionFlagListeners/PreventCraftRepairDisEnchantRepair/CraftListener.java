package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CraftListener extends DuplicateEventManager<PrepareItemCraftEvent> implements Listener {

	//Event can be fired for crafting and repairing
	@EventHandler
	public void preventCraftingWithItem(PrepareItemCraftEvent e) {
		duplicateExecutionSafeProcess(e.getView().getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareItemCraftEvent event) {
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		for(ItemStack item : event.getInventory().getMatrix()) {
			if (!UniqueItemIdentifierManager.isUniqueItem(item)) {
				continue;
			}

			if(event.isRepair()) {
				if(PreventionFlagManager.isActionPrevented(item, ForgeAction.REPAIR)) {
					return true;
				}
				continue;
			} else if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.CRAFT)) {
				continue;
			}

			return true;
		}
		return false;
	}

	@Override
	protected void customCancelLogic(PrepareItemCraftEvent event) {
		event.getInventory().setResult(null);
	}
}
