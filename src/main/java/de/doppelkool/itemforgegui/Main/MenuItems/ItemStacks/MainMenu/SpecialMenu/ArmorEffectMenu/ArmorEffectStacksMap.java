package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import oshi.util.tuples.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.logging.Level;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ArmorEffectStacksMap {
	public static final Pair<PotionEffectType, ItemStack> speedItem;
	public static final Pair<PotionEffectType, ItemStack> slownessItem;
	public static final Pair<PotionEffectType, ItemStack> hasteItem;
	public static final Pair<PotionEffectType, ItemStack> miningFatigueItem;
	public static final Pair<PotionEffectType, ItemStack> strengthItem;
	public static final Pair<PotionEffectType, ItemStack> instantHealthItem;
	public static final Pair<PotionEffectType, ItemStack> instantDamageItem;
	public static final Pair<PotionEffectType, ItemStack> jumpBoostItem;
	public static final Pair<PotionEffectType, ItemStack> nauseaItem;
	public static final Pair<PotionEffectType, ItemStack> regenerationItem;
	public static final Pair<PotionEffectType, ItemStack> resistanceItem;
	public static final Pair<PotionEffectType, ItemStack> fireResistanceItem;
	public static final Pair<PotionEffectType, ItemStack> waterBreathingItem;
	public static final Pair<PotionEffectType, ItemStack> invisibilityItem;
	public static final Pair<PotionEffectType, ItemStack> blindnessItem;
	public static final Pair<PotionEffectType, ItemStack> nightVisionItem;
	public static final Pair<PotionEffectType, ItemStack> hungerItem;
	public static final Pair<PotionEffectType, ItemStack> weaknessItem;
	public static final Pair<PotionEffectType, ItemStack> poisonItem;
	public static final Pair<PotionEffectType, ItemStack> witherItem;
	public static final Pair<PotionEffectType, ItemStack> healthBoostItem;
	public static final Pair<PotionEffectType, ItemStack> absorptionItem;
	public static final Pair<PotionEffectType, ItemStack> saturationItem;
	public static final Pair<PotionEffectType, ItemStack> glowingItem;
	public static final Pair<PotionEffectType, ItemStack> levitationItem;
	public static final Pair<PotionEffectType, ItemStack> luckItem;
	public static final Pair<PotionEffectType, ItemStack> unluckItem;
	public static final Pair<PotionEffectType, ItemStack> conduitPowerItem;
	public static final Pair<PotionEffectType, ItemStack> slowFallingItem;
	public static final Pair<PotionEffectType, ItemStack> badOmenItem;
	public static final Pair<PotionEffectType, ItemStack> heroOfTheVillageItem;
	public static final Pair<PotionEffectType, ItemStack> darknessItem;
	public static final Pair<PotionEffectType, ItemStack> trialOmenItem;
	public static final Pair<PotionEffectType, ItemStack> raidOmenItem;
	public static final Pair<PotionEffectType, ItemStack> windChargedItem;
	public static final Pair<PotionEffectType, ItemStack> weavingItem;
	public static final Pair<PotionEffectType, ItemStack> oozingItem;
	public static final Pair<PotionEffectType, ItemStack> infestedItem;
	public static final Pair<PotionEffectType, ItemStack> dolphinsGraceItem;

	public static final LinkedHashMap<PotionEffectType, ItemStack> potionEffectTypeToItemStack = new LinkedHashMap<>();
	public static final LinkedHashMap<Integer, PotionEffectType> itemStackIDToPotionEffectType = new LinkedHashMap<>();

	static {
		speedItem = new Pair<>(PotionEffectType.SPEED, ItemStackCreateHelper.makeItem(Material.FEATHER, ChatColor.GREEN + "Speed"));
		slownessItem = new Pair<>(PotionEffectType.SLOWNESS, ItemStackCreateHelper.makeItem(Material.COBWEB, ChatColor.GREEN + "Slowness"));
		hasteItem = new Pair<>(PotionEffectType.HASTE, ItemStackCreateHelper.makeItem(Material.REDSTONE, ChatColor.GREEN + "Haste"));
		miningFatigueItem = new Pair<>(PotionEffectType.MINING_FATIGUE, ItemStackCreateHelper.makeItem(Material.OBSIDIAN, ChatColor.GREEN + "Mining Fatigue"));
		strengthItem = new Pair<>(PotionEffectType.STRENGTH, ItemStackCreateHelper.makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Strength"));
		instantHealthItem = new Pair<>(PotionEffectType.INSTANT_HEALTH, ItemStackCreateHelper.makeItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Instant Health / Healing"));
		instantDamageItem = new Pair<>(PotionEffectType.INSTANT_DAMAGE, ItemStackCreateHelper.makeItem(Material.FERMENTED_SPIDER_EYE, ChatColor.GREEN + "Instant Damage / Harming"));
		jumpBoostItem = new Pair<>(PotionEffectType.JUMP_BOOST, ItemStackCreateHelper.makeItem(Material.RABBIT_FOOT, ChatColor.GREEN + "Jump Boost / Leaping"));
		nauseaItem = new Pair<>(PotionEffectType.NAUSEA, ItemStackCreateHelper.makeItem(Material.SLIME_BALL, ChatColor.GREEN + "Nausea"));
		regenerationItem = new Pair<>(PotionEffectType.REGENERATION, ItemStackCreateHelper.makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Regeneration"));
		resistanceItem = new Pair<>(PotionEffectType.RESISTANCE, ItemStackCreateHelper.makeItem(Material.SHIELD, ChatColor.GREEN + "Resistance"));
		fireResistanceItem = new Pair<>(PotionEffectType.FIRE_RESISTANCE, ItemStackCreateHelper.makeItem(Material.LAVA_BUCKET, ChatColor.GREEN + "Fire Resistance"));
		waterBreathingItem = new Pair<>(PotionEffectType.WATER_BREATHING, ItemStackCreateHelper.makeItem(Material.TURTLE_HELMET, ChatColor.GREEN + "Water Breathing"));
		invisibilityItem = new Pair<>(PotionEffectType.INVISIBILITY, ItemStackCreateHelper.makeItem(Material.GLASS, ChatColor.GREEN + "Invisibility"));
		blindnessItem = new Pair<>(PotionEffectType.BLINDNESS, ItemStackCreateHelper.makeItem(Material.BLACK_DYE, ChatColor.GREEN + "Blindness"));
		nightVisionItem = new Pair<>(PotionEffectType.NIGHT_VISION, ItemStackCreateHelper.makeItem(Material.GLOWSTONE_DUST, ChatColor.GREEN + "Night Vision"));
		hungerItem = new Pair<>(PotionEffectType.HUNGER, ItemStackCreateHelper.makeItem(Material.ROTTEN_FLESH, ChatColor.GREEN + "Hunger"));
		weaknessItem = new Pair<>(PotionEffectType.WEAKNESS, ItemStackCreateHelper.makeItem(Material.BONE, ChatColor.GREEN + "Weakness"));
		poisonItem = new Pair<>(PotionEffectType.POISON, ItemStackCreateHelper.makeItem(Material.SPIDER_EYE, ChatColor.GREEN + "Poison"));
		witherItem = new Pair<>(PotionEffectType.WITHER, ItemStackCreateHelper.makeItem(Material.WITHER_SKELETON_SKULL, ChatColor.GREEN + "Wither"));
		healthBoostItem = new Pair<>(PotionEffectType.HEALTH_BOOST, ItemStackCreateHelper.makeItem(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.GREEN + "Health Boost"));
		absorptionItem = new Pair<>(PotionEffectType.ABSORPTION, ItemStackCreateHelper.makeItem(Material.TOTEM_OF_UNDYING, ChatColor.GREEN + "Absorption"));
		saturationItem = new Pair<>(PotionEffectType.SATURATION, ItemStackCreateHelper.makeItem(Material.GOLDEN_CARROT, ChatColor.GREEN + "Saturation"));
		glowingItem = new Pair<>(PotionEffectType.GLOWING, ItemStackCreateHelper.makeItem(Material.SEA_LANTERN, ChatColor.GREEN + "Glowing"));
		levitationItem = new Pair<>(PotionEffectType.LEVITATION, ItemStackCreateHelper.makeItem(Material.PHANTOM_MEMBRANE, ChatColor.GREEN + "Levitation"));
		luckItem = new Pair<>(PotionEffectType.LUCK, ItemStackCreateHelper.makeItem(Material.EMERALD, ChatColor.GREEN + "Luck"));
		unluckItem = new Pair<>(PotionEffectType.UNLUCK, ItemStackCreateHelper.makeItem(Material.COAL, ChatColor.GREEN + "Unluck"));
		conduitPowerItem = new Pair<>(PotionEffectType.CONDUIT_POWER, ItemStackCreateHelper.makeItem(Material.HEART_OF_THE_SEA, ChatColor.GREEN + "Conduit Power"));
		slowFallingItem = new Pair<>(PotionEffectType.SLOW_FALLING, ItemStackCreateHelper.makeItem(Material.ELYTRA, ChatColor.GREEN + "Slow Falling"));
		badOmenItem = new Pair<>(PotionEffectType.BAD_OMEN, ItemStackCreateHelper.makeItem(Material.OMINOUS_BOTTLE, ChatColor.GREEN + "Bad Omen"));
		heroOfTheVillageItem = new Pair<>(PotionEffectType.HERO_OF_THE_VILLAGE, ItemStackCreateHelper.makeItem(Material.BELL, ChatColor.GREEN + "Hero of the Village"));
		darknessItem = new Pair<>(PotionEffectType.DARKNESS, ItemStackCreateHelper.makeItem(Material.BLACK_CONCRETE, ChatColor.GREEN + "Darkness"));
		trialOmenItem = new Pair<>(PotionEffectType.TRIAL_OMEN, ItemStackCreateHelper.makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Trial Omen"));
		raidOmenItem = new Pair<>(PotionEffectType.RAID_OMEN, ItemStackCreateHelper.makeItem(Material.RED_BANNER, ChatColor.GREEN + "Raid Omen"));
		windChargedItem = new Pair<>(PotionEffectType.WIND_CHARGED, ItemStackCreateHelper.makeItem(Material.TRIDENT, ChatColor.GREEN + "Wind Charging"));
		weavingItem = new Pair<>(PotionEffectType.WEAVING, ItemStackCreateHelper.makeItem(Material.STRING, ChatColor.GREEN + "Weaving"));
		oozingItem = new Pair<>(PotionEffectType.OOZING, ItemStackCreateHelper.makeItem(Material.SLIME_BLOCK, ChatColor.GREEN + "Oozing"));
		infestedItem = new Pair<>(PotionEffectType.INFESTED, ItemStackCreateHelper.makeItem(Material.WARPED_FUNGUS, ChatColor.GREEN + "Infestation"));
		dolphinsGraceItem = new Pair<>(PotionEffectType.DOLPHINS_GRACE, ItemStackCreateHelper.makeItem(Material.PRISMARINE_SHARD, ChatColor.GREEN + "Dolphin's Grace"));

		for (Field field : ArmorEffectStacksMap.class.getDeclaredFields()) {
			if (!Modifier.isPublic(field.getModifiers()) ||
				!Modifier.isStatic(field.getModifiers()) ||
				!Modifier.isFinal(field.getModifiers()) ||
				!Pair.class.isAssignableFrom(field.getType())) {
				continue;
			}

			try {
				@SuppressWarnings("unchecked")
				Pair<PotionEffectType, ItemStack> pair = (Pair<PotionEffectType, ItemStack>) field.get(null);

				PotionEffectType effectType = pair.getA();
				ItemStack itemStack = pair.getB();
				ItemMeta meta = itemStack.getItemMeta();

				int id = potionEffectTypeToItemStack.size() + 1;

				meta.getPersistentDataContainer().set(
					Main.getPlugin().getCustomArmorEffectsKeyStackIDKey(),
					PersistentDataType.INTEGER,
					id
				);

				itemStack.setItemMeta(meta);

				itemStackIDToPotionEffectType.put(id, effectType);
				potionEffectTypeToItemStack.put(effectType, itemStack);

			} catch (IllegalAccessException e) {
				Bukkit.getLogger().log(Level.SEVERE, "Failed to access field: " + field.getName(), e);
			}
		}
	}
}
