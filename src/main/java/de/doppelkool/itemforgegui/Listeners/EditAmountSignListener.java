package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.AmountMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.SignNumberEditor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditAmountSignListener implements Listener {
	@EventHandler
	public void onPlayerEditAmountSign(SignChangeEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.AMOUNT
			|| signNumberEditor.getSignLocation() == null
			|| !isSameBlockLocation(event.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (event.getLines().length < 1) {
			pl.sendMessage(Main.prefix + "The amount you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		Integer amount = SignNumberEditor.parseInteger(event.getLine(0).trim());
		if (amount == null) {
			pl.sendMessage(Main.prefix + "The amount you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		amount = Integer.min(amount, 99);
		amount = Integer.max(amount, 1);

		pl.getInventory().getItemInMainHand().setAmount(amount);
		endProcess(playerMenuUtility);
	}

	private boolean isSameBlockLocation(Location loc1, Location loc2) {
		return
			loc1.getBlockX() == loc2.getBlockX() &&
			loc1.getBlockY() == loc2.getBlockY() &&
			loc1.getBlockZ() == loc2.getBlockZ();
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		new AmountMenu(util)
			.open();
	}
}
