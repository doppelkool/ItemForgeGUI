package de.doppelkool.itemforgegui.Main;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeItem;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.PrepareInventoryResultEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A temporary Logger class to provide quick debug message access
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Logger {

	public static void log(ItemStack itemStack) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"ItemStack Log ->\n" +
				"at: %s\n" +
				"DisplayName: %s\n" +
				"Lore: %s\n" +
				"Type: %s\n" +
				"Disallowed Actions: %s\n" +
				"Effects: %s\n" +
				"AsString: %s",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			(itemStack.getItemMeta() != null) ? itemStack.getItemMeta().getDisplayName() : "meta null",
			(itemStack.getItemMeta() != null) ? itemStack.getItemMeta().getLore() : "meta null",
			itemStack.getType(),
			(itemStack.getItemMeta() != null) ? PreventionFlagManager.getInstance().mapItemFlags(itemStack.getItemMeta().getPersistentDataContainer()) : "meta null",
			(itemStack.getItemMeta() != null) ? itemStack.getItemMeta().getPersistentDataContainer().get(Main.getPlugin().getCustomArmorEffectsKey(),
				Main.getPlugin().getCustomArmorEffectListDataType()) : "meta null",
			itemStack
		));
	}

	public static void log(Entity e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Entity Log ->\n" +
				"at: %s\n" +
				"etype: %s\n" +
				"PersistentDataContainer: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getType().getTranslationKey(),
			getKeysAndValuesAsString(e)
		));
	}

	public static void log(PersistentDataHolder dataHolder) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"PersistentDataHolder Log ->\n" +
				"at: %s\n" +
				"PersistentDataContainer: %s\n" +
				"AsString: %s",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			getKeysAndValuesAsString(dataHolder),
			dataHolder
		));
	}

	private static String getKeysAndValuesAsString(PersistentDataHolder holder) {
		PersistentDataContainer pdc = holder.getPersistentDataContainer();

		return pdc.getKeys().stream()
			.map(key -> {
				String value = pdc.get(key, PersistentDataType.STRING);
				return "\"" + key.toString() + "\":\"" + (value != null ? value : "null") + "\"";
			})
			.collect(Collectors.joining(";"));
	}

	public static void log(PlayerEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"PlayerEvent Log ->\n" +
				"at: %s\n" +
				"Name: %s\n" +
				"Player: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName(),
			e.getPlayer().getName() + " : " + e.getPlayer().getUniqueId()
		));
	}

	public static void log(PlayerInteractEntityEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"PlayerInteractEntityEvent Log ->\n" +
				"at: %s\n" +
				"Name: %s\n" +
				"Player: %s\n" +
				"Entity: %s\n" +
				"MainHandItem: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName(),
			e.getPlayer().getName() + " : " + e.getPlayer().getUniqueId(),
			e.getRightClicked(),
			e.getPlayer().getInventory().getItemInMainHand().getType()
		));
	}

	public static void log(PrepareInventoryResultEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"PrepareInventoryResultEvent Log ->\n" +
				"at: %s\n" +
				"Name: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName()
		));
	}

	public static void log(ItemMeta e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		ArrayList<ForgeArmorEffect> forgeArmorEffects = e.getPersistentDataContainer().get(Main.getPlugin().getCustomArmorEffectsKey(), Main.getPlugin().getCustomArmorEffectListDataType());
		Bukkit.getLogger().info(String.format(
			"ItemMeta Log ->\n" +
				"at: %s\n" +
				"Lore: %s\n" +
				"Enchants: %s\n" +
				"Activated Effects: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			String.join(", ", e.getLore() == null ? new ArrayList<>() : e.getLore()),
			e.getEnchants(),
			forgeArmorEffects == null ? "" : forgeArmorEffects
				.stream()
				.map(eff -> eff.getType() + "<->" + eff.getAmplifier())
				.collect(Collectors.toList())
		));
	}

	public static void log(ArrayList<ForgeArmorEffect> e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"ArrayList Log ->\n" +
				"at: %s\n" +
				"Enchants: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.stream()
				.map(eff -> eff.getType() + "-" + eff.getAmplifier())
				.collect(Collectors.joining(","))
		));
	}

	public static void log(Map<PotionEffectType, Integer> e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.entrySet().stream()
				.map(eff -> eff.getKey().getTranslationKey() + "-" + eff.getValue())
				.collect(Collectors.toList())
		));
	}

	public static void log(List<String> e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			String.join(", ", e)
		));
	}

	public static void log(PlayerMenuUtility playerMenuUtility) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			playerMenuUtility.toString()
		));
	}

	public static void log(List<SlotItemWrapper.SlotItemExecute> list, int slot) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		String a = "";
		for (int i = 0; i < list.size(); i++) {
			SlotItemWrapper.SlotItemExecute a1 = list.get(i);
			a += a1.slot() + " : " + a1.item().getType() + " : " + a1.runnable();
		}

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			a
		));
	}

	public static void log(AttributeItem selectedAttribute) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if(selectedAttribute == null) {
			Bukkit.getLogger().info(String.format(
				"Map<PotionEffectType, Integer Log ->\n" +
					"at: %s\n" +
					"null",
				stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName()));
			return;
		}

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"attribute: %s\n",
				"getType: %s\n",
				"slot: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			selectedAttribute.getAttribute(),
			selectedAttribute.getItem().getType(),
			selectedAttribute.getSlot()
		));
	}

	public static void log( EnumMap<AttributeModifier. Operation, Double> enummap) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if(enummap == null) {
			Bukkit.getLogger().info(String.format(
				"Map<PotionEffectType, Integer Log ->\n" +
					"at: %s\n" +
					"null",
				stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName()));
			return;
		}

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"ADD_NUMBER: %s\n" +
				"ADD_SCALAR: %s\n" +
				"MULTIPLY_SCALAR_1: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			enummap.get(AttributeModifier.Operation.ADD_NUMBER),
			enummap.get(AttributeModifier.Operation.ADD_SCALAR),
			enummap.get(AttributeModifier.Operation.MULTIPLY_SCALAR_1)
		));
	}

	public static void log2(Map<EquipmentSlotGroup, Map<AttributeModifier.Operation, Double>> modifierValues) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			modifierValues.entrySet().stream()
				.map(eff -> eff.getKey().toString() + "-" + eff.getValue())
				.collect(Collectors.toList())
		));
	}
	public static void log3(Map<AttributeModifier.Operation, Double> modifierValues) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			modifierValues.entrySet().stream()
				.map(eff -> eff.getKey().toString() + "-" + eff.getValue())
				.collect(Collectors.toList())
		));
	}
	public static void log2(List<AttributeModifier> modifiers) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Map<PotionEffectType, Integer Log ->\n" +
				"at: %s\n" +
				"contains: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			modifiers
		));
	}
}
