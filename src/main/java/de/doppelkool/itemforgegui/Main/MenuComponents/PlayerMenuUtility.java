package de.doppelkool.itemforgegui.Main.MenuComponents;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
@Setter
public class PlayerMenuUtility {

	private Player owner;

	private ItemStack tempStoredItem;
	private int storedSlot;
	private Enchantment targetEnchantment;
	private AttributeStorage attributeStorage;
	private PotionEffectType targetPotionEffectType;
	private SignNumberEditor signNumberEditor;

	public PlayerMenuUtility(Player pl) {
		this.owner = pl;
	}

	@Override
	public String toString() {
		return "{" +
			"owner=" + owner +
			",tempStoredItem=" + tempStoredItem +
			",storedSlot=" + storedSlot +
			",targetEnchantment=" + targetEnchantment +
			",targetPotionEffectType=" + targetPotionEffectType +
			",signNumberEditor=" + signNumberEditor +
			"}";
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttributeStorage {
		private Attribute attribute;
		private EnumMap<AttributeModifier.Operation, Double> modifierValues = new EnumMap<>(AttributeModifier.Operation.class);
		private EnumMap<EquipmentSlot, Boolean> slotMap = new EnumMap<>(EquipmentSlot.class);
		{
			slotMap.put(EquipmentSlot.HEAD, false);
			slotMap.put(EquipmentSlot.CHEST, false);
			slotMap.put(EquipmentSlot.LEGS, false);
			slotMap.put(EquipmentSlot.FEET, false);
			slotMap.put(EquipmentSlot.HAND, false);
			slotMap.put(EquipmentSlot.OFF_HAND, false);
		}

		@Override
		public String toString() {
			return "{" +
				"attribute=" + attribute +
				",modifierValues=" + modifierValues +
				",slotMap=" + Arrays.toString(slotMap.entrySet().toArray()) +
				"}";
		}

		public void fillByAttributeModifier(Attribute attribute, Collection<AttributeModifier> activeModifier) {
			this.attribute = attribute;

			activeModifier.forEach(modifier -> {

			});
		}
	}
}
