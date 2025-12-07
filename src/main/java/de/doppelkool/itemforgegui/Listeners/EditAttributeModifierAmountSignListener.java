package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus.ValueSelectionMenus.ValuePickerMenus.AddNumberOperationMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
public class EditAttributeModifierAmountSignListener implements Listener {
	@EventHandler
	public void onPlayerEditAttributeMOdifierAmountSign(SignChangeEvent e) {
		Player pl = e.getPlayer();
		PlayerMenuUtility playerMenuUtility = MenuManager.getPlayerMenuUtility(pl);
		SignNumberEditor signNumberEditor = playerMenuUtility.getSignNumberEditor();

		if (signNumberEditor == null
			|| signNumberEditor.getType() != SignNumberEditor.NUMBER_EDIT_TYPE.ATTRIBUTE_MODIFIER_VALUE
			|| signNumberEditor.getSignLocation() == null
			|| !SignNumberEditor.isSameBlockLocation(e.getBlock().getLocation(), signNumberEditor.getSignLocation().getBlock().getLocation())) {
			return;
		}

		if (e.getLines().length < 1) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_ATTRIBUTE_MODIFIER_EMPTY_INPUT);
			endProcess(playerMenuUtility);
			return;
		}

		Double amount = SignNumberEditor.parseDouble(e.getLine(0));
		if (amount == null) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_EDIT_ATTRIBUTE_MODIFIER_INVALID_INPUT);
			endProcess(playerMenuUtility);
			return;
		}

		playerMenuUtility.getAttributeStorage().getModifierValues().put(
			playerMenuUtility.getSignNumberEditor().getTargetOperation(), roundUp(amount)
		);
		endProcess(playerMenuUtility);
	}

	private double roundUp(double setValue) {
		return Math.ceil((setValue) * 10) / 10.0;
	}

	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);
		util.setSignNumberEditor(null);

		ItemStack item = util.getOwner().getInventory().getItemInMainHand();
		new ItemInfoManager(item).updateItemInfo();
		if (item.getItemMeta().hasEnchants()) {
			new AddNumberOperationMenu(util)
				.open();
		} else {
			new AddNumberOperationMenu(util)
				.open();
		}
	}
}
