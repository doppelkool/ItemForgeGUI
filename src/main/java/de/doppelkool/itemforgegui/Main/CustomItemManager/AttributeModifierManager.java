package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeModifierManager {

	//In the future: Support more than 1 Modifier per Attribute
	public static boolean canMore(ItemMeta meta) {
		Multimap<Attribute, AttributeModifier> attributeModifiers = meta.getAttributeModifiers();
		if(attributeModifiers == null) {
			return true;
		}
		return attributeModifiers.keySet().size() != 35;
	}

	public static void insertValues(ItemStack attributeItemStackclone, Map.Entry<Attribute, AttributeModifier> attributeModifierEntry) {
		AttributeModifier.Operation operation = attributeModifierEntry.getValue().getOperation();
		String formattedOperation = map(operation.name());

		double amount = attributeModifierEntry.getValue().getAmount();

		EquipmentSlotGroup slotGroup = attributeModifierEntry.getValue().getSlotGroup();
		String formattedSlot = map(slotGroup);

		ItemStackCreateHelper.modifyLore(attributeItemStackclone,
			"Operation '" + formattedOperation + "'",
			"by " + amount,
			"for " + formattedSlot);
	}

	private static String map(EquipmentSlotGroup slotGroup) {
		if(EquipmentSlotGroup.ANY == slotGroup) {
			return "Any applied Slot";
		}
		return map(slotGroup.toString());
	}

	// "ADD_NUMBER" -> "Add Number" | "HAND" -> "Hand"
	private static String map(String input) {
		String[] parts = input.split("_");
		StringBuilder sb = new StringBuilder();

		for (String part : parts) {
			if (part.isEmpty()) continue;

			sb.append(Character.toUpperCase(part.charAt(0)));
			if (part.length() > 1) {
				sb.append(part.substring(1).toLowerCase());
			}
			sb.append(" ");
		}
		return sb.toString().trim();
	}
}
