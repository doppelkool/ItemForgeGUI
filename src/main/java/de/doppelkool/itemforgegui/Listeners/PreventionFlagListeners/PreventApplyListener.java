package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventApplyListener implements Listener {

	@EventHandler
	public void preventPuttingItemInItemFrame(PlayerInteractEntityEvent e) {
		// Check if the player is trying to place item in an item frame
		if (!(e.getRightClicked() instanceof ItemFrame)) {
			return;
		}

		// Check if the player is trying to place a plugins item in an item frame
		ItemStack itemInMainHand = e.getPlayer().getInventory().getItemInMainHand();
		if (!UniqueItemIdentifierManager.isUniqueItem(itemInMainHand)) {
			return;
		}

		if (!PreventionFlagManager.getInstance().isFlagApplied(itemInMainHand, ForgeAction.ITEM_FRAME_PLACE)) {
			return;
		}

		e.setCancelled(true);
		MessageManager.message(e.getPlayer(), Messages.ACTION_PREVENTED_ITEM_PUT_IN_ITEM_FRAME);
	}

	@EventHandler
	public void preventPuttingItemOnArmorStand(PlayerArmorStandManipulateEvent e) {
		Player pl = e.getPlayer();
		ItemStack itemInMainHand = pl.getInventory().getItemInMainHand();
		if (!UniqueItemIdentifierManager.isUniqueItem(itemInMainHand)) {
			return;
		}

		if (!PreventionFlagManager.getInstance().isFlagApplied(itemInMainHand, ForgeAction.EQUIP)) {
			return;
		}

		e.setCancelled(true);
		MessageManager.message(pl, Messages.ACTION_PREVENTED_ITEM_PUT_ON_ARMOR_STAND);
	}

}
