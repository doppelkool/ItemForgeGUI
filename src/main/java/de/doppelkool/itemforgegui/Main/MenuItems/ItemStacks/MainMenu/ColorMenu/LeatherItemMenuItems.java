package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.ColorMenu;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class LeatherItemMenuItems {
	public static final ItemStack RED_CAP;
	public static final ItemStack GREEN_CAP;
	public static final ItemStack BLUE_CAP;
	public static final ItemStack resetBackLeatherItem;
	
	static {
		RED_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.RED + "Red");
		modifyLore(RED_CAP,
			ChatColor.YELLOW + "Represents the red component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(RED_CAP, Color.fromRGB(255, 0, 0));
		modifyItemFlags(RED_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		GREEN_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Green");
		modifyLore(GREEN_CAP, ChatColor.YELLOW + "Represents the green component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(GREEN_CAP, Color.fromRGB(0, 255, 0));
		modifyItemFlags(GREEN_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		BLUE_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.RED + "Blue");
		modifyLore(BLUE_CAP, ChatColor.YELLOW + "Represents the blue component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(BLUE_CAP, Color.fromRGB(0, 0, 255));
		modifyItemFlags(BLUE_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		resetBackLeatherItem = makeItem(Material.LEATHER_BOOTS, ChatColor.RED + "Reset Color to Start");
		modifyLore(resetBackLeatherItem, ChatColor.YELLOW + "Resets the item to the variant it was when editing began");
		modifyItemFlags(resetBackLeatherItem, ItemFlag.HIDE_ATTRIBUTES);
	}
}
