package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyPotionType;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeRegistryFiller {

	public static List<AttributeItem> loadDefenseAndDurability() {
		ArrayList<AttributeItem> defenseAndDurabilityItems = new ArrayList<>();

		ItemStack armorItems = makeItem(Material.NETHERITE_CHESTPLATE, ChatColor.GREEN + "Armor");
		modifyLore(armorItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(armorItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			20,
			armorItems, Attribute.ARMOR
		));

		ItemStack armorToughnessItems = makeItem(Material.NETHERITE_AXE, ChatColor.GREEN + "Armor Toughness");
		modifyLore(armorToughnessItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(armorToughnessItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			21,
			armorToughnessItems, Attribute.ARMOR_TOUGHNESS
		));

		ItemStack explosionKnockbackResistanceItems = makeItem(Material.TNT, ChatColor.GREEN + "Exploration Knockback Resistance");
		modifyLore(explosionKnockbackResistanceItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(explosionKnockbackResistanceItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			22,
			explosionKnockbackResistanceItems, Attribute.EXPLOSION_KNOCKBACK_RESISTANCE
		));

		ItemStack knockbackResistanceItems = makeItem(Material.SHIELD, ChatColor.GREEN + "Knockback Resistance");
		modifyLore(knockbackResistanceItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(knockbackResistanceItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			23,
			knockbackResistanceItems, Attribute.KNOCKBACK_RESISTANCE
		));

		ItemStack fallDamageMultiplierItems = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Fall Damage Multiplier");
		modifyLore(fallDamageMultiplierItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(fallDamageMultiplierItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			24,
			fallDamageMultiplierItems, Attribute.FALL_DAMAGE_MULTIPLIER
		));

		ItemStack maxAbsorptionItems = makeItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Max Absorption");
		modifyLore(maxAbsorptionItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(maxAbsorptionItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			30,
			maxAbsorptionItems, Attribute.MAX_ABSORPTION
		));

		ItemStack maxHealthItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Max Health");
		modifyLore(maxHealthItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(maxHealthItems, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifyPotionType(maxHealthItems, PotionType.HEALING);
		defenseAndDurabilityItems.add(new AttributeItem(
			31,
			maxHealthItems, Attribute.MAX_HEALTH
		));

		ItemStack safeFallDistanceItems = makeItem(Material.SLIME_BLOCK, ChatColor.GREEN + "Safe Fall Distance");
		modifyLore(safeFallDistanceItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(safeFallDistanceItems, ItemFlag.HIDE_ENCHANTS);
		defenseAndDurabilityItems.add(new AttributeItem(
			32,
			safeFallDistanceItems, Attribute.SAFE_FALL_DISTANCE
		));

		return defenseAndDurabilityItems;
	}

	public static List<AttributeItem> loadCombat() {
		ArrayList<AttributeItem> combatItems = new ArrayList<>();

		ItemStack attackDamageItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Attack Damage");
		modifyLore(attackDamageItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(attackDamageItems, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifyPotionType(attackDamageItems, PotionType.STRENGTH);
		combatItems.add(new AttributeItem(
			21,
			attackDamageItems, Attribute.ATTACK_DAMAGE
		));

		ItemStack attackKnockbackItems = makeItem(Material.SHIELD, ChatColor.GREEN + "Attack Knockback");
		modifyLore(attackKnockbackItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(attackKnockbackItems, ItemFlag.HIDE_ENCHANTS);
		combatItems.add(new AttributeItem(
			22,
			attackKnockbackItems, Attribute.ATTACK_KNOCKBACK
		));

		ItemStack attackSpeedItems = makeItem(Material.MACE, ChatColor.GREEN + "Attack Speed");
		modifyLore(attackSpeedItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(attackSpeedItems, ItemFlag.HIDE_ENCHANTS);
		combatItems.add(new AttributeItem(
			23,
			attackSpeedItems, Attribute.ATTACK_SPEED
		));

		ItemStack sweepingDamageRatioItems = makeItem(Material.IRON_SWORD, ChatColor.GREEN + "Sweeping Damage Ratio");
		modifyLore(sweepingDamageRatioItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(sweepingDamageRatioItems, ItemFlag.HIDE_ENCHANTS);
		combatItems.add(new AttributeItem(
			30,
			sweepingDamageRatioItems, Attribute.SWEEPING_DAMAGE_RATIO
		));

		ItemStack spawnReinforcementsItems = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Spawn Reinforcements");
		modifyLore(spawnReinforcementsItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(spawnReinforcementsItems, ItemFlag.HIDE_ENCHANTS);
		modifyToCustomHead(spawnReinforcementsItems, SkullData.DROWNED);
		combatItems.add(new AttributeItem(
			32,
			spawnReinforcementsItems, Attribute.SPAWN_REINFORCEMENTS
		));

		return combatItems;
	}

	public static List<AttributeItem> loadMobility() {
		ArrayList<AttributeItem> mobilityItems = new ArrayList<>();

		ItemStack flyingSpeedItems = makeItem(Material.FIREWORK_ROCKET, ChatColor.GREEN + "Flying Speed");
		modifyLore(flyingSpeedItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(flyingSpeedItems, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		mobilityItems.add(new AttributeItem(
			20,
			flyingSpeedItems, Attribute.FLYING_SPEED
		));

		ItemStack gravityItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Gravity");
		modifyLore(gravityItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(gravityItems, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifyPotionType(gravityItems, PotionType.SLOW_FALLING);
		mobilityItems.add(new AttributeItem(
			21,
			gravityItems, Attribute.GRAVITY
		));

		ItemStack jumpStrengthItems = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Jump Strength");
		modifyLore(jumpStrengthItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(jumpStrengthItems, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifyPotionType(jumpStrengthItems, PotionType.LEAPING);
		mobilityItems.add(new AttributeItem(
			22,
			jumpStrengthItems, Attribute.JUMP_STRENGTH
		));

		ItemStack movementSpeedItems = makeItem(Material.ICE, ChatColor.GREEN + "Movement Speed");
		modifyLore(movementSpeedItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(movementSpeedItems, ItemFlag.HIDE_ENCHANTS);
		mobilityItems.add(new AttributeItem(
			23,
			movementSpeedItems, Attribute.MOVEMENT_SPEED
		));

		ItemStack sneakingSpeedItems = makeItem(Material.TURTLE_HELMET, ChatColor.GREEN + "Sneaking Speed");
		modifyLore(sneakingSpeedItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(sneakingSpeedItems, ItemFlag.HIDE_ENCHANTS);
		mobilityItems.add(new AttributeItem(
			24,
			sneakingSpeedItems,
			Attribute.SNEAKING_SPEED
		));

		ItemStack stepHeightItems = makeItem(Material.EXPOSED_CUT_COPPER_STAIRS, ChatColor.GREEN + "Step Height");
		modifyLore(stepHeightItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(stepHeightItems, ItemFlag.HIDE_ENCHANTS);
		mobilityItems.add(new AttributeItem(
			30,
			stepHeightItems,
			Attribute.STEP_HEIGHT
		));

		ItemStack waterMovementEfficiencyItems = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Water Movement Efficiency");
		modifyLore(waterMovementEfficiencyItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(waterMovementEfficiencyItems, ItemFlag.HIDE_ENCHANTS);
		mobilityItems.add(new AttributeItem(
			32,
			waterMovementEfficiencyItems,
			Attribute.WATER_MOVEMENT_EFFICIENCY
		));

		return mobilityItems;
	}

	public static List<AttributeItem> loadInteractionAndReach() {
		ArrayList<AttributeItem> interactionAndReachItems = new ArrayList<>();

		ItemStack blockBreakSpeedItems = makeItem(Material.GOLDEN_PICKAXE, ChatColor.GREEN + "Block Break Speed");
		modifyLore(blockBreakSpeedItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(blockBreakSpeedItems, ItemFlag.HIDE_ENCHANTS);
		interactionAndReachItems.add(new AttributeItem(
			21,
			blockBreakSpeedItems,
			Attribute.BLOCK_BREAK_SPEED
		));

		ItemStack blockInteractionRangeItems = makeItem(Material.DISPENSER, ChatColor.GREEN + "Block Interaction Range");
		modifyLore(blockInteractionRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(blockInteractionRangeItems, ItemFlag.HIDE_ENCHANTS);
		interactionAndReachItems.add(new AttributeItem(
			22,
			blockInteractionRangeItems,
			Attribute.BLOCK_INTERACTION_RANGE
		));

		ItemStack entityInteractionRangeItems = makeItem(Material.DROPPER, ChatColor.GREEN + "Entity Interaction Range");
		modifyLore(entityInteractionRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(entityInteractionRangeItems, ItemFlag.HIDE_ENCHANTS);
		interactionAndReachItems.add(new AttributeItem(
			23,
			entityInteractionRangeItems,
			Attribute.ENTITY_INTERACTION_RANGE
		));

		ItemStack movementEfficiencyItems = makeItem(Material.GOLDEN_CARROT, ChatColor.GREEN + "Movement Efficiency");
		modifyLore(movementEfficiencyItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(movementEfficiencyItems, ItemFlag.HIDE_ENCHANTS);
		interactionAndReachItems.add(new AttributeItem(
			30,
			movementEfficiencyItems,
			Attribute.MOVEMENT_EFFICIENCY
		));

		ItemStack miningEfficiencyItems = makeItem(Material.NETHERITE_PICKAXE, ChatColor.GREEN + "Mining Efficiency");
		modifyLore(miningEfficiencyItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(miningEfficiencyItems, ItemFlag.HIDE_ENCHANTS);
		interactionAndReachItems.add(new AttributeItem(
			32,
			miningEfficiencyItems,
			Attribute.MINING_EFFICIENCY
		));

		return interactionAndReachItems;
	}

	public static List<AttributeItem> loadPerceptionAndAwareness() {
		ArrayList<AttributeItem> perceptionAndAwarenessItems = new ArrayList<>();

		ItemStack cameraDistanceItems = makeItem(Material.ENDER_EYE, ChatColor.GREEN + "Camera Distance");
		modifyLore(cameraDistanceItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(cameraDistanceItems, ItemFlag.HIDE_ENCHANTS);
		perceptionAndAwarenessItems.add(new AttributeItem(
			21,
			cameraDistanceItems,
			Attribute.CAMERA_DISTANCE));

		ItemStack followRangeItems = makeItem(Material.ZOMBIE_HEAD, ChatColor.GREEN + "Follow Range");
		modifyLore(followRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(followRangeItems, ItemFlag.HIDE_ENCHANTS);
		perceptionAndAwarenessItems.add(new AttributeItem(
			22,
			followRangeItems,
			Attribute.FOLLOW_RANGE));

		ItemStack temptRangeItems = makeItem(Material.WHEAT_SEEDS, ChatColor.GREEN + "Tempt Range");
		modifyLore(temptRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(temptRangeItems, ItemFlag.HIDE_ENCHANTS);
		perceptionAndAwarenessItems.add(new AttributeItem(
			23,
			temptRangeItems,
			Attribute.TEMPT_RANGE));

		ItemStack waypointReceiveRangeItems = makeItem(Material.CAT_SPAWN_EGG, ChatColor.GREEN + "Waypoint Receive Range");
		modifyLore(waypointReceiveRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(waypointReceiveRangeItems, ItemFlag.HIDE_ENCHANTS);
		perceptionAndAwarenessItems.add(new AttributeItem(
			30,
			waypointReceiveRangeItems,
			Attribute.WAYPOINT_RECEIVE_RANGE));

		ItemStack waypointTransmitRangeItems = makeItem(Material.CHICKEN_SPAWN_EGG, ChatColor.GREEN + "Waypoint Transmit Range");
		modifyLore(waypointTransmitRangeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(waypointTransmitRangeItems, ItemFlag.HIDE_ENCHANTS);
		perceptionAndAwarenessItems.add(new AttributeItem(
			32,
			waypointTransmitRangeItems,
			Attribute.WAYPOINT_TRANSMIT_RANGE));

		return perceptionAndAwarenessItems;
	}

	public static List<AttributeItem> loadEnvAndSurvival() {
		ArrayList<AttributeItem> envAndSurvivalItems = new ArrayList<>();

		ItemStack burningTimeItems = makeItem(Material.FLINT_AND_STEEL, ChatColor.GREEN + "Burning Time");
		modifyLore(burningTimeItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(burningTimeItems, ItemFlag.HIDE_ENCHANTS);
		envAndSurvivalItems.add(new AttributeItem(
			21,
			burningTimeItems,
			Attribute.BURNING_TIME));

		ItemStack oxygenBonusItems = makeItem(Material.OAK_DOOR, ChatColor.GREEN + "Oxygen Bonus");
		modifyLore(oxygenBonusItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(oxygenBonusItems, ItemFlag.HIDE_ENCHANTS);
		envAndSurvivalItems.add(new AttributeItem(
			22,
			oxygenBonusItems,
			Attribute.OXYGEN_BONUS));

		ItemStack luckItems = makeItem(Material.NAUTILUS_SHELL, ChatColor.GREEN + "Luck");
		modifyLore(luckItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(luckItems, ItemFlag.HIDE_ENCHANTS);
		envAndSurvivalItems.add(new AttributeItem(
			23,
			luckItems,
			Attribute.LUCK));

		ItemStack scaleItems = makeItem(Material.AMETHYST_CLUSTER, ChatColor.GREEN + "Scale");
		modifyLore(scaleItems, ChatColor.YELLOW + "Click to list modifiers");
		modifyItemFlags(scaleItems, ItemFlag.HIDE_ENCHANTS);
		envAndSurvivalItems.add(new AttributeItem(
			31,
			scaleItems,
			Attribute.SCALE));

		return envAndSurvivalItems;
	}
}