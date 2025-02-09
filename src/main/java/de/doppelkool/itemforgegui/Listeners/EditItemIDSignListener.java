package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.*;
import de.doppelkool.itemforgegui.Main.Menus.ItemUniquenessSettingsMenu;
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
	public void onPlayerEditAmountSign(SignChangeEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ITEM_ID
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(event.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (event.getLines().length < 1) {
			pl.sendMessage(Main.prefix + "The ID you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		StringBuilder sb = new StringBuilder();

		for (String line : event.getLines()) {
			if (line != null) {
				sb.append(line);
			}
		}

		String uniqueID = sb.toString();

		if (uniqueID.length() > 4*15) {
			pl.sendMessage(Main.prefix + "The ID you entered was too long");
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