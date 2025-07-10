package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyColor;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyItemFlags;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MainMenuItems {

	public static final ItemStack editName;
	public static final ItemStack editLore;
	public static final ItemStack editEnchantments;
	public static final ItemStack editDurability;
	public static final ItemStack editAmount;
	public static final ItemStack editColor;
	public static final ItemStack editSpecials;
	public static final ItemStack itemIdentity;

	static {
		editName = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Edit Name");
		modifyLore(editName, ChatColor.YELLOW + "Change the display name");

		editLore = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Edit Lore");
		modifyLore(editLore, ChatColor.YELLOW + "Change the lore text");

		editEnchantments = makeItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Edit Enchantments");
		modifyLore(editEnchantments, ChatColor.YELLOW + "Add or Remove enchantments");

		editDurability = makeItem(Material.DAMAGED_ANVIL, ChatColor.GREEN + "Edit Durability");
		modifyLore(editDurability, ChatColor.YELLOW + "Increase or Decrease the durability");

		editAmount = makeItem(Material.CHEST, ChatColor.GREEN + "Edit Amount");
		modifyLore(editAmount, ChatColor.YELLOW + "Change the amount of items");

		editColor = makeItem(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "Edit Color");
		modifyLore(editColor, ChatColor.YELLOW + "Change the color of the item");
		modifyColor(editColor, Color.BLUE);
		modifyItemFlags(editColor, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		editSpecials = makeItem(Material.END_CRYSTAL, ChatColor.GREEN + "Edit Specialities");
		modifyLore(editSpecials, ChatColor.YELLOW + "Add or remove special ItemFlags");

		itemIdentity = makeItem(Material.ARMOR_STAND, ChatColor.GREEN + "Change Item Identity");
		modifyLore(itemIdentity, ChatColor.YELLOW + "Manages the items identity settings");
	}
}
