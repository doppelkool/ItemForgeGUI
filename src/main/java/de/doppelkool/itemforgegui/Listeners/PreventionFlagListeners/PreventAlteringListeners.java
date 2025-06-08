package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventAlteringListeners implements Listener {

	@EventHandler
	public void preventItemDrop(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();

		if (!UniqueItemIdentifierManager.isUniqueItem(itemStack)) {
			return;
		}

		if (!PreventionFlagManager.isActionPrevented(itemStack, ForgeAction.DROP)) {
			return;
		}

		e.setCancelled(true);
		MessageManager.message(e.getPlayer(), "action-prevented.item-drop");
	}
}