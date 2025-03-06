package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus.SpecialsActivatedArmorEffectsMenu;
import de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus.SpecialsDeactivatedArmorEffectsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditSingleArmorEffectStrengthSignListener implements Listener {
	@EventHandler
	public void onPlayerEditSingleArmorEffectStrengthBook(SignChangeEvent event) {
		Player pl = event.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ARMOR_EFFECT
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

		strength = Integer.min(strength, 255);
		strength = Integer.max(strength, 0);

		if (strength <= 1) {
			ArmorEffectManager.removeArmorEffect(itemInMainHand, playerMenuUtility.getTargetPotionEffectType());
		} else {
			ArmorEffectManager.addArmorEffect(itemInMainHand, playerMenuUtility.getTargetPotionEffectType(), strength);
		}

		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		ItemStack item = util.getOwner().getInventory().getItemInMainHand();
		new ItemInfoManager(item).updateItemInfo();
		if (ArmorEffectManager.hasArmorEffects(item)) {
			new SpecialsActivatedArmorEffectsMenu(util)
				.open();
		} else {
			new SpecialsDeactivatedArmorEffectsMenu(util)
				.open();
		}
	}
}
