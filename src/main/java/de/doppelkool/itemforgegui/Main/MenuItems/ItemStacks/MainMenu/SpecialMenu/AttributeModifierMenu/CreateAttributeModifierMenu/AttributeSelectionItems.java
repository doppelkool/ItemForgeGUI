package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyToCustomHead;

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

	public static final ItemStack confirmSlots_deactivated;
	public static final ItemStack confirmSlots;

	static {
		defenseAndDurability = makeItem(Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Defense and Durability");
		modifyLore(defenseAndDurability, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(defenseAndDurability, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

		combat = makeItem(Material.NETHERITE_SWORD, ChatColor.GREEN + "Combat");
		modifyLore(combat, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(combat, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

		mobility = makeItem(Material.ELYTRA, ChatColor.GREEN + "Mobility");
		modifyLore(mobility, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(combat, ItemFlag.HIDE_ENCHANTS);

		interactionAndReach = makeItem(Material.SHEARS, ChatColor.GREEN + "Interaction and Reach");
		modifyLore(interactionAndReach, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(combat, ItemFlag.HIDE_ENCHANTS);

		perceptionAndAwareness = makeItem(Material.SPYGLASS, ChatColor.GREEN + "Perception and Awareness");
		modifyLore(perceptionAndAwareness, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(combat, ItemFlag.HIDE_ENCHANTS);

		environmentalAndSurvival = makeItem(Material.WATER_BUCKET, ChatColor.GREEN + "Environmental and Survival");
		modifyLore(environmentalAndSurvival, ChatColor.YELLOW + "Click to select attribute category");
		modifyItemFlags(combat, ItemFlag.HIDE_ENCHANTS);

		confirmSlots_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots_deactivated,
			ChatColor.YELLOW + "Confirm Attribute Selection",
			//TODO change respectively to value or slot select, eg. the ones that are not selected
			ChatColor.RED + "Deactivated: No Attribute selected");

		confirmSlots = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots, ChatColor.YELLOW + "Confirm Attribute Selection");
		modifyToCustomHead(confirmSlots, SkullData.QUARTZ_CHECK);
	}
}
