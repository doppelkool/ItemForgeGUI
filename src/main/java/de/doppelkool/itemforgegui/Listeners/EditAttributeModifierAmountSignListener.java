package de.doppelkool.itemforgegui.Listeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;

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

		//TODo maybe create two listeners one for creating attribute modifier and one for editing
		//ToDo optimize and rewrite variables
		if(playerMenuUtility.getAttributeStorage() != null) {
			playerMenuUtility.getAttributeStorage().getOperationDoubleValues().put(
				playerMenuUtility.getSignNumberEditor().getTargetOperation(), roundUp(amount)
			);
		} else {
			EnumMap<AttributeModifier.Operation, Double> operationDoubleEnumMap = playerMenuUtility.getModifyAttributeStorage().getModifierValues().get(playerMenuUtility.getModifyAttributeStorage().getSelectedSlotGroup());

			if(operationDoubleEnumMap == null) {
				operationDoubleEnumMap = new EnumMap<>(AttributeModifier.Operation.class);
			}

			operationDoubleEnumMap.put(playerMenuUtility.getSignNumberEditor().getTargetOperation(), roundUp(amount));
			playerMenuUtility.getModifyAttributeStorage().getModifierValues().put(playerMenuUtility.getModifyAttributeStorage().getSelectedSlotGroup(), operationDoubleEnumMap);
		}
		endProcess(playerMenuUtility);
	}

	private double roundUp(double setValue) {
		return BigDecimal.valueOf(setValue)
			.setScale(3, RoundingMode.HALF_UP)
			.doubleValue();
	}

	@SneakyThrows
	private void endProcess(PlayerMenuUtility util) {
		util.getOwner().getLocation().getBlock().setType(Material.AIR);

		ItemStack item = util.getOwner().getInventory().getItemInMainHand();
		new ItemInfoManager(item).updateItemInfo();
		util.getSignNumberEditor().getReturnInventory().getConstructor(PlayerMenuUtility.class)
			.newInstance(util)
			.open();
		util.setSignNumberEditor(null);
	}
}
