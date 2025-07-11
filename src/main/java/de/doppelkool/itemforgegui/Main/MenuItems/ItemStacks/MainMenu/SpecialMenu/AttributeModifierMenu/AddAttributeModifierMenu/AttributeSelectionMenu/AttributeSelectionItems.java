package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyPotionType;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeSelectionItems {

	public static final ItemStack defenseAndDurability;
	public static final ItemStack armor;
	public static final ItemStack armorToughness;
	public static final ItemStack explosionKnockbackResistance;
	public static final ItemStack fallDamageMultiplier;
	public static final ItemStack knockbackResistance;
	public static final ItemStack maxAbsorption;
	public static final ItemStack maxHealth;
	public static final ItemStack safeFallDistance;

	public static final ItemStack combat;
	public static final ItemStack attackDamage;
	public static final ItemStack attackKnockback;
	public static final ItemStack attackSpeed;
	//ToDo public static final ItemStack attack_reach;
	public static final ItemStack sweepingDamageRatio;
	public static final ItemStack spawnReinforcements;

	public static final ItemStack mobility;
	public static final ItemStack flyingSpeed;
	public static final ItemStack gravity;
	public static final ItemStack jumpStrength;
	public static final ItemStack movementSpeed;
	public static final ItemStack sneakingSpeed;
	public static final ItemStack stepHeight;
	public static final ItemStack waterMovementEfficiency;

	public static final ItemStack interactionAndReach;
	public static final ItemStack blockBreakSpeed;
	public static final ItemStack blockInteractionRange;
	public static final ItemStack entityInteractionRange;
	public static final ItemStack movementEfficiency;
	public static final ItemStack miningEfficiency;

	public static final ItemStack perceptionAndAwareness;
	public static final ItemStack cameraDistance;
	public static final ItemStack followRange;
	public static final ItemStack temptRange;
	public static final ItemStack waypointReceiveRange;
	public static final ItemStack waypointTransmitRange;

	public static final ItemStack environmentalAndSurvival;
	public static final ItemStack burningTime;
	public static final ItemStack oxygenBonus;
	public static final ItemStack luck;
	public static final ItemStack scale;


	static {
		defenseAndDurability = makeItem(Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Defense and Durability");
		modifyLore(defenseAndDurability, ChatColor.YELLOW + "Click to select attribute category");

		armor = makeItem(Material.NETHERITE_CHESTPLATE, ChatColor.GREEN + "Armor");
		modifyLore(armor, ChatColor.YELLOW + "Click to select attribute modifier");
		armorToughness = makeItem(Material.NETHERITE_AXE, ChatColor.GREEN + "Armor Toughness");
		modifyLore(armorToughness, ChatColor.YELLOW + "Click to select attribute modifier");
		explosionKnockbackResistance = makeItem(Material.CREEPER_HEAD, ChatColor.GREEN + "Exploration Knockback Resistance");
		modifyLore(explosionKnockbackResistance, ChatColor.YELLOW + "Click to select attribute modifier");
		knockbackResistance = makeItem(Material.SHIELD, ChatColor.GREEN + "Knockback Resistance");
		modifyLore(knockbackResistance, ChatColor.YELLOW + "Click to select attribute modifier");
		fallDamageMultiplier = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Fall Damage Multiplier");
		modifyLore(fallDamageMultiplier, ChatColor.YELLOW + "Click to select attribute modifier");
		maxAbsorption = makeItem(Material.GOLDEN_APPLE, ChatColor.GREEN + "Max Absorption");
		modifyLore(maxAbsorption, ChatColor.YELLOW + "Click to select attribute modifier");
		maxHealth = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Max Health");
		modifyLore(maxHealth, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(maxHealth, PotionType.HEALING);
		safeFallDistance = makeItem(Material.SLIME_BLOCK, ChatColor.GREEN + "Safe Fall Distance");
		modifyLore(safeFallDistance, ChatColor.YELLOW + "Click to select attribute modifier");

		combat = makeItem(Material.NETHERITE_SWORD, ChatColor.GREEN + "Combat");
		modifyLore(combat, ChatColor.YELLOW + "Click to select attribute category");

		attackDamage = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Attack Damage");
		modifyLore(attackDamage, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(attackDamage, PotionType.STRENGTH);
		attackKnockback = makeItem(Material.SHIELD, ChatColor.GREEN + "Attack Knockback");
		modifyLore(attackKnockback, ChatColor.YELLOW + "Click to select attribute modifier");
		attackSpeed = makeItem(Material.MACE, ChatColor.GREEN + "Attack Speed");
		modifyLore(attackSpeed, ChatColor.YELLOW + "Click to select attribute modifier");
		sweepingDamageRatio = makeItem(Material.IRON_SWORD, ChatColor.GREEN + "Sweeping Damage Ratio");
		modifyLore(sweepingDamageRatio, ChatColor.YELLOW + "Click to select attribute modifier");
		spawnReinforcements = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Spawn Reinforcements");
		modifyLore(spawnReinforcements, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyToCustomHead(spawnReinforcements, SkullData.DROWNED);

		mobility = makeItem(Material.ELYTRA, ChatColor.GREEN + "Mobility");
		modifyLore(mobility, ChatColor.YELLOW + "Click to select attribute category");

		flyingSpeed = makeItem(Material.FIREWORK_ROCKET, ChatColor.GREEN + "Flying Speed");
		modifyLore(flyingSpeed, ChatColor.YELLOW + "Click to select attribute modifier");
		gravity = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Gravity");
		modifyLore(gravity, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(gravity, PotionType.SLOW_FALLING);
		jumpStrength = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Jump Strength");
		modifyLore(jumpStrength, ChatColor.YELLOW + "Click to select attribute modifier");
		modifyPotionType(jumpStrength, PotionType.LEAPING);
		movementSpeed = makeItem(Material.ICE, ChatColor.GREEN + "Movement Speed");
		modifyLore(movementSpeed, ChatColor.YELLOW + "Click to select attribute modifier");
		sneakingSpeed = makeItem(Material.TURTLE_HELMET, ChatColor.GREEN + "Sneaking Speed");
		modifyLore(sneakingSpeed, ChatColor.YELLOW + "Click to select attribute modifier");
		stepHeight = makeItem(Material.EXPOSED_CUT_COPPER_STAIRS, ChatColor.GREEN + "Step Height");
		modifyLore(stepHeight, ChatColor.YELLOW + "Click to select attribute modifier");
		waterMovementEfficiency = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Water Movement Efficiency");
		modifyLore(waterMovementEfficiency, ChatColor.YELLOW + "Click to select attribute modifier");

		interactionAndReach = makeItem(Material.SHEARS, ChatColor.GREEN + "Interaction and Reach");
		modifyLore(interactionAndReach, ChatColor.YELLOW + "Click to select attribute category");

		blockBreakSpeed = makeItem(Material.GOLDEN_PICKAXE, ChatColor.GREEN + "Block Break Speed");
		modifyLore(blockBreakSpeed, ChatColor.YELLOW + "Click to select attribute modifier");
		blockInteractionRange = makeItem(Material.DISPENSER, ChatColor.GREEN + "Block Interaction Range");
		modifyLore(blockInteractionRange, ChatColor.YELLOW + "Click to select attribute modifier");
		entityInteractionRange = makeItem(Material.DROPPER, ChatColor.GREEN + "Entity Interaction Range");
		modifyLore(entityInteractionRange, ChatColor.YELLOW + "Click to select attribute modifier");
		movementEfficiency = makeItem(Material.GOLDEN_CARROT, ChatColor.GREEN + "Movement Efficiency");
		modifyLore(movementEfficiency, ChatColor.YELLOW + "Click to select attribute modifier");
		miningEfficiency = makeItem(Material.NETHERITE_PICKAXE, ChatColor.GREEN + "Mining Efficiency");
		modifyLore(miningEfficiency, ChatColor.YELLOW + "Click to select attribute modifier");

		perceptionAndAwareness = makeItem(Material.SPYGLASS, ChatColor.GREEN + "Perception and Awareness");
		modifyLore(perceptionAndAwareness, ChatColor.YELLOW + "Click to select attribute category");

		cameraDistance = makeItem(Material.ENDER_EYE, ChatColor.GREEN + "Camera Distance");
		modifyLore(cameraDistance, ChatColor.YELLOW + "Click to select attribute modifier");
		followRange = makeItem(Material.ZOMBIE_HEAD, ChatColor.GREEN + "Follow Range");
		modifyLore(followRange, ChatColor.YELLOW + "Click to select attribute modifier");
		temptRange = makeItem(Material.WHEAT_SEEDS, ChatColor.GREEN + "Tempt Range");
		modifyLore(temptRange, ChatColor.YELLOW + "Click to select attribute modifier");
		waypointReceiveRange = makeItem(Material.CAT_SPAWN_EGG, ChatColor.GREEN + "Waypoint Receive Range");
		modifyLore(waypointReceiveRange, ChatColor.YELLOW + "Click to select attribute modifier");
		waypointTransmitRange = makeItem(Material.CHICKEN_SPAWN_EGG, ChatColor.GREEN + "Waypoint Transmit Range");
		modifyLore(waypointTransmitRange, ChatColor.YELLOW + "Click to select attribute modifier");

		environmentalAndSurvival = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Environmental and Survival");
		modifyLore(environmentalAndSurvival, ChatColor.YELLOW + "Click to select attribute category");

		burningTime = makeItem(Material.FLINT_AND_STEEL, ChatColor.GREEN + "Burning Time");
		modifyLore(burningTime, ChatColor.YELLOW + "Click to select attribute modifier");
		oxygenBonus = makeItem(Material.OAK_DOOR, ChatColor.GREEN + "Oxygen Bonus");
		modifyLore(oxygenBonus, ChatColor.YELLOW + "Click to select attribute modifier");
		luck = makeItem(Material.NAUTILUS_SHELL, ChatColor.GREEN + "Luck");
		modifyLore(luck, ChatColor.YELLOW + "Click to select attribute modifier");
		scale = makeItem(Material.AMETHYST_CLUSTER, ChatColor.GREEN + "Scale");
		modifyLore(scale, ChatColor.YELLOW + "Click to select attribute modifier");
	}
}
