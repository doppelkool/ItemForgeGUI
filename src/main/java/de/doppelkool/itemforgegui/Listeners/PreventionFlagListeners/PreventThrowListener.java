package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventThrowListener implements Listener {

	@EventHandler
	public void onThrow(ProjectileLaunchEvent e) {
		Logger.log(e);

		//ToDo Not having pdc-values like the itemstack -> find another way to get the itemstack; another event or track the consumeditem
		Projectile projectile = e.getEntity();
		Logger.log(projectile);
		if (!UniqueItemIdentifierManager.isUniqueItem(projectile)) {
			return;
		}

		if (UniqueItemIdentifierManager.isActionPrevented(projectile, ForgeAction.LAUNCH)) {
			e.setCancelled(true);
			if (e.getEntity().getShooter() instanceof Player pl) {
				pl.sendMessage(Main.prefix + "You are not allowed to do this!");
			}
		}
	}

	@EventHandler
	public void preventUniqueArrowShoot(EntityShootBowEvent e) {
		ItemStack consumedItem = e.getConsumable();
		if (!UniqueItemIdentifierManager.isUniqueItem(consumedItem)) {
			return;
		}

		if (UniqueItemIdentifierManager.isActionPrevented(consumedItem.getItemMeta(), ForgeAction.LAUNCH)) {
			e.setCancelled(true);
			if (e.getEntity() instanceof Player pl) {
				//e#setConsumeItem(boolean) is currently non-functional. Workaround readd the item:
				if(pl.getGameMode() != GameMode.CREATIVE)
					pl.getInventory().addItem(consumedItem);

				pl.sendMessage(Main.prefix + "You are not allowed to do this!");
			}
		}
	}

}