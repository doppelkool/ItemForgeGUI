package de.doppelkool.itemforgegui.Main.MenuItems;

import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Level;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PotionEffectStacks {
	public static final SimpleEntry<PotionEffectType, ItemStack> speedItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> slownessItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> hasteItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> miningFatigueItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> strengthItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> instantHealthItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> instantDamageItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> jumpBoostItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> nauseaItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> regenerationItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> resistanceItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> fireResistanceItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> waterBreathingItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> invisibilityItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> blindnessItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> nightVisionItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> hungerItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> weaknessItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> poisonItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> witherItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> healthBoostItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> absorptionItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> saturationItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> glowingItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> levitationItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> luckItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> unluckItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> conduitPowerItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> slowFallingItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> badOmenItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> heroOfTheVillageItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> darknessItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> trialOmenItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> raidOmenItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> windChargedItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> weavingItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> oozingItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> infestedItem;
	public static final SimpleEntry<PotionEffectType, ItemStack> dolphinsGraceItem;

	public static final LinkedHashMap<PotionEffectType, ItemStack> potionEffectTypeToItemStack = new LinkedHashMap<>();
	public static final LinkedHashMap<Integer, PotionEffectType> itemStackIDToPotionEffectType = new LinkedHashMap<>();

	static {
		speedItem = new AbstractMap.SimpleEntry<>(PotionEffectType.SPEED, ItemStacks.makeItem(Material.FEATHER, ChatColor.GREEN + "Speed"));
		slownessItem = new AbstractMap.SimpleEntry<>(PotionEffectType.SLOWNESS, ItemStacks.makeItem(Material.COBWEB, ChatColor.GREEN + "Slowness"));
		hasteItem = new AbstractMap.SimpleEntry<>(PotionEffectType.HASTE, ItemStacks.makeItem(Material.REDSTONE, ChatColor.GREEN + "Haste"));
		miningFatigueItem = new AbstractMap.SimpleEntry<>(PotionEffectType.MINING_FATIGUE, ItemStacks.makeItem(Material.OBSIDIAN, ChatColor.GREEN + "Mining Fatigue"));
		strengthItem = new AbstractMap.SimpleEntry<>(PotionEffectType.STRENGTH, ItemStacks.makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Strength"));
		instantHealthItem = new AbstractMap.SimpleEntry<>(PotionEffectType.INSTANT_HEALTH, ItemStacks.makeItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Instant Health / Healing"));
		instantDamageItem = new AbstractMap.SimpleEntry<>(PotionEffectType.INSTANT_DAMAGE, ItemStacks.makeItem(Material.FERMENTED_SPIDER_EYE, ChatColor.GREEN + "Instant Damage / Harming"));
		jumpBoostItem = new AbstractMap.SimpleEntry<>(PotionEffectType.JUMP_BOOST, ItemStacks.makeItem(Material.RABBIT_FOOT, ChatColor.GREEN + "Jump Boost / Leaping"));
		nauseaItem = new AbstractMap.SimpleEntry<>(PotionEffectType.NAUSEA, ItemStacks.makeItem(Material.SLIME_BALL, ChatColor.GREEN + "Nausea"));
		regenerationItem = new AbstractMap.SimpleEntry<>(PotionEffectType.REGENERATION, ItemStacks.makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Regeneration"));
		resistanceItem = new AbstractMap.SimpleEntry<>(PotionEffectType.RESISTANCE, ItemStacks.makeItem(Material.SHIELD, ChatColor.GREEN + "Resistance"));
		fireResistanceItem = new AbstractMap.SimpleEntry<>(PotionEffectType.FIRE_RESISTANCE, ItemStacks.makeItem(Material.LAVA_BUCKET, ChatColor.GREEN + "Fire Resistance"));
		waterBreathingItem = new AbstractMap.SimpleEntry<>(PotionEffectType.WATER_BREATHING, ItemStacks.makeItem(Material.TURTLE_HELMET, ChatColor.GREEN + "Water Breathing"));
		invisibilityItem = new AbstractMap.SimpleEntry<>(PotionEffectType.INVISIBILITY, ItemStacks.makeItem(Material.GLASS, ChatColor.GREEN + "Invisibility"));
		blindnessItem = new AbstractMap.SimpleEntry<>(PotionEffectType.BLINDNESS, ItemStacks.makeItem(Material.BLACK_DYE, ChatColor.GREEN + "Blindness"));
		nightVisionItem = new AbstractMap.SimpleEntry<>(PotionEffectType.NIGHT_VISION, ItemStacks.makeItem(Material.GLOWSTONE_DUST, ChatColor.GREEN + "Night Vision"));
		hungerItem = new AbstractMap.SimpleEntry<>(PotionEffectType.HUNGER, ItemStacks.makeItem(Material.ROTTEN_FLESH, ChatColor.GREEN + "Hunger"));
		weaknessItem = new AbstractMap.SimpleEntry<>(PotionEffectType.WEAKNESS, ItemStacks.makeItem(Material.BONE, ChatColor.GREEN + "Weakness"));
		poisonItem = new AbstractMap.SimpleEntry<>(PotionEffectType.POISON, ItemStacks.makeItem(Material.SPIDER_EYE, ChatColor.GREEN + "Poison"));
		witherItem = new AbstractMap.SimpleEntry<>(PotionEffectType.WITHER, ItemStacks.makeItem(Material.WITHER_SKELETON_SKULL, ChatColor.GREEN + "Wither"));
		healthBoostItem = new AbstractMap.SimpleEntry<>(PotionEffectType.HEALTH_BOOST, ItemStacks.makeItem(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.GREEN + "Health Boost"));
		absorptionItem = new AbstractMap.SimpleEntry<>(PotionEffectType.ABSORPTION, ItemStacks.makeItem(Material.TOTEM_OF_UNDYING, ChatColor.GREEN + "Absorption"));
		saturationItem = new AbstractMap.SimpleEntry<>(PotionEffectType.SATURATION, ItemStacks.makeItem(Material.GOLDEN_CARROT, ChatColor.GREEN + "Saturation"));
		glowingItem = new AbstractMap.SimpleEntry<>(PotionEffectType.GLOWING, ItemStacks.makeItem(Material.SEA_LANTERN, ChatColor.GREEN + "Glowing"));
		levitationItem = new AbstractMap.SimpleEntry<>(PotionEffectType.LEVITATION, ItemStacks.makeItem(Material.PHANTOM_MEMBRANE, ChatColor.GREEN + "Levitation"));
		luckItem = new AbstractMap.SimpleEntry<>(PotionEffectType.LUCK, ItemStacks.makeItem(Material.EMERALD, ChatColor.GREEN + "Luck"));
		unluckItem = new AbstractMap.SimpleEntry<>(PotionEffectType.UNLUCK, ItemStacks.makeItem(Material.COAL, ChatColor.GREEN + "Unluck"));
		conduitPowerItem = new AbstractMap.SimpleEntry<>(PotionEffectType.CONDUIT_POWER, ItemStacks.makeItem(Material.HEART_OF_THE_SEA, ChatColor.GREEN + "Conduit Power"));
		slowFallingItem = new AbstractMap.SimpleEntry<>(PotionEffectType.SLOW_FALLING, ItemStacks.makeItem(Material.ELYTRA, ChatColor.GREEN + "Slow Falling"));
		badOmenItem = new AbstractMap.SimpleEntry<>(PotionEffectType.BAD_OMEN, ItemStacks.makeItem(Material.OMINOUS_BOTTLE, ChatColor.GREEN + "Bad Omen"));
		heroOfTheVillageItem = new AbstractMap.SimpleEntry<>(PotionEffectType.HERO_OF_THE_VILLAGE, ItemStacks.makeItem(Material.BELL, ChatColor.GREEN + "Hero of the Village"));
		darknessItem = new AbstractMap.SimpleEntry<>(PotionEffectType.DARKNESS, ItemStacks.makeItem(Material.BLACK_CONCRETE, ChatColor.GREEN + "Darkness"));
		trialOmenItem = new AbstractMap.SimpleEntry<>(PotionEffectType.TRIAL_OMEN, ItemStacks.makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Trial Omen"));
		raidOmenItem = new AbstractMap.SimpleEntry<>(PotionEffectType.RAID_OMEN, ItemStacks.makeItem(Material.RED_BANNER, ChatColor.GREEN + "Raid Omen"));
		windChargedItem = new AbstractMap.SimpleEntry<>(PotionEffectType.WIND_CHARGED, ItemStacks.makeItem(Material.TRIDENT, ChatColor.GREEN + "Wind Charging"));
		weavingItem = new AbstractMap.SimpleEntry<>(PotionEffectType.WEAVING, ItemStacks.makeItem(Material.STRING, ChatColor.GREEN + "Weaving"));
		oozingItem = new AbstractMap.SimpleEntry<>(PotionEffectType.OOZING, ItemStacks.makeItem(Material.SLIME_BLOCK, ChatColor.GREEN + "Oozing"));
		infestedItem = new AbstractMap.SimpleEntry<>(PotionEffectType.INFESTED, ItemStacks.makeItem(Material.WARPED_FUNGUS, ChatColor.GREEN + "Infestation"));
		dolphinsGraceItem = new AbstractMap.SimpleEntry<>(PotionEffectType.DOLPHINS_GRACE, ItemStacks.makeItem(Material.PRISMARINE_SHARD, ChatColor.GREEN + "Dolphin's Grace"));

		for (Field field : PotionEffectStacks.class.getDeclaredFields()) {
			int modifiers = field.getModifiers();
			if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers)) {
				continue;
			}

			// Check if the field's type is ItemStack
			if (!AbstractMap.SimpleEntry.class.isAssignableFrom(field.getType())) {
				continue;
			}

			try {
				@SuppressWarnings("unchecked")
				AbstractMap.SimpleEntry<PotionEffectType, ItemStack> itemStackPair = (AbstractMap.SimpleEntry<PotionEffectType, ItemStack>) field.get(null);
				ItemStack itemStack = itemStackPair.getValue();
				ItemMeta itemMeta = itemStack.getItemMeta();

				itemMeta.getPersistentDataContainer().set(
					Main.getPlugin().getCustomArmorEffectsKeyStackIDKey(),
					PersistentDataType.INTEGER, PotionEffectStacks.potionEffectTypeToItemStack.size() + 1);

				itemStack.setItemMeta(itemMeta);
				//itemStackPair.setValue(itemStack);
				PotionEffectStacks.itemStackIDToPotionEffectType.put(PotionEffectStacks.potionEffectTypeToItemStack.size() + 1, itemStackPair.getKey());
				PotionEffectStacks.potionEffectTypeToItemStack.put(itemStackPair.getKey(), itemStackPair.getValue());
			} catch (IllegalAccessException e) {
				Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
				Bukkit.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
			}
		}
	}
}
