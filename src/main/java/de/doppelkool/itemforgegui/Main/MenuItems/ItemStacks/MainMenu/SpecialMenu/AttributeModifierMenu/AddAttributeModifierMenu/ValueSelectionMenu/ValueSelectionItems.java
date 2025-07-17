package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.ValueSelectionMenu;

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
public class ValueSelectionItems {

	public static final ItemStack operationAddNumber;
	public static final ItemStack operationAddScalar;
	public static final ItemStack operationMultiplyScalarOne;

	static {
		operationAddNumber = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Number");
		modifyLore(
			operationAddNumber,
			ChatColor.DARK_AQUA + "Adds the specified number to the attributes base value",
			ChatColor.YELLOW + "Click to activate and modify"
		);

		operationAddScalar = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Scalar");
		modifyLore(
			operationAddScalar,
			ChatColor.DARK_AQUA + "Applies the modifier amount as a fraction of the base value",
			ChatColor.YELLOW + "Click to activate and modify"
		);

		operationMultiplyScalarOne = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Scalar + One");
		modifyLore(
			operationMultiplyScalarOne,
			ChatColor.DARK_AQUA + "After all base and scalar additions, scales the final value",
			ChatColor.YELLOW + "Click to activate and modify"
		);
	}
}
