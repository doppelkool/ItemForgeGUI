package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.ItemIdentityMenu.ItemUniquenessSettingsMenu;

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
public class ItemUniquenessItems {

	public static final ItemStack copyUniqueIdentifier;
	public static final ItemStack itemUniquenessSettingsShowID;
	public static final ItemStack editUniqueIdentifier;

	static {
		copyUniqueIdentifier = makeItem(Material.PAPER, ChatColor.GREEN + "Copy");
		modifyLore(copyUniqueIdentifier, ChatColor.YELLOW + "Copy the unique identifier to the clipboard");
		itemUniquenessSettingsShowID = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Current Value" + ChatColor.GRAY + ":");
		modifyLore(itemUniquenessSettingsShowID, ChatColor.GRAY + "{currentValue}");
		editUniqueIdentifier = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Item Uniqueness Settings");
		modifyLore(editUniqueIdentifier, ChatColor.YELLOW + "Edit the unique identifier according to your requirements");
	}
}
