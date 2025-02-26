package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.ActivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.DeactivatedEnchantmentsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditSingleEnchantmentStrengthSignListener implements Listener {
	@EventHandler
	public void onPlayerEditSingleEnchantmentStrengthBook(SignChangeEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ENCHANTMENT
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(event.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (event.getLines().length < 1) {
			pl.sendMessage(Main.prefix + "The strength you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		Integer strength = SignNumberEditor.parseInteger(event.getLine(0));
		if (strength == null) {
			pl.sendMessage(Main.prefix + "The strength you entered is not valid");
			endProcess(playerMenuUtility);
			return;
		}

		//new strength = entered strength \/ 255
		ItemStack itemInMainHand = pl.getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		strength = Integer.min(strength, 255);
		strength = Integer.max(strength, 0);

		itemMeta.removeEnchant(playerMenuUtility.getTargetEnchantment());
		if(strength != 0) {
			itemMeta.addEnchant(playerMenuUtility.getTargetEnchantment(), strength, true);
		}

		itemInMainHand.setItemMeta(itemMeta);
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		ItemStack item = util.getOwner().getInventory().getItemInMainHand();
		if (item.getItemMeta().hasEnchants()) {
			new ActivatedEnchantmentsMenu(util)
				.open();
		} else {
			new DeactivatedEnchantmentsMenu(util)
				.open();
		}
	}
}
