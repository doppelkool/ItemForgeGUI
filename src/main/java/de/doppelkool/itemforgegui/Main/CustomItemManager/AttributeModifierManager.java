package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Step;
import org.checkerframework.checker.units.qual.A;
import org.jline.builtins.Less;

import javax.annotation.meta.When;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeModifierManager {

	//In the future: Support more than 1 Modifier per Attribute
	public static boolean canMore(ItemMeta meta) {
		Multimap<Attribute, AttributeModifier> attributeModifiers = meta.getAttributeModifiers();
		if (attributeModifiers == null) {
			return true;
		}
		return attributeModifiers.keySet().size() != 35;
	}

	//public static void insertValues(ItemStack attributeItemStackclone, Map.Entry<Attribute, List<AttributeModifier>> attributeModifierEntry) {
	//	AttributeModifier.Operation operation = attributeModifierEntry.getValue().getOperation();
	//	String formattedOperation = map(operation.name());
//
	//	double amount = attributeModifierEntry.getValue().getAmount();
//
	//	EquipmentSlotGroup slotGroup = attributeModifierEntry.getValue().getSlotGroup();
	//	String formattedSlot = map(slotGroup);
//
	//	ItemStackCreateHelper.modifyLore(attributeItemStackclone,
	//		"Operation '" + formattedOperation + "'",
	//		"by " + amount,
	//		"for " + formattedSlot);
//
	//	ItemStackCreateHelper.modifyAttributeStringInPDC(attributeItemStackclone, attributeModifierEntry.getKey());
	//}

	public static void insertValues(ItemStack attributeItemStackclone, Attribute attribute, Collection<AttributeModifier> attributeModifierEntry) {
		ItemStackModifyHelper.addAttributeModifierToItem(attributeItemStackclone, attribute, attributeModifierEntry);
	}

	private static String map(EquipmentSlotGroup slotGroup) {
		if (EquipmentSlotGroup.ANY == slotGroup) {
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

	public static Collection<AttributeModifier> getActiveAttributeModifiersByAttribute(ItemStack itemStack, Attribute attribute) {
		return itemStack
			.getItemMeta()
			.getAttributeModifiers(attribute);
	}

	public static Collection<AttributeModifier> filterBySlot(Collection<AttributeModifier> activeAttributeModifiersByAttribute, EquipmentSlotGroup slotGroup) {
		return activeAttributeModifiersByAttribute.stream()
			.filter(attr -> attr.getSlotGroup().equals(slotGroup))
			.collect(Collectors.toList());
	}

	/**
	 * Takes an ItemStack and an Attribute whose modifiers should be
	 * shown as lore. “Fake” copies are added to the lore with similar formatting.
	 */
	public static void applyAttributeLore(ItemStack item, Attribute attribute, Collection<AttributeModifier> attributesToDisplay) {
		if (item == null || attributesToDisplay == null || attributesToDisplay.isEmpty()) {
			return;
		}

		ItemMeta meta = item.getItemMeta();
		if (meta == null) {
			return;
		}

		List<String> lore = meta.hasLore() ? new ArrayList<>(meta.getLore()) : new ArrayList<>();

		for (AttributeModifier mod : attributesToDisplay) {
			String line = formatAttributeLine(attribute, mod);
			if (line != null && !line.isEmpty()) {
				lore.add(line);
			}
		}

		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.##");

	/**
	 * Formats a single attribute+modifier line to look similar to vanilla.
	 * Example: " +7 Attack Damage" with a color depending on whether it’s good/bad.
	 */
	private static String formatAttributeLine(Attribute attribute, AttributeModifier modifier) {
		double amount = modifier.getAmount();
		if (amount == 0) return null;

		boolean positive = amount > 0;

		String valuePart;
		AttributeModifier.Operation op = modifier.getOperation();
		switch (op) {
			case ADD_NUMBER:
				// +5 Attack Damage
				valuePart = (positive ? "+" : "") + NUMBER_FORMAT.format(amount);
				break;

			case ADD_SCALAR:
			case MULTIPLY_SCALAR_1:
				// 0.2 -> +20%
				double percent = amount * 100.0;
				valuePart = (percent > 0 ? "+" : "") + NUMBER_FORMAT.format(percent) + "%";
				break;

			default:
				// Fallback: just print the raw number
				valuePart = (positive ? "+" : "") + NUMBER_FORMAT.format(amount);
				break;
		}

		ChatColor color = isModifierBeneficial(attribute, amount);
		String attrName = ItemStackModifyHelper.formatTranslationalNames(attribute.getTranslationKey());
		return color + " " + valuePart + " " + attrName;
	}

	public enum AttributePlayerNeutrality {
		POSITIVE, NEGATIVE, NEUTRAL
	}

	// =========================
	// ✅ POSITIVE WHEN INCREASED
	// =========================
	static Collection<Attribute> goodIfIncreased = List.of(
		Attribute.MAX_HEALTH,
		Attribute.KNOCKBACK_RESISTANCE,
		Attribute.MOVEMENT_SPEED,
		Attribute.FLYING_SPEED,
		Attribute.ATTACK_DAMAGE,
		Attribute.ATTACK_KNOCKBACK,
		Attribute.ATTACK_SPEED,
		Attribute.ARMOR,
		Attribute.ARMOR_TOUGHNESS,
		Attribute.LUCK,
		Attribute.MAX_ABSORPTION,
		Attribute.SAFE_FALL_DISTANCE,
		Attribute.STEP_HEIGHT,
		Attribute.JUMP_STRENGTH,
		Attribute.EXPLOSION_KNOCKBACK_RESISTANCE,
		Attribute.MOVEMENT_EFFICIENCY,
		Attribute.OXYGEN_BONUS,
		Attribute.WATER_MOVEMENT_EFFICIENCY,
		Attribute.BLOCK_INTERACTION_RANGE,
		Attribute.ENTITY_INTERACTION_RANGE,
		Attribute.BLOCK_BREAK_SPEED,
		Attribute.MINING_EFFICIENCY,
		Attribute.SNEAKING_SPEED,
		Attribute.SUBMERGED_MINING_SPEED,
		Attribute.SWEEPING_DAMAGE_RATIO,
		Attribute.WAYPOINT_TRANSMIT_RANGE,
		Attribute.WAYPOINT_RECEIVE_RANGE,
		Attribute.CAMERA_DISTANCE
	);

	// =========================
	// ❌ NEGATIVE WHEN INCREASED
	// =========================

	static Collection<Attribute> badIfIncreased = List.of(
		Attribute.FALL_DAMAGE_MULTIPLIER,
		Attribute.GRAVITY,
		Attribute.BURNING_TIME,
		Attribute.FOLLOW_RANGE,
		Attribute.TEMPT_RANGE,
		Attribute.SPAWN_REINFORCEMENTS
	);

	// =========================
	// ⚠️ SAFETY DEFAULT
	// =========================

	static Collection<Attribute> neutralIfIncreased = List.of(
		Attribute.SCALE
	);

	static EnumMap<AttributePlayerNeutrality, Collection<Attribute>> attributePlayerNeutralityCollectionEnumMap = new EnumMap<>(
		Map.of(
			AttributePlayerNeutrality.POSITIVE, goodIfIncreased,
			AttributePlayerNeutrality.NEGATIVE, badIfIncreased,
			AttributePlayerNeutrality.NEUTRAL, neutralIfIncreased
		)
	);

	private static AttributePlayerNeutrality getAttributePlayerNeutrality(Attribute attribute) {
		return attributePlayerNeutralityCollectionEnumMap.entrySet()
			.stream()
			.filter(entry -> entry.getValue().contains(attribute))
			.findFirst()
			.get().getKey();
	}

	/**
	 * Combines the sign of the modifier with the “direction” of the attribute.
	 */
	private static ChatColor isModifierBeneficial(Attribute attribute, double amount) {
		AttributePlayerNeutrality attributePlayerNeutrality = getAttributePlayerNeutrality(attribute);
		if (attributePlayerNeutrality == AttributePlayerNeutrality.NEUTRAL) {
			return ChatColor.GRAY;
		}
		if(amount > 0) {
			if(attributePlayerNeutrality == AttributePlayerNeutrality.POSITIVE) {
				return ChatColor.BLUE;
			} else {
				return ChatColor.RED;
			}
		}
		if(amount < 0) {
			if(attributePlayerNeutrality == AttributePlayerNeutrality.POSITIVE) {
				return ChatColor.RED;
			} else {
				return ChatColor.BLUE;
			}
		}

		//Other ways impossible
		return null;
	}
}
