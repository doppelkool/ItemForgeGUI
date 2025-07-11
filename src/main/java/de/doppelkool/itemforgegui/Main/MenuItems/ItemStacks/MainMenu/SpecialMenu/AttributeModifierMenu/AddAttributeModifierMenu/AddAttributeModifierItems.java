package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu;

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
public class AddAttributeModifierItems {

	public static final ItemStack attributeSelection;
	public static final ItemStack valueSelection;
	public static final ItemStack slotSelection;

	static {
		attributeSelection = makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Attribute Selection");
		modifyLore(attributeSelection, ChatColor.YELLOW + "Select the attribute to modify", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);
		valueSelection = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Value Selection");
		modifyLore(valueSelection, ChatColor.YELLOW + "Select the strength of the attribute modifier", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);
		slotSelection = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Attribute Selection");
		modifyLore(slotSelection, ChatColor.YELLOW + "Select the attribute to modify", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);
		modifyItemFlags(slotSelection, ItemFlag.HIDE_ATTRIBUTES);
	}
}
