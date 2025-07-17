package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeSelectionItems {

	public static final ItemStack defenseAndDurability;
	public static final ItemStack combat;
	public static final ItemStack mobility;
	public static final ItemStack interactionAndReach;
	public static final ItemStack perceptionAndAwareness;
	public static final ItemStack environmentalAndSurvival;

	static {
		defenseAndDurability = makeItem(Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Defense and Durability");
		modifyLore(defenseAndDurability, ChatColor.YELLOW + "Click to select attribute category");
		combat = makeItem(Material.NETHERITE_SWORD, ChatColor.GREEN + "Combat");
		modifyLore(combat, ChatColor.YELLOW + "Click to select attribute category");
		mobility = makeItem(Material.ELYTRA, ChatColor.GREEN + "Mobility");
		modifyLore(mobility, ChatColor.YELLOW + "Click to select attribute category");
		interactionAndReach = makeItem(Material.SHEARS, ChatColor.GREEN + "Interaction and Reach");
		modifyLore(interactionAndReach, ChatColor.YELLOW + "Click to select attribute category");
		perceptionAndAwareness = makeItem(Material.SPYGLASS, ChatColor.GREEN + "Perception and Awareness");
		modifyLore(perceptionAndAwareness, ChatColor.YELLOW + "Click to select attribute category");
		environmentalAndSurvival = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Environmental and Survival");
		modifyLore(environmentalAndSurvival, ChatColor.YELLOW + "Click to select attribute category");
	}
}
