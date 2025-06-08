package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.AmountMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
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
	public void onPlayerEditAmountSign(SignChangeEvent e) {
		Player pl = e.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.AMOUNT
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(e.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (e.getLines().length < 1) {
			MessageManager.message(pl, "sign-editor.edit.amount.empty-input");
			endProcess(playerMenuUtility);
			return;
		}

		Integer amount = SignNumberEditor.parseInteger(e.getLine(0).trim());
		if (amount == null) {
			MessageManager.message(pl, "sign-editor.edit.amount.invalid-input");
			endProcess(playerMenuUtility);
			return;
		}

		amount = Integer.min(amount, 99);
		amount = Integer.max(amount, 1);

		pl.getInventory().getItemInMainHand().setAmount(amount);
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		new AmountMenu(util)
			.open();
	}
}
