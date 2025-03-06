package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventEquipListener implements Listener {

	@EventHandler
	public void preventArmorEquip(ArmorEquipEvent e) {
		ItemStack newArmorPiece = e.getNewArmorPiece();

		if(newArmorPiece == null) {
			return;
		}

		if(!PreventionFlagManager.isActionPrevented(newArmorPiece, ForgeAction.EQUIP)) {
			return;
		}

		e.setCancelled(true);
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}
}
