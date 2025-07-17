package de.doppelkool.itemforgegui.Main.MenuComponents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

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

	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttributeStorage {
		private Attribute attribute;
		private double amount;
		private AttributeModifier.Operation operation;
		private EquipmentSlotGroup slotGroup;
	}
}
