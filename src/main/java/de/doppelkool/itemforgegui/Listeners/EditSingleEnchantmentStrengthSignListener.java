package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus.ActivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus.DeactivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
	public void onPlayerEditSingleEnchantmentStrengthBook(SignChangeEvent e) {
		Player pl = e.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ENCHANTMENT
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(e.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (e.getLines().length < 1) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_ENCHANTMENT_EMPTY_INPUT);
			endProcess(playerMenuUtility);
			return;
		}

		Integer strength = SignNumberEditor.parseInteger(e.getLine(0));
		if (strength == null) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_ENCHANTMENT_INVALID_INPUT);
			endProcess(playerMenuUtility);
			return;
		}

		//new strength = entered strength \/ 255
		ItemStack itemInMainHand = pl.getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		strength = Integer.min(strength, 255);
		strength = Integer.max(strength, 0);

		itemMeta.removeEnchant(playerMenuUtility.getTargetEnchantment());
		if (strength != 0) {
			itemMeta.addEnchant(playerMenuUtility.getTargetEnchantment(), strength, true);
		}

		itemInMainHand.setItemMeta(itemMeta);
		endProcess(playerMenuUtility);
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		ItemStack item = util.getOwner().getInventory().getItemInMainHand();
		new ItemInfoManager(item).updateItemInfo();
		if (item.getItemMeta().hasEnchants()) {
			new ActivatedEnchantmentsMenu(util)
				.open();
		} else {
			new DeactivatedEnchantmentsMenu(util)
				.open();
		}
	}
}
