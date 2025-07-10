package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.PreventionFlagMenu;

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
public class PreventionFlagItems {

	public static final ItemStack itemDrop;
	public static final ItemStack itemCraft;
	public static final ItemStack itemSmelt;
	public static final ItemStack itemFramePlace;
	public static final ItemStack throwItem;
	public static final ItemStack eatItem;
	public static final ItemStack placeItem;
	public static final ItemStack equipItem;
	public static final ItemStack destroyItem;
	public static final ItemStack repairItem;
	public static final ItemStack enchantItem;
	public static final ItemStack disenchantItem;
	public static final ItemStack upgradeItem;
	public static final ItemStack renameItem;

	static {
		itemDrop = makeItem(Material.FEATHER, ChatColor.GREEN + "Disable Item Drop");
		modifyLore(itemDrop, ChatColor.YELLOW + "The player will (not) be able to drop that item");
		modifyItemFlags(itemDrop, ItemFlag.HIDE_ENCHANTS);
		itemCraft = makeItem(Material.CRAFTING_TABLE, ChatColor.GREEN + "Modify Crafting");
		modifyLore(itemCraft, ChatColor.YELLOW + "The player will not be able to craft with that item in:");
		modifyItemFlags(itemCraft, ItemFlag.HIDE_ENCHANTS);
		itemSmelt = makeItem(Material.FURNACE, ChatColor.GREEN + "Disable Smelting");
		modifyLore(itemSmelt, ChatColor.YELLOW + "The player will (not) be able to smelt that item or use it as fuel");
		modifyItemFlags(itemSmelt, ItemFlag.HIDE_ENCHANTS);
		itemFramePlace = makeItem(Material.ITEM_FRAME, ChatColor.GREEN + "Disable Item Frame Placement");
		modifyLore(itemFramePlace, ChatColor.YELLOW + "The player will (not) be able to place that item in an item frame");
		modifyItemFlags(itemFramePlace, ItemFlag.HIDE_ENCHANTS);
		throwItem = makeItem(Material.EGG, ChatColor.GREEN + "Disable Item Throw");
		modifyLore(throwItem, ChatColor.YELLOW + "The player will (not) be able to throw that item (not equal Item Drop)");
		modifyItemFlags(throwItem, ItemFlag.HIDE_ENCHANTS);
		eatItem = makeItem(Material.APPLE, ChatColor.GREEN + "Disable Item Edibility");
		modifyLore(eatItem, ChatColor.YELLOW + "The player will (not) be able to eat that item");
		modifyItemFlags(eatItem, ItemFlag.HIDE_ENCHANTS);
		placeItem = makeItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Disable Item Placement");
		modifyLore(placeItem, ChatColor.YELLOW + "The player will (not) be able to place that item");
		modifyItemFlags(placeItem, ItemFlag.HIDE_ENCHANTS);
		equipItem = makeItem(Material.GOLDEN_LEGGINGS, ChatColor.GREEN + "Disable Item is equippable");
		modifyLore(equipItem, ChatColor.YELLOW + "The player will (not) be able to equip that item on armor slots on himself or on armorstands");
		modifyItemFlags(equipItem, ItemFlag.HIDE_ENCHANTS);
		destroyItem = makeItem(Material.CACTUS, ChatColor.GREEN + "Disable Item can be destroyed");
		modifyLore(destroyItem, ChatColor.YELLOW + "The player will (not) be able to destroy that item");
		modifyItemFlags(destroyItem, ItemFlag.HIDE_ENCHANTS);
		repairItem = makeItem(Material.ANVIL, ChatColor.GREEN + "Disable Item can be repaired");
		modifyLore(repairItem, ChatColor.YELLOW + "The player will (not) be able to repair that item in any way");
		modifyItemFlags(repairItem, ItemFlag.HIDE_ENCHANTS);
		enchantItem = makeItem(Material.ENCHANTING_TABLE, ChatColor.GREEN + "Disable Item can be enchanted or enchanted with");
		modifyLore(enchantItem, ChatColor.YELLOW + "The player will (not) be able to enchant that item or with that item");
		modifyItemFlags(enchantItem, ItemFlag.HIDE_ENCHANTS);
		disenchantItem = makeItem(Material.GRINDSTONE, ChatColor.GREEN + "Disable Item can be disenchanted");
		modifyLore(disenchantItem, ChatColor.YELLOW + "The player will (not) be able to disenchant that item");
		modifyItemFlags(disenchantItem, ItemFlag.HIDE_ENCHANTS);
		upgradeItem = makeItem(Material.NETHERITE_INGOT, ChatColor.GREEN + "Disable Item is upgradable");
		modifyLore(upgradeItem, ChatColor.YELLOW + "The player will (not) be able to use a smithing table to upgrade the item");
		modifyItemFlags(upgradeItem, ItemFlag.HIDE_ENCHANTS);
		renameItem = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Disable Item is renamable");
		modifyLore(renameItem, ChatColor.YELLOW + "The player will (not) be able to rename the item");
		modifyItemFlags(renameItem, ItemFlag.HIDE_ENCHANTS);
	}
}
