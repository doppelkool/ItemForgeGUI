package de.doppelkool.itemforgegui.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static org.bukkit.potion.PotionEffectType.BLINDNESS;
import static org.bukkit.potion.PotionEffectType.DARKNESS;
import static org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE;
import static org.bukkit.potion.PotionEffectType.GLOWING;
import static org.bukkit.potion.PotionEffectType.INFESTED;
import static org.bukkit.potion.PotionEffectType.INVISIBILITY;
import static org.bukkit.potion.PotionEffectType.NAUSEA;
import static org.bukkit.potion.PotionEffectType.NIGHT_VISION;
import static org.bukkit.potion.PotionEffectType.OOZING;
import static org.bukkit.potion.PotionEffectType.SLOW_FALLING;
import static org.bukkit.potion.PotionEffectType.TRIAL_OMEN;
import static org.bukkit.potion.PotionEffectType.WATER_BREATHING;
import static org.bukkit.potion.PotionEffectType.WEAVING;
import static org.bukkit.potion.PotionEffectType.WIND_CHARGED;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Resources {

	// Mapping of repairable items to their corresponding repair ingot/material.
	public static final Map<Material, Material> REPAIR_INGOT_MAP = Map.ofEntries(
		// Netherite tools and armor
		Map.entry(Material.NETHERITE_SWORD, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_AXE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_PICKAXE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_SHOVEL, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_HOE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_HELMET, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_CHESTPLATE, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_LEGGINGS, Material.NETHERITE_INGOT),
		Map.entry(Material.NETHERITE_BOOTS, Material.NETHERITE_INGOT),
		// Diamond tools and armor
		Map.entry(Material.DIAMOND_SWORD, Material.DIAMOND),
		Map.entry(Material.DIAMOND_AXE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_PICKAXE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_SHOVEL, Material.DIAMOND),
		Map.entry(Material.DIAMOND_HOE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_HELMET, Material.DIAMOND),
		Map.entry(Material.DIAMOND_CHESTPLATE, Material.DIAMOND),
		Map.entry(Material.DIAMOND_LEGGINGS, Material.DIAMOND),
		Map.entry(Material.DIAMOND_BOOTS, Material.DIAMOND),
		// Golden tools and armor
		Map.entry(Material.GOLDEN_SWORD, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_AXE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_PICKAXE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_SHOVEL, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_HOE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_HELMET, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_CHESTPLATE, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_LEGGINGS, Material.GOLD_INGOT),
		Map.entry(Material.GOLDEN_BOOTS, Material.GOLD_INGOT),
		// Iron tools and armor
		Map.entry(Material.IRON_SWORD, Material.IRON_INGOT),
		Map.entry(Material.IRON_AXE, Material.IRON_INGOT),
		Map.entry(Material.IRON_PICKAXE, Material.IRON_INGOT),
		Map.entry(Material.IRON_SHOVEL, Material.IRON_INGOT),
		Map.entry(Material.IRON_HOE, Material.IRON_INGOT),
		Map.entry(Material.IRON_HELMET, Material.IRON_INGOT),
		Map.entry(Material.IRON_CHESTPLATE, Material.IRON_INGOT),
		Map.entry(Material.IRON_LEGGINGS, Material.IRON_INGOT),
		Map.entry(Material.IRON_BOOTS, Material.IRON_INGOT),
		// Stone tools (using cobblestone)
		Map.entry(Material.STONE_SWORD, Material.COBBLESTONE),
		Map.entry(Material.STONE_AXE, Material.COBBLESTONE),
		Map.entry(Material.STONE_PICKAXE, Material.COBBLESTONE),
		Map.entry(Material.STONE_SHOVEL, Material.COBBLESTONE),
		Map.entry(Material.STONE_HOE, Material.COBBLESTONE),
		// Leather armor (using leather)
		Map.entry(Material.LEATHER_HELMET, Material.LEATHER),
		Map.entry(Material.LEATHER_CHESTPLATE, Material.LEATHER),
		Map.entry(Material.LEATHER_LEGGINGS, Material.LEATHER),
		Map.entry(Material.LEATHER_BOOTS, Material.LEATHER),
		// Chainmail armor (using iron)
		Map.entry(Material.CHAINMAIL_HELMET, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_CHESTPLATE, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_LEGGINGS, Material.IRON_INGOT),
		Map.entry(Material.CHAINMAIL_BOOTS, Material.IRON_INGOT),

		Map.entry(Material.ELYTRA, Material.PHANTOM_MEMBRANE),
		Map.entry(Material.TURTLE_HELMET, Material.TURTLE_SCUTE)
	);

	// Set of wooden planks used as repair material for wooden items.
	public static final Set<Material> WOODEN_PLANKS = Tag.PLANKS.getValues();

	public static final Set<Material> UPGRADABLE_ITEMS = Set.of(
		Material.DIAMOND_SWORD,
		Material.DIAMOND_AXE,
		Material.DIAMOND_PICKAXE,
		Material.DIAMOND_SHOVEL,
		Material.DIAMOND_HOE,
		Material.DIAMOND_HELMET,
		Material.DIAMOND_CHESTPLATE,
		Material.DIAMOND_LEGGINGS,
		Material.DIAMOND_BOOTS
	);

	public static final Set<Material> PLACEABLE_ITEMS = Set.of(
		Material.WATER_BUCKET,
		Material.LAVA_BUCKET,
		Material.POWDER_SNOW_BUCKET,
		Material.AXOLOTL_BUCKET,
		Material.TROPICAL_FISH_BUCKET,
		Material.PUFFERFISH_BUCKET,
		Material.SALMON_BUCKET,
		Material.COD_BUCKET,
		Material.TADPOLE_BUCKET,
		Material.WHEAT_SEEDS,
		Material.BEETROOT_SEEDS,
		Material.PUMPKIN_SEEDS,
		Material.MELON_SEEDS,
		Material.CARROT,
		Material.POTATO,
		Material.SWEET_BERRIES,
		Material.COCOA_BEANS,
		Material.ARMOR_STAND
	);

	public static final Set<Material> LAUNCHABLE_ITEMS = Set.of(
		Material.SNOWBALL,
		Material.EGG,
		Material.ENDER_PEARL,
		Material.TRIDENT,
		Material.ARROW,
		Material.TIPPED_ARROW,
		Material.SPECTRAL_ARROW,
		Material.SPLASH_POTION,
		Material.LINGERING_POTION,
		Material.EXPERIENCE_BOTTLE
	);

	public static final Set<PotionEffectType> CAPPED_POTION_EFFECT_TYPES = Set.of(
		BLINDNESS,
		DARKNESS,
		FIRE_RESISTANCE,
		GLOWING,
		INVISIBILITY,
		NAUSEA,
		NIGHT_VISION,
		TRIAL_OMEN,
		SLOW_FALLING,
		WATER_BREATHING,
		INFESTED,
		OOZING,
		WEAVING,
		WIND_CHARGED
	);

	public static final Set<Material> A_NO_DYED_VARIATION_EXIST = Set.of(
		Material.BUNDLE,
		Material.SHULKER_BOX,
		Material.TERRACOTTA,
		Material.GLASS,
		Material.GLASS_PANE,
		Material.CANDLE
	);

	public static final Set<Material> HORSE_ARMOR = Set.of(
		Material.DIAMOND_HORSE_ARMOR,
		Material.GOLDEN_HORSE_ARMOR,
		Material.LEATHER_HORSE_ARMOR,
		Material.IRON_HORSE_ARMOR
	);

	public static final Set<Material> EQUIPABLE_ITEMS_WITHOUT_HUMAN_ARMOR;
	public static final Set<Material> VANILLA_ENCHANTABLE_MATERIALS;
	public static final ArrayList<String> ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST = new ArrayList<>();
	public static final Set<Material> smeltableItems = new HashSet<>();

	static {
		Set<Material> set = new HashSet<>(HORSE_ARMOR);

		set.add(Material.ELYTRA);
		set.add(Material.SADDLE);

		set.add(Material.CARVED_PUMPKIN);
		set.addAll(Tag.ITEMS_SKULLS.getValues());
		EQUIPABLE_ITEMS_WITHOUT_HUMAN_ARMOR = Collections.unmodifiableSet(set);
	}

	static {
		Set<Material> set = new HashSet<>(REPAIR_INGOT_MAP.keySet());
		set.remove(Material.BOW);
		set.remove(Material.CROSSBOW);
		set.remove(Material.BOOK);

		set.addAll(HORSE_ARMOR);
		VANILLA_ENCHANTABLE_MATERIALS = Collections.unmodifiableSet(set);
	}

	static {
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_SHULKER_BOX");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_DYE");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_WOOL");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_BUNDLE");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_CANDLE");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_CONCRETE_POWDER");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_CONCRETE");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_GLAZED_TERRACOTTA");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_TERRACOTTA");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_STAINED_GLASS_PANE");
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_STAINED_GLASS");

		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_BED"); //exclution for "BEDROCK" in place
		ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST.add("_CARPET"); //exclution for "MOSS"y carpet variations in place
	}

	static {
		for (Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
			if (!(it.next() instanceof CookingRecipe<?> recipe)) continue;
			smeltableItems.add(recipe.getInput().getType());
		}
	}
}
