package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.ItemIdentityMenu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemIdentityItems {

	public static final ItemStack openItemUniquenessSettings;

	static {
		openItemUniquenessSettings = makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Item Uniqueness Settings");
		modifyLore(openItemUniquenessSettings,
			ChatColor.YELLOW + "Copy or change the items unique identifier", "",
			ChatColor.GRAY + "Current value:",
			ChatColor.GRAY + "{currentValue}");
	}
}
