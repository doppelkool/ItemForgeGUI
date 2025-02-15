package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
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
public class CraftListener implements Listener {

	//ToDo dont send messages if configurable string (todo) is empty

	//Event can be fired for crafting and repairing
	@EventHandler
	public void preventCraftingWithItem(PrepareItemCraftEvent e) {
		for(ItemStack item : e.getInventory().getMatrix()) {
			if (!UniqueItemIdentifierManager.isUniqueItem(item)) {
				continue;
			}

			if(e.isRepair()) {
				if(DisallowedActionsManager.isActionPrevented(item, ForgeAction.REPAIR)) {
					e.getInventory().setResult(null);
					e.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
					return;
				}
				continue;
			} else if (!DisallowedActionsManager.isActionPrevented(item, ForgeAction.CRAFT)) {
				continue;
			}

			e.getInventory().setResult(null);
			e.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
		}
	}

	//ToDo A way to prevent a crafter from doing the same thing as above
	//@EventHandler
	//public void preventCraftingCrafter(CrafterCraftEvent e) {}
}
