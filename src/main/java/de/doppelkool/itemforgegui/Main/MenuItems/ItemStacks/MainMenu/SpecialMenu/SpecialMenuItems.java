package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu;

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
public class SpecialMenuItems {
	public static final ItemStack itemFlags;
	public static final ItemStack customItemFlags;
	public static final ItemStack preventionFlags;
	public static final ItemStack armorEffects;
	public static final ItemStack attributeModifiers;
	
	static {
		itemFlags = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Edit Minecraft Item Flags");
		customItemFlags = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Edit Custom Item Flags");
		modifyItemFlags(customItemFlags, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifySpecificBannerPattern(customItemFlags);
		preventionFlags = makeItem(Material.STRUCTURE_VOID, ChatColor.GREEN + "Edit Prevention Flags");
		modifyLore(preventionFlags, ChatColor.YELLOW + "Edit what the player can/cannot do with that item");
		armorEffects = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Edit Armor Effects");
		modifyLore(armorEffects, ChatColor.YELLOW + "Edit effects the armor applies to the player when worn");
		modifyItemFlags(armorEffects, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		attributeModifiers = makeItem(Material.DRAGON_BREATH, ChatColor.GREEN + "Edit Attribute Modifiers");
		modifyLore(attributeModifiers, ChatColor.YELLOW + "Edit attributes the item applies to the player when interacted");
	}
}
