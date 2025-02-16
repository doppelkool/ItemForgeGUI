package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
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

	//ToDo dont send messages if configurable string (todo) is empty

	//Event can be fired for crafting and repairing
	@EventHandler
	public void preventCraftingWithItem(PrepareItemCraftEvent e) {
		duplicateExecutionSafeProcess(e.getView().getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareItemCraftEvent event) {
		for(ItemStack item : event.getInventory().getMatrix()) {
			if (!UniqueItemIdentifierManager.isUniqueItem(item)) {
				continue;
			}

			if(event.isRepair()) {
				if(DisallowedActionsManager.isActionPrevented(item, ForgeAction.REPAIR)) {
					event.getInventory().setResult(null);
					event.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
					return false;
				}
				continue;
			} else if (!DisallowedActionsManager.isActionPrevented(item, ForgeAction.CRAFT)) {
				continue;
			}

			event.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
			return true;
		}
		return false;
	}

	@Override
	protected void customCancelLogic(PrepareItemCraftEvent event) {
		event.getInventory().setResult(null);
	}

	//ToDo A way to prevent a crafter from doing the same thing as above
	//@EventHandler
	//public void preventCraftingCrafter(CrafterCraftEvent e) {}
}
