package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuManager;
import de.doppelkool.itemforgegui.Main.Menus.DurabilityMenu;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.SignNumberEditor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditDurabilitySignListener implements Listener {
	@EventHandler
	public void onPlayerEditDurabilitySign(SignChangeEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.DURABILITY
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(event.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (event.getLines().length < 1) {
			pl.sendMessage(Main.prefix + "The durability you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		Integer durability = SignNumberEditor.parseInteger(
			event.getLine(0)
				.split("/")
				[0]);
		if (durability == null) {
			pl.sendMessage(Main.prefix + "The durability you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		ItemStack itemInMainHand = pl.getInventory().getItemInMainHand();

		durability = Integer.min(durability, itemInMainHand.getType().getMaxDurability());
		durability = Integer.max(durability, 0);

		//new durability = entered durability \/ maxDurability
		Damageable itemMeta = (Damageable) itemInMainHand.getItemMeta();
		durability = Integer.min(durability, itemInMainHand.getType().getMaxDurability());
		itemMeta.setDamage(itemInMainHand.getType().getMaxDurability() - durability);

		itemInMainHand.setItemMeta(itemMeta);
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		new DurabilityMenu(util)
			.open();
	}
}
