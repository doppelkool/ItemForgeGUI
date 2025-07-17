package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import oshi.util.tuples.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.logging.Level;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyPotionType;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeModifierStacksMap {

	/* defenseAndDurability */
	public static final Pair<Attribute, ItemStack> armor;
	public static final Pair<Attribute, ItemStack> armorToughness;
	public static final Pair<Attribute, ItemStack> explosionKnockbackResistance;
	public static final Pair<Attribute, ItemStack> fallDamageMultiplier;
	public static final Pair<Attribute, ItemStack> knockbackResistance;
	public static final Pair<Attribute, ItemStack> maxAbsorption;
	public static final Pair<Attribute, ItemStack> maxHealth;
	public static final Pair<Attribute, ItemStack> safeFallDistance;

	/* combat */
	public static final Pair<Attribute, ItemStack> attackDamage;
	public static final Pair<Attribute, ItemStack> attackKnockback;
	public static final Pair<Attribute, ItemStack> attackSpeed;
	public static final Pair<Attribute, ItemStack> sweepingDamageRatio;
	public static final Pair<Attribute, ItemStack> spawnReinforcements;

	/* mobility */
	public static final Pair<Attribute, ItemStack> flyingSpeed;
	public static final Pair<Attribute, ItemStack> gravity;
	public static final Pair<Attribute, ItemStack> jumpStrength;
	public static final Pair<Attribute, ItemStack> movementSpeed;
	public static final Pair<Attribute, ItemStack> sneakingSpeed;
	public static final Pair<Attribute, ItemStack> stepHeight;
	public static final Pair<Attribute, ItemStack> waterMovementEfficiency;

	/* interactionAndReach */
	public static final Pair<Attribute, ItemStack> blockBreakSpeed;
	public static final Pair<Attribute, ItemStack> blockInteractionRange;
	public static final Pair<Attribute, ItemStack> entityInteractionRange;
	public static final Pair<Attribute, ItemStack> movementEfficiency;
	public static final Pair<Attribute, ItemStack> miningEfficiency;

	/* perceptionAndAwareness */
	public static final Pair<Attribute, ItemStack> cameraDistance;
	public static final Pair<Attribute, ItemStack> followRange;
	public static final Pair<Attribute, ItemStack> temptRange;
	public static final Pair<Attribute, ItemStack> waypointReceiveRange;
	public static final Pair<Attribute, ItemStack> waypointTransmitRange;

	/* environmentalAndSurvival */
	public static final Pair<Attribute, ItemStack> burningTime;
	public static final Pair<Attribute, ItemStack> oxygenBonus;
	public static final Pair<Attribute, ItemStack> luck;
	public static final Pair<Attribute, ItemStack> scale;

	public static final LinkedHashMap<Attribute, ItemStack> attributeTypeToItemStack = new LinkedHashMap<>();
	public static final LinkedHashMap<Integer, Attribute> itemStackIDToAttributeType = new LinkedHashMap<>();

	static {
		ItemStack armorItems = makeItem(Material.NETHERITE_CHESTPLATE, ChatColor.GREEN + "Armor");
		modifyLore(armorItems, ChatColor.YELLOW + "Click to select attribute modifier");
		armor = new Pair<>(Attribute.ARMOR, armorItems);

		ItemStack armorToughnessItems = makeItem(Material.NETHERITE_AXE, ChatColor.GREEN + "Armor Toughness");
		modifyLore(armorToughnessItems, ChatColor.YELLOW + "Click to select attribute modifier");
		armorToughness = new Pair<>(Attribute.ARMOR_TOUGHNESS, armorToughnessItems);

		ItemStack explosionKnockbackResistanceItems = makeItem(Material.CREEPER_HEAD, ChatColor.GREEN + "Exploration Knockback Resistance");
		modifyLore(explosionKnockbackResistanceItems, ChatColor.YELLOW + "Click to select attribute modifier");
		explosionKnockbackResistance = new Pair<>(Attribute.EXPLOSION_KNOCKBACK_RESISTANCE, explosionKnockbackResistanceItems);

		ItemStack knockbackResistanceItems = makeItem(Material.SHIELD, ChatColor.GREEN + "Knockback Resistance");
		modifyLore(knockbackResistanceItems, ChatColor.YELLOW + "Click to select attribute modifier");
		knockbackResistance = new Pair<>(Attribute.KNOCKBACK_RESISTANCE, knockbackResistanceItems);

		ItemStack fallDamageMultiplierItems = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Fall Damage Multiplier");
		modifyLore(fallDamageMultiplierItems, ChatColor.YELLOW + "Click to select attribute modifier");
		fallDamageMultiplier = new Pair<>(Attribute.FALL_DAMAGE_MULTIPLIER, fallDamageMultiplierItems);

		ItemStack maxAbsorptionItems = makeItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Max Absorption");
		modifyLore(maxAbsorptionItems, ChatColor.YELLOW + "Click to select attribute modifier");
		maxAbsorption = new Pair<>(Attribute.MAX_ABSORPTION, maxAbsorptionItems);

		ItemStack maxHealthItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Max Health");
		modifyLore(maxHealthItems, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(maxHealthItems, PotionType.HEALING);
		maxHealth = new Pair<>(Attribute.MAX_HEALTH, maxHealthItems);

		ItemStack safeFallDistanceItems = makeItem(Material.SLIME_BLOCK, ChatColor.GREEN + "Safe Fall Distance");
		modifyLore(safeFallDistanceItems, ChatColor.YELLOW + "Click to select attribute modifier");
		safeFallDistance = new Pair<>(Attribute.SAFE_FALL_DISTANCE, safeFallDistanceItems);



		ItemStack attackDamageItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Attack Damage");
		modifyLore(attackDamageItems, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(attackDamageItems, PotionType.STRENGTH);
		attackDamage = new Pair<>(Attribute.ATTACK_DAMAGE, attackDamageItems);

		ItemStack attackKnockbackItems = makeItem(Material.SHIELD, ChatColor.GREEN + "Attack Knockback");
		modifyLore(attackKnockbackItems, ChatColor.YELLOW + "Click to select attribute modifier");
		attackKnockback = new Pair<>(Attribute.ATTACK_KNOCKBACK, attackKnockbackItems);

		ItemStack attackSpeedItems = makeItem(Material.MACE, ChatColor.GREEN + "Attack Speed");
		modifyLore(attackSpeedItems, ChatColor.YELLOW + "Click to select attribute modifier");
		attackSpeed = new Pair<>(Attribute.ATTACK_SPEED, attackSpeedItems);

		ItemStack sweepingDamageRatioItems = makeItem(Material.IRON_SWORD, ChatColor.GREEN + "Sweeping Damage Ratio");
		modifyLore(sweepingDamageRatioItems, ChatColor.YELLOW + "Click to select attribute modifier");
		sweepingDamageRatio = new Pair<>(Attribute.SWEEPING_DAMAGE_RATIO, sweepingDamageRatioItems);

		ItemStack spawnReinforcementsItems = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Spawn Reinforcements");
		modifyLore(spawnReinforcementsItems, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyToCustomHead(spawnReinforcementsItems, SkullData.DROWNED);
		spawnReinforcements = new Pair<>(Attribute.SPAWN_REINFORCEMENTS, spawnReinforcementsItems);



		ItemStack flyingSpeedItems = makeItem(Material.FIREWORK_ROCKET, ChatColor.GREEN + "Flying Speed");
		modifyLore(flyingSpeedItems, ChatColor.YELLOW + "Click to select attribute modifier");
		flyingSpeed = new Pair<>(Attribute.FLYING_SPEED, flyingSpeedItems);

		ItemStack gravityItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Gravity");
		modifyLore(gravityItems, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(gravityItems, PotionType.SLOW_FALLING);
		gravity = new Pair<>(Attribute.GRAVITY, gravityItems);

		ItemStack jumpStrengthItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Jump Strength");
		modifyLore(jumpStrengthItems, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(jumpStrengthItems, PotionType.LEAPING);
		jumpStrength = new Pair<>(Attribute.JUMP_STRENGTH, jumpStrengthItems);

		ItemStack movementSpeedItems = makeItem(Material.ICE, ChatColor.GREEN + "Movement Speed");
		modifyLore(movementSpeedItems, ChatColor.YELLOW + "Click to select attribute modifier");
		movementSpeed = new Pair<>(Attribute.MOVEMENT_SPEED, movementSpeedItems);

		ItemStack sneakingSpeedItems = makeItem(Material.TURTLE_HELMET, ChatColor.GREEN + "Sneaking Speed");
		modifyLore(sneakingSpeedItems, ChatColor.YELLOW + "Click to select attribute modifier");
		sneakingSpeed = new Pair<>(Attribute.SNEAKING_SPEED, sneakingSpeedItems);

		ItemStack stepHeightItems = makeItem(Material.EXPOSED_CUT_COPPER_STAIRS, ChatColor.GREEN + "Step Height");
		modifyLore(stepHeightItems, ChatColor.YELLOW + "Click to select attribute modifier");
		stepHeight = new Pair<>(Attribute.STEP_HEIGHT, stepHeightItems);

		ItemStack waterMovementEfficiencyItems = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Water Movement Efficiency");
		modifyLore(waterMovementEfficiencyItems, ChatColor.YELLOW + "Click to select attribute modifier");
		waterMovementEfficiency = new Pair<>(Attribute.WATER_MOVEMENT_EFFICIENCY, waterMovementEfficiencyItems);



		ItemStack blockBreakSpeedItems = makeItem(Material.GOLDEN_PICKAXE, ChatColor.GREEN + "Block Break Speed");
		modifyLore(blockBreakSpeedItems, ChatColor.YELLOW + "Click to select attribute modifier");
		blockBreakSpeed = new Pair<>(Attribute.BLOCK_BREAK_SPEED, blockBreakSpeedItems);

		ItemStack blockInteractionRangeItems = makeItem(Material.DISPENSER, ChatColor.GREEN + "Block Interaction Range");
		modifyLore(blockInteractionRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		blockInteractionRange = new Pair<>(Attribute.BLOCK_INTERACTION_RANGE, blockInteractionRangeItems);

		ItemStack entityInteractionRangeItems = makeItem(Material.DROPPER, ChatColor.GREEN + "Entity Interaction Range");
		modifyLore(entityInteractionRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		entityInteractionRange = new Pair<>(Attribute.ENTITY_INTERACTION_RANGE, entityInteractionRangeItems);

		ItemStack movementEfficiencyItems = makeItem(Material.GOLDEN_CARROT, ChatColor.GREEN + "Movement Efficiency");
		modifyLore(movementEfficiencyItems, ChatColor.YELLOW + "Click to select attribute modifier");
		movementEfficiency = new Pair<>(Attribute.MOVEMENT_EFFICIENCY, movementEfficiencyItems);

		ItemStack miningEfficiencyItems = makeItem(Material.NETHERITE_PICKAXE, ChatColor.GREEN + "Mining Efficiency");
		modifyLore(miningEfficiencyItems, ChatColor.YELLOW + "Click to select attribute modifier");
		miningEfficiency = new Pair<>(Attribute.MINING_EFFICIENCY, miningEfficiencyItems);



		ItemStack cameraDistanceItems = makeItem(Material.ENDER_EYE, ChatColor.GREEN + "Camera Distance");
		modifyLore(cameraDistanceItems, ChatColor.YELLOW + "Click to select attribute modifier");
		cameraDistance = new Pair<>(Attribute.CAMERA_DISTANCE, cameraDistanceItems);

		ItemStack followRangeItems = makeItem(Material.ZOMBIE_HEAD, ChatColor.GREEN + "Follow Range");
		modifyLore(followRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		followRange = new Pair<>(Attribute.FOLLOW_RANGE, followRangeItems);

		ItemStack temptRangeItems = makeItem(Material.WHEAT_SEEDS, ChatColor.GREEN + "Tempt Range");
		modifyLore(temptRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		temptRange = new Pair<>(Attribute.TEMPT_RANGE, temptRangeItems);

		ItemStack waypointReceiveRangeItems = makeItem(Material.CAT_SPAWN_EGG, ChatColor.GREEN + "Waypoint Receive Range");
		modifyLore(waypointReceiveRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		waypointReceiveRange = new Pair<>(Attribute.WAYPOINT_RECEIVE_RANGE, waypointReceiveRangeItems);

		ItemStack waypointTransmitRangeItems = makeItem(Material.CHICKEN_SPAWN_EGG, ChatColor.GREEN + "Waypoint Transmit Range");
		modifyLore(waypointTransmitRangeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		waypointTransmitRange = new Pair<>(Attribute.WAYPOINT_TRANSMIT_RANGE, waypointTransmitRangeItems);



		ItemStack burningTimeItems = makeItem(Material.FLINT_AND_STEEL, ChatColor.GREEN + "Burning Time");
		modifyLore(burningTimeItems, ChatColor.YELLOW + "Click to select attribute modifier");
		burningTime = new Pair<>(Attribute.BURNING_TIME, burningTimeItems);

		ItemStack oxygenBonusItems = makeItem(Material.OAK_DOOR, ChatColor.GREEN + "Oxygen Bonus");
		modifyLore(oxygenBonusItems, ChatColor.YELLOW + "Click to select attribute modifier");
		oxygenBonus = new Pair<>(Attribute.OXYGEN_BONUS, oxygenBonusItems);

		ItemStack luckItems = makeItem(Material.NAUTILUS_SHELL, ChatColor.GREEN + "Luck");
		modifyLore(luckItems, ChatColor.YELLOW + "Click to select attribute modifier");
		luck = new Pair<>(Attribute.LUCK, luckItems);

		ItemStack scaleItems = makeItem(Material.AMETHYST_CLUSTER, ChatColor.GREEN + "Scale");
		modifyLore(scaleItems, ChatColor.YELLOW + "Click to select attribute modifier");
		scale = new Pair<>(Attribute.SCALE, scaleItems);


		for (Field field : AttributeModifierStacksMap.class.getDeclaredFields()) {
			if (!Modifier.isPublic(field.getModifiers()) ||
				!Modifier.isStatic(field.getModifiers()) ||
				!Modifier.isFinal(field.getModifiers()) ||
				!Pair.class.isAssignableFrom(field.getType())) {
				continue;
			}

			try {
				@SuppressWarnings("unchecked")
				Pair<Attribute, ItemStack> pair = (Pair<Attribute, ItemStack>) field.get(null);

				Attribute effectType = pair.getA();
				ItemStack itemStack = pair.getB();
				ItemMeta meta = itemStack.getItemMeta();

				int id = attributeTypeToItemStack.size() + 1;

				meta.getPersistentDataContainer().set(
					Main.getPlugin().getCustomAttributeModifierKeyStackIDKey(),
					PersistentDataType.INTEGER,
					id
				);

				itemStack.setItemMeta(meta);

				itemStackIDToAttributeType.put(id, effectType);
				attributeTypeToItemStack.put(effectType, itemStack);

			} catch (IllegalAccessException e) {
				Bukkit.getLogger().log(Level.SEVERE, "Failed to access field: " + field.getName(), e);
			}
		}
	}
}
