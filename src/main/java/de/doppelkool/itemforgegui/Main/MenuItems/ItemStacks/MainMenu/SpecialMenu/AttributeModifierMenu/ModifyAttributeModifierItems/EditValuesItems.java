package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EditValuesItems {
	public static final ItemStack infoBook;

	static {
		infoBook = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Information");
		modifyItemFlags(infoBook, ItemFlag.HIDE_ENCHANTS);
		modifyLore(infoBook,
			ChatColor.YELLOW + "Operation" + ChatColor.GRAY + ": " + ChatColor.YELLOW  + "{currentOperation}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": " + ChatColor.YELLOW  + "{currentValue}",
			"",
			ChatColor.GRAY + "*left click* to set the value through manual enter",
			ChatColor.GRAY + "*right click* to set the value to zero"
		);
	}
}
