package de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents;

import de.doppelkool.itemforgegui.API.ItemForgeGuiAPI;
import de.doppelkool.itemforgegui.Main.MenuServices.SignNumberEditor;
import lombok.*;
import org.bukkit.Color;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
@Setter
@ToString
public class PlayerMenuUtility {

	private Player owner;
	private ObservableObject<ItemStack> itemInHand;
	private Optional<ItemForgeGuiAPI.ItemForgeCallback> APICallback = Optional.empty();

	private Menu currentMenu = null;
	private boolean isMenuTransitioning = false;

	private int storedSlot;
	private Enchantment targetEnchantment;
	private AttributeStorage attributeStorage;
	private ModifyAttributeStorage modifyAttributeStorage;
	private PotionEffectType targetPotionEffectType;
	private SignNumberEditor signNumberEditor;
	private Color leatherColorPicker_ResetColor;

	public PlayerMenuUtility(Player pl) {
		this.owner = pl;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class AttributeStorage {
		private Attribute attribute;
		private EnumMap<AttributeModifier.Operation, Double> operationDoubleValues = new EnumMap<>(AttributeModifier.Operation.class);
		private EnumMap<EquipmentSlot, Boolean> slotMap = new EnumMap<>(EquipmentSlot.class);
		{
			slotMap.put(EquipmentSlot.HEAD, false);
			slotMap.put(EquipmentSlot.CHEST, false);
			slotMap.put(EquipmentSlot.LEGS, false);
			slotMap.put(EquipmentSlot.FEET, false);
			slotMap.put(EquipmentSlot.HAND, false);
			slotMap.put(EquipmentSlot.OFF_HAND, false);
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class ModifyAttributeStorage {
		//Slot Selection Menu
		private Attribute attribute;
		private Map<EquipmentSlotGroup, EnumMap<AttributeModifier.Operation, Double>> modifierValues = new HashMap<>();
		{
			modifierValues.put(EquipmentSlotGroup.HEAD, new EnumMap<>(AttributeModifier.Operation.class));
			modifierValues.put(EquipmentSlotGroup.CHEST, new EnumMap<>(AttributeModifier.Operation.class));
			modifierValues.put(EquipmentSlotGroup.LEGS, new EnumMap<>(AttributeModifier.Operation.class));
			modifierValues.put(EquipmentSlotGroup.FEET, new EnumMap<>(AttributeModifier.Operation.class));
			modifierValues.put(EquipmentSlotGroup.MAINHAND, new EnumMap<>(AttributeModifier.Operation.class));
			modifierValues.put(EquipmentSlotGroup.OFFHAND, new EnumMap<>(AttributeModifier.Operation.class));
		}

		//Value Selection Menu
		private EquipmentSlotGroup selectedSlotGroup;
		private EnumMap<AttributeModifier.Operation, Double> operationDoubleValues;
	}
}
