package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.MinecraftItemFlagMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MinecraftItemFlagItems {

	public static final ItemStack hideEnchantments;
	public static final ItemStack hideAttributes;
	public static final ItemStack hideUnbreakable;
	public static final ItemStack hideDestroys;
	public static final ItemStack hidePlacedOn;
	public static final ItemStack hideAdditionalToolTip;
	public static final ItemStack hideDye;
	public static final ItemStack hideArmorTrim;

	static {
		hideEnchantments = makeItem(Material.ENCHANTING_TABLE, ChatColor.GREEN + "Hide Enchantments");
		modifyLore(hideEnchantments, ChatColor.YELLOW + "Hides the enchantment in the items description");
		modifyItemFlags(hideEnchantments, ItemFlag.HIDE_ENCHANTS);
		hideAttributes = makeItem(Material.PAPER, ChatColor.GREEN + "Hide Attributes");
		modifyLore(hideAttributes, ChatColor.YELLOW + "Hide the items attributes in the description", ChatColor.YELLOW + "For example the attack damage of a sword");
		modifyItemFlags(hideAttributes, ItemFlag.HIDE_ENCHANTS);
		hideUnbreakable = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Hide Unbreakable Property");
		modifyLore(hideUnbreakable, ChatColor.YELLOW + "Hides the fact that the item is unbreakable");
		modifyItemFlags(hideUnbreakable, ItemFlag.HIDE_ENCHANTS);
		hideDestroys = makeItem(Material.STONE_PICKAXE, ChatColor.GREEN + "Hide Destorys Property");
		modifyLore(hideDestroys, ChatColor.YELLOW + "Hides what the item is able to destroy");
		modifyItemFlags(hideDestroys, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		hidePlacedOn = makeItem(Material.STONE, ChatColor.GREEN + "Hide Place-On Property");
		modifyLore(hidePlacedOn, ChatColor.YELLOW + "Hides what the item is able to be placed on");
		modifyItemFlags(hidePlacedOn, ItemFlag.HIDE_ENCHANTS);
		hideAdditionalToolTip = makeItem(Material.EMERALD, ChatColor.GREEN + "Hide Additional ToolTip");
		modifyLore(hideAdditionalToolTip, ChatColor.YELLOW + "Hide the items additional tooltips in the description", ChatColor.YELLOW + "For example which smithing ingredients you need for an armor trim");
		modifyItemFlags(hideAdditionalToolTip, ItemFlag.HIDE_ENCHANTS);
		hideDye = makeItem(Material.MAGENTA_DYE, ChatColor.GREEN + "Hide Dye");
		modifyLore(hideDye, ChatColor.YELLOW + "Hides the RGB hex value showed in a colored items description");
		modifyItemFlags(hideDye, ItemFlag.HIDE_ENCHANTS);
		hideArmorTrim = makeItem(Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.GREEN + "Hide Armor Trim");
		modifyLore(hideArmorTrim, ChatColor.YELLOW + "Hides the applied armor trim on an armor piece in its description");
		modifyItemFlags(hideArmorTrim, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
	}
}
