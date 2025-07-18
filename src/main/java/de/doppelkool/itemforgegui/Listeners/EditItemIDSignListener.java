package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.ItemUniquenessSettingsMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
public class EditItemIDSignListener implements Listener {
	@EventHandler
	public void onPlayerEditAmountSign(SignChangeEvent e) {
		Player pl = e.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ITEM_ID
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(e.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (e.getLines().length < 1) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_UNIQUEID_EMPTY_INPUT);
			endProcess(playerMenuUtility);
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (String line : e.getLines()) {
			if (line != null) {
				sb.append(line);
			}
		}

		String uniqueID = sb.toString();

		if (uniqueID.length() > 4 * 15) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_UNIQUEID_INPUT_TO_LONG);
			endProcess(playerMenuUtility);
			return;
		}

		UniqueItemIdentifierManager.setUniqueItemIdentifier(
			pl.getInventory().getItemInMainHand(),
			uniqueID.trim().replace(" ", "_"));
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		new ItemUniquenessSettingsMenu(util)
			.open();
	}
}