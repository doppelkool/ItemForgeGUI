package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.CustomItemFlagMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagItems {

	public static final ItemStack itemFlagHide;
	public static final ItemStack preventionFlagHide;
	public static final ItemStack armorEffectHide;
	
	static {
		itemFlagHide = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Hide Minecraft Item Flags");
		modifyLore(itemFlagHide, ChatColor.YELLOW + "Minecraft Item Flags will not be displayed in the items lore");
		modifyItemFlags(itemFlagHide, ItemFlag.HIDE_ENCHANTS);
		preventionFlagHide = makeItem(Material.STRUCTURE_VOID, ChatColor.GREEN + "Hide Prevention Flags");
		modifyLore(preventionFlagHide, ChatColor.YELLOW + "Prevention Flags will not be displayed in the items lore");
		modifyItemFlags(preventionFlagHide, ItemFlag.HIDE_ENCHANTS);
		armorEffectHide = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Hide Armor Effects");
		modifyLore(armorEffectHide, ChatColor.YELLOW + "Armor Effects will not be displayed in the items lore");
		modifyItemFlags(armorEffectHide, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
	}
}
