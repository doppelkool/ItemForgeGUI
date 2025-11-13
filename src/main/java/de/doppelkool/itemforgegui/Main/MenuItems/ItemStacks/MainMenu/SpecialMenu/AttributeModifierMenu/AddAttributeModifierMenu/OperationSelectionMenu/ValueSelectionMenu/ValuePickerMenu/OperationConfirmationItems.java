package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.OperationSelectionMenu.ValueSelectionMenu.ValuePickerMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class OperationConfirmationItems {

	public static final ItemStack infoCurrentValuesBook;
	public static final ItemStack confirmSelectedValue_Activated;
	public static final ItemStack confirmSelectedValue_Deactivated;

	static {
		infoCurrentValuesBook = makeItem(Material.BOOK, ChatColor.GREEN + "Information:");
		modifyLore(
			infoCurrentValuesBook
		);

		confirmSelectedValue_Activated = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Confirm");
		modifyLore(
			confirmSelectedValue_Activated,
			ChatColor.YELLOW + "Confirm value Selection"
		);

		confirmSelectedValue_Deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(
			confirmSelectedValue_Deactivated,
			ChatColor.YELLOW + "Confirm value Selection",
			ChatColor.RED + "Deactivated: 0.0 is not allowed"
		);
	}
}
