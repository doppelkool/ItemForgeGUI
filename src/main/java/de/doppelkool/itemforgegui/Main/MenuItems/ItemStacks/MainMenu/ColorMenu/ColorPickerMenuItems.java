package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.ColorMenu;

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
public class ColorPickerMenuItems {
	public static final ItemStack DEFAULT_dye;
	public static final ItemStack WHITE_dye;
	public static final ItemStack ORANGE_dye;
	public static final ItemStack MAGENTA_dye;
	public static final ItemStack LIGHT_BLUE_dye;
	public static final ItemStack YELLOW_dye;
	public static final ItemStack LIME_dye;
	public static final ItemStack PINK_dye;
	public static final ItemStack GRAY_dye;
	public static final ItemStack LIGHT_GRAY_dye;
	public static final ItemStack CYAN_dye;
	public static final ItemStack PURPLE_dye;
	public static final ItemStack BLUE_dye;
	public static final ItemStack BROWN_dye;
	public static final ItemStack GREEN_dye;
	public static final ItemStack RED_dye;
	public static final ItemStack BLACK_dye;
	
	static {
		DEFAULT_dye = makeItem(Material.PAPER, ChatColor.WHITE + "No Color");
		modifyLore(DEFAULT_dye, ChatColor.YELLOW + "Sets the items type to its natural, uncolored variant");
		WHITE_dye = makeItem(Material.WHITE_DYE, ChatColor.WHITE + "White");
		ORANGE_dye = makeItem(Material.ORANGE_DYE, ChatColor.GOLD + "Orange");
		MAGENTA_dye = makeItem(Material.MAGENTA_DYE, ChatColor.WHITE + "Magenta");
		LIGHT_BLUE_dye = makeItem(Material.LIGHT_BLUE_DYE, ChatColor.AQUA + "Light Blue");
		YELLOW_dye = makeItem(Material.YELLOW_DYE, ChatColor.YELLOW + "Yellow");
		LIME_dye = makeItem(Material.LIME_DYE, ChatColor.GREEN + "Lime");
		PINK_dye = makeItem(Material.PINK_DYE, ChatColor.WHITE + "Pink");
		GRAY_dye = makeItem(Material.GRAY_DYE, ChatColor.DARK_GRAY + "Gray");
		LIGHT_GRAY_dye = makeItem(Material.LIGHT_GRAY_DYE, ChatColor.GRAY + "Light Gray");
		CYAN_dye = makeItem(Material.CYAN_DYE, ChatColor.DARK_AQUA + "Cyan");
		PURPLE_dye = makeItem(Material.PURPLE_DYE, ChatColor.WHITE + "Purple");
		BLUE_dye = makeItem(Material.BLUE_DYE, ChatColor.BLUE + "Blue");
		BROWN_dye = makeItem(Material.BROWN_DYE, ChatColor.GOLD + "Brown");
		GREEN_dye = makeItem(Material.GREEN_DYE, ChatColor.DARK_GREEN + "Green");
		RED_dye = makeItem(Material.RED_DYE, ChatColor.RED + "Red");
		BLACK_dye = makeItem(Material.BLACK_DYE, ChatColor.BLACK + "Black");
	}
}
