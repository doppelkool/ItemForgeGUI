package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.EnchantmentMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EnchantmentMenuItems {

	public static final ItemStack activatedEnchantments;
	public static final ItemStack deactivatedEnchantments;

	static {
		activatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Activated Enchantments");
		modifyLore(EnchantmentMenuItems.activatedEnchantments, ChatColor.YELLOW + "Display all active enchantments on the current item");
		modifyToCustomHead(EnchantmentMenuItems.activatedEnchantments, SkullData.QUARTZ_CHECK);

		deactivatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Deactivated Enchantments");
		modifyLore(EnchantmentMenuItems.deactivatedEnchantments, ChatColor.YELLOW + "Display all inactive enchantments on the current item");
		modifyToCustomHead(EnchantmentMenuItems.deactivatedEnchantments, SkullData.QUARTZ_X);
	}
}
