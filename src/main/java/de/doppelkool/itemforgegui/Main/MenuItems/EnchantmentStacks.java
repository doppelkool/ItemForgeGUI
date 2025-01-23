package de.doppelkool.itemforgegui.Main.MenuItems;

import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyItemFlags;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EnchantmentStacks {
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> PROTECTION_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FIRE_PROTECTION_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FEATHER_FALLING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> BLAST_PROTECTION_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> PROJECTILE_PROTECTION_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> RESPIRATION_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> AQUA_AFFINITY_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> THORNS_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> DEPTH_STRIDER_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FROST_WALKER_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> BINDING_CURSE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SHARPNESS_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SMITE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> BANE_OF_ARTHROPODS_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> KNOCKBACK_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FIRE_ASPECT_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> LOOTING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SWEEPING_EDGE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> EFFICIENCY_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SILK_TOUCH_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> UNBREAKING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FORTUNE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> POWER_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> PUNCH_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> FLAME_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> INFINITY_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> LUCK_OF_THE_SEA_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> LURE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> LOYALTY_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> IMPALING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> RIPTIDE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> CHANNELING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> MULTISHOT_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> QUICK_CHARGE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> PIERCING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> DENSITY_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> BREACH_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> WIND_BURST_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> MENDING_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> VANISHING_CURSE_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SOUL_SPEED_item;
	public static final AbstractMap.SimpleEntry<Enchantment, ItemStack> SWIFT_SNEAK_item;

	public static final LinkedHashMap<Enchantment, ItemStack> enchantmentsToItemStack = new LinkedHashMap<>();
	public static final LinkedHashMap<Integer, Enchantment> itemStackIDToEnchantment = new LinkedHashMap<>();

	static {
		PROTECTION_item = new AbstractMap.SimpleEntry<>(Enchantment.PROTECTION, makeItem(Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Protection"));
		modifyItemFlags(PROTECTION_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		FIRE_PROTECTION_item = new AbstractMap.SimpleEntry<>(Enchantment.FIRE_PROTECTION, makeItem(Material.POTION, ChatColor.GREEN + "Fire Protection"));
		PotionMeta fireprotMeta = (PotionMeta) FIRE_PROTECTION_item.getValue().getItemMeta();
		fireprotMeta.setBasePotionType(PotionType.FIRE_RESISTANCE);
		FIRE_PROTECTION_item.getValue().setItemMeta(fireprotMeta);
		modifyItemFlags(FIRE_PROTECTION_item.getValue(), ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ATTRIBUTES);

		FEATHER_FALLING_item = new AbstractMap.SimpleEntry<>(Enchantment.FEATHER_FALLING, makeItem(Material.WIND_CHARGE, ChatColor.GREEN + "Feather Falling"));
		BLAST_PROTECTION_item = new AbstractMap.SimpleEntry<>(Enchantment.BLAST_PROTECTION, makeItem(Material.TNT, ChatColor.GREEN + "Blast Protection"));

		PROJECTILE_PROTECTION_item = new AbstractMap.SimpleEntry<>(Enchantment.PROJECTILE_PROTECTION, makeItem(Material.CHAINMAIL_LEGGINGS, ChatColor.GREEN + "Projectile Protection"));
		modifyItemFlags(PROJECTILE_PROTECTION_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		RESPIRATION_item = new AbstractMap.SimpleEntry<>(Enchantment.RESPIRATION, makeItem(Material.TROPICAL_FISH_BUCKET, ChatColor.GREEN + "Respiration"));

		AQUA_AFFINITY_item = new AbstractMap.SimpleEntry<>(Enchantment.AQUA_AFFINITY, makeItem(Material.GOLDEN_PICKAXE, ChatColor.GREEN + "Aqua Affinity"));
		modifyItemFlags(AQUA_AFFINITY_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		THORNS_item = new AbstractMap.SimpleEntry<>(Enchantment.THORNS, makeItem(Material.CACTUS, ChatColor.GREEN + "Thorns"));

		DEPTH_STRIDER_item = new AbstractMap.SimpleEntry<>(Enchantment.DEPTH_STRIDER, makeItem(Material.DIAMOND_BOOTS, ChatColor.GREEN + "Depth Strider"));
		modifyItemFlags(DEPTH_STRIDER_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		FROST_WALKER_item = new AbstractMap.SimpleEntry<>(Enchantment.FROST_WALKER, makeItem(Material.ICE, ChatColor.GREEN + "Frost Walker"));
		BINDING_CURSE_item = new AbstractMap.SimpleEntry<>(Enchantment.BINDING_CURSE, makeItem(Material.LEAD, ChatColor.GREEN + "Curse of Binding"));

		SHARPNESS_item = new AbstractMap.SimpleEntry<>(Enchantment.SHARPNESS, makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Sharpness"));
		modifyItemFlags(SHARPNESS_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		SMITE_item = new AbstractMap.SimpleEntry<>(Enchantment.SMITE, makeItem(Material.NETHERITE_AXE, ChatColor.GREEN + "Smite"));
		modifyItemFlags(SMITE_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		BANE_OF_ARTHROPODS_item = new AbstractMap.SimpleEntry<>(Enchantment.BANE_OF_ARTHROPODS, makeItem(Material.SPIDER_EYE, ChatColor.GREEN + "Bane of Arthropods"));
		KNOCKBACK_item = new AbstractMap.SimpleEntry<>(Enchantment.KNOCKBACK, makeItem(Material.SLIME_BALL, ChatColor.GREEN + "Knockback"));
		FIRE_ASPECT_item = new AbstractMap.SimpleEntry<>(Enchantment.FIRE_ASPECT, makeItem(Material.FIRE_CHARGE, ChatColor.GREEN + "Fire Aspect"));

		LOOTING_item = new AbstractMap.SimpleEntry<>(Enchantment.LOOTING, makeItem(Material.BUNDLE, ChatColor.GREEN + "Looting"));
		modifyItemFlags(LOOTING_item.getValue(), ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

		SWEEPING_EDGE_item = new AbstractMap.SimpleEntry<>(Enchantment.SWEEPING_EDGE, makeItem(Material.STONE_SWORD, ChatColor.GREEN + "Sweeping Edge"));
		modifyItemFlags(SWEEPING_EDGE_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		EFFICIENCY_item = new AbstractMap.SimpleEntry<>(Enchantment.EFFICIENCY, makeItem(Material.NETHERITE_PICKAXE, ChatColor.GREEN + "Efficiency"));
		modifyItemFlags(EFFICIENCY_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		SILK_TOUCH_item = new AbstractMap.SimpleEntry<>(Enchantment.SILK_TOUCH, makeItem(Material.BRUSH, ChatColor.GREEN + "Silk Touch"));

		UNBREAKING_item = new AbstractMap.SimpleEntry<>(Enchantment.UNBREAKING, makeItem(Material.NETHERITE_HOE, ChatColor.GREEN + "Unbreaking"));
		modifyItemFlags(UNBREAKING_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		FORTUNE_item = new AbstractMap.SimpleEntry<>(Enchantment.FORTUNE, makeItem(Material.DIAMOND, ChatColor.GREEN + "Fortune"));

		POWER_item = new AbstractMap.SimpleEntry<>(Enchantment.POWER, makeItem(Material.DIAMOND_HORSE_ARMOR, ChatColor.GREEN + "Power"));
		modifyItemFlags(POWER_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		PUNCH_item = new AbstractMap.SimpleEntry<>(Enchantment.PUNCH, makeItem(Material.ARROW, ChatColor.GREEN + "Punch"));
		FLAME_item = new AbstractMap.SimpleEntry<>(Enchantment.FLAME, makeItem(Material.FIRE_CHARGE, ChatColor.GREEN + "Flame"));
		INFINITY_item = new AbstractMap.SimpleEntry<>(Enchantment.INFINITY, makeItem(Material.BEDROCK, ChatColor.GREEN + "Infinity"));
		LUCK_OF_THE_SEA_item = new AbstractMap.SimpleEntry<>(Enchantment.LUCK_OF_THE_SEA, makeItem(Material.HEART_OF_THE_SEA, ChatColor.GREEN + "Luck of the Sea"));
		LURE_item = new AbstractMap.SimpleEntry<>(Enchantment.LURE, makeItem(Material.FISHING_ROD, ChatColor.GREEN + "Lure"));

		LOYALTY_item = new AbstractMap.SimpleEntry<>(Enchantment.LOYALTY, makeItem(Material.TRIDENT, ChatColor.GREEN + "Loyalty"));
		modifyItemFlags(LOYALTY_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		IMPALING_item = new AbstractMap.SimpleEntry<>(Enchantment.IMPALING, makeItem(Material.PUFFERFISH, ChatColor.GREEN + "Impaling"));
		RIPTIDE_item = new AbstractMap.SimpleEntry<>(Enchantment.RIPTIDE, makeItem(Material.SEAGRASS, ChatColor.GREEN + "Riptide"));

		CHANNELING_item = new AbstractMap.SimpleEntry<>(Enchantment.CHANNELING, makeItem(Material.TRIDENT, ChatColor.GREEN + "Channeling")); // enchanted
		modifyItemFlags(CHANNELING_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		MULTISHOT_item = new AbstractMap.SimpleEntry<>(Enchantment.MULTISHOT, makeItem(Material.CROSSBOW, ChatColor.GREEN + "Multishot"));

		QUICK_CHARGE_item = new AbstractMap.SimpleEntry<>(Enchantment.QUICK_CHARGE, makeItem(Material.MACE, ChatColor.GREEN + "Quick Charge"));
		modifyItemFlags(QUICK_CHARGE_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		PIERCING_item = new AbstractMap.SimpleEntry<>(Enchantment.PIERCING, makeItem(Material.CROSSBOW, ChatColor.GREEN + "Piercing")); // enchanted

		DENSITY_item = new AbstractMap.SimpleEntry<>(Enchantment.DENSITY, makeItem(Material.MACE, ChatColor.GREEN + "Density")); // enchanted
		modifyItemFlags(DENSITY_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		BREACH_item = new AbstractMap.SimpleEntry<>(Enchantment.BREACH, makeItem(Material.MACE, ChatColor.GREEN + "Breach"));
		modifyItemFlags(BREACH_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		WIND_BURST_item = new AbstractMap.SimpleEntry<>(Enchantment.WIND_BURST, makeItem(Material.MACE, ChatColor.GREEN + "Wind Burst"));
		modifyItemFlags(WIND_BURST_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		MENDING_item = new AbstractMap.SimpleEntry<>(Enchantment.MENDING, makeItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Mending")); // enchanted
		VANISHING_CURSE_item = new AbstractMap.SimpleEntry<>(Enchantment.VANISHING_CURSE, makeItem(Material.END_CRYSTAL, ChatColor.GREEN + "Curse of Vanishing"));
		SOUL_SPEED_item = new AbstractMap.SimpleEntry<>(Enchantment.SOUL_SPEED, makeItem(Material.SOUL_SAND, ChatColor.GREEN + "Soul Speed"));

		SWIFT_SNEAK_item = new AbstractMap.SimpleEntry<>(Enchantment.SWIFT_SNEAK, makeItem(Material.GOLDEN_BOOTS, ChatColor.GREEN + "Swift Sneak"));
		modifyItemFlags(SWIFT_SNEAK_item.getValue(), ItemFlag.HIDE_ATTRIBUTES);

		for (Field field : EnchantmentStacks.class.getDeclaredFields()) {
			int modifiers = field.getModifiers();
			if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
				// Check if the field's type is ItemStack
				if (AbstractMap.SimpleEntry.class.isAssignableFrom(field.getType())) {
					try {
						@SuppressWarnings("unchecked")
						AbstractMap.SimpleEntry<Enchantment, ItemStack> itemStackPair = (AbstractMap.SimpleEntry<Enchantment, ItemStack>) field.get(null);
						ItemStack itemStack = itemStackPair.getValue();
						ItemMeta itemMeta = itemStack.getItemMeta();

						itemMeta.getPersistentDataContainer().set(
							Main.getPlugin().getCustomEnchantmentStackIDKey(),
							PersistentDataType.INTEGER, enchantmentsToItemStack.size()+1);

						itemStack.setItemMeta(itemMeta);
						//itemStackPair.setValue(itemStack);
						itemStackIDToEnchantment.put(enchantmentsToItemStack.size()+1, itemStackPair.getKey());
						enchantmentsToItemStack.put(itemStackPair.getKey(), itemStackPair.getValue());
					} catch (IllegalAccessException e) {
						Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
						Bukkit.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
					}
				}
			}
		}
	}

	public static ArrayList<Enchantment> getAllDeactivatedEnchantments(ItemStack is) {
		ArrayList<Enchantment> list = new ArrayList<>();

		Map<Enchantment, Integer> activatedEnchantments = is.getItemMeta().getEnchants();

		for(Enchantment enchantment : enchantmentsToItemStack.keySet()) {
			if(activatedEnchantments.get(enchantment) == null) {
				list.add(enchantment);
			}
		}

		return list;
	}
}
