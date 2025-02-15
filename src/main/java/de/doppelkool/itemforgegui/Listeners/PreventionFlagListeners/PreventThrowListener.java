package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventThrowListener implements Listener {

	//@EventHandler
	//public void onThrow(ProjectileLaunchEvent e) {
	//	Logger.log(e);
//
	//	//ToDo Not having pdc-values like the itemstack -> find another way to get the itemstack; another event or track the consumeditem; Prob need some kind of inventory event to prevent right click
	//	Projectile projectile = e.getEntity();
	//	Logger.log(projectile);
	//	if (!UniqueItemIdentifierManager.isUniqueItem(projectile)) {
	//		return;
	//	}
//
	//	if (UniqueItemIdentifierManager.isActionPrevented(projectile, ForgeAction.LAUNCH)) {
	//		e.setCancelled(true);
	//		if (e.getEntity().getShooter() instanceof Player pl) {
	//			pl.sendMessage(Main.prefix + "You are not allowed to do this!");
	//		}
	//	}
	//}

	@EventHandler
	public void preventUniqueArrowShoot(EntityShootBowEvent e) {
		ItemStack consumedItem = e.getConsumable();
		if (!UniqueItemIdentifierManager.isUniqueItem(consumedItem)) {
			return;
		}

		if (!DisallowedActionsManager.isActionPrevented(consumedItem, ForgeAction.LAUNCH)) {
			return;
		}

		e.setCancelled(true);

		if (!(e.getEntity() instanceof Player pl)) {
			return;
		}

		//e#setConsumeItem(boolean) is currently non-functional. Workaround readd the item:
		if(pl.getGameMode() != GameMode.CREATIVE) {
			readdItem(e.getHand(), pl, consumedItem);
		}

		pl.sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	private void readdItem(EquipmentSlot es, Player pl, ItemStack consumedItem) {
		PlayerInventory inventory = pl.getInventory();

		EquipmentSlot toCheck =
			(es == EquipmentSlot.HAND || es == EquipmentSlot.OFF_HAND)
				? (es == EquipmentSlot.HAND
					? EquipmentSlot.OFF_HAND
					: EquipmentSlot.HAND)
			: null;

		if (toCheck != null) {
			ItemStack item = inventory.getItem(toCheck);
			if (item != null && item.isSimilar(consumedItem)) {
				item.setAmount(item.getAmount() + 1);
				return;
			}
		}

		for(Map.Entry<Integer, ? extends ItemStack> entry : inventory.all(consumedItem.getType()).entrySet()) {
			if(entry.getValue().isSimilar(consumedItem)) {
				entry.getValue().setAmount(entry.getValue().getAmount() + 1);
				return;
			}
		}

		inventory.addItem(consumedItem);
		return;
	}
}