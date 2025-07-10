package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class GlobalItems {

	public static final ItemStack FILLER_GLASS;
	
	public static final ItemStack paginatedMenuLeft;
	public static final ItemStack paginatedMenuRight;
	
	public static final ItemStack closeInventory;
	public static final ItemStack backInventory;
	
	public static final ItemStack minus100;
	public static final ItemStack minus50;
	public static final ItemStack minus20;
	public static final ItemStack minus10;
	public static final ItemStack minus5;
	public static final ItemStack minus1;
	public static final ItemStack plus1;
	public static final ItemStack plus5;
	public static final ItemStack plus10;
	public static final ItemStack plus20;
	public static final ItemStack plus50;
	public static final ItemStack plus100;
	public static final ItemStack toMin;
	public static final ItemStack toMax;
	public static final ItemStack customValue;
	public static final ItemStack toDefault;

	static {
		FILLER_GLASS = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");

		paginatedMenuLeft = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Left");
		modifyLore(paginatedMenuLeft, ChatColor.YELLOW + "Go back a page");
		modifyToCustomHead(paginatedMenuLeft, SkullData.QUARTZ_ARROW_LEFT);
		paginatedMenuRight = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Right");
		modifyLore(paginatedMenuRight, ChatColor.YELLOW + "Go forward a page");
		modifyToCustomHead(paginatedMenuRight, SkullData.QUARTZ_ARROW_RIGHT);

		closeInventory = makeItem(Material.PLAYER_HEAD, ChatColor.DARK_RED + "Close");
		modifyLore(closeInventory, ChatColor.YELLOW + "Close the inventory");
		modifyToCustomHead(closeInventory, SkullData.REDSTONE_BLOCK_ARROW_LEFT);
		backInventory = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Back");
		modifyLore(backInventory, ChatColor.YELLOW + "Go back to the parent inventory");
		modifyToCustomHead(backInventory, SkullData.REDSTONE_BLOCK_LEFT);

		minus100 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-100");
		modifyToCustomHead(minus100, SkullData.REDSTONE_HUNDRED);
		minus50 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-50");
		modifyToCustomHead(minus50, SkullData.REDSTONE_FIFTY);
		minus20 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-20");
		modifyToCustomHead(minus20, SkullData.REDSTONE_TWENTY);
		minus10 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-10");
		modifyToCustomHead(minus10, SkullData.REDSTONE_TEN);
		minus5 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-5");
		modifyToCustomHead(minus5, SkullData.REDSTONE_FIVE);
		minus1 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-1");
		modifyToCustomHead(minus1, SkullData.REDSTONE_ONE);
		plus1 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+1");
		modifyToCustomHead(plus1, SkullData.LIME_ONE);
		plus5 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+5");
		modifyToCustomHead(plus5, SkullData.LIME_FIVE);
		plus10 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+10");
		modifyToCustomHead(plus10, SkullData.LIME_TEN);
		plus20 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+20");
		modifyToCustomHead(plus20, SkullData.LIME_TWENTY);
		plus50 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+50");
		modifyToCustomHead(plus50, SkullData.LIME_FIFTY);
		plus100 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+100");
		modifyToCustomHead(plus100, SkullData.LIME_HUNDRED);
		toMin = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Minimum");
		modifyLore(toMin, ChatColor.YELLOW + "Sets the absolute minimum of the given value");
		modifyToCustomHead(toMin, SkullData.REDSTONE_M);
		toMax = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Maximum");
		modifyLore(toMax, ChatColor.YELLOW + "Sets the absolute maximum of the given value");
		modifyToCustomHead(toMax, SkullData.LIME_M);
		customValue = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Custom");
		modifyLore(customValue, ChatColor.YELLOW + "Set the value by directly typing it");
		toDefault = makeItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ChatColor.GREEN + "Default Max. Stack Size");
		modifyLore(toDefault, ChatColor.YELLOW + "Sets the items amount to its natural default maximum stack size");

	}
}
