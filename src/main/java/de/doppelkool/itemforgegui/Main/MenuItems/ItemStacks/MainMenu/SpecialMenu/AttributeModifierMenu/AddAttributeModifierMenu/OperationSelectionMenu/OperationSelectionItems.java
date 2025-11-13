package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.OperationSelectionMenu;

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
public class OperationSelectionItems {

	public static final ItemStack confirmAddNewAttributeModifierValue_Activated;
	public static final ItemStack confirmAddNewAttributeModifierValue_Deactivated;

	public static final ItemStack operationAddNumber;
	public static final ItemStack operationAddScalar;
	public static final ItemStack operationMultiplyScalarOne;

	static {
		confirmAddNewAttributeModifierValue_Activated = makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Confirm");
		modifyLore(confirmAddNewAttributeModifierValue_Activated, ChatColor.YELLOW + "Adds the Values as separate Attribute Modifiers to the item");

		confirmAddNewAttributeModifierValue_Deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(confirmAddNewAttributeModifierValue_Deactivated,
			ChatColor.YELLOW + "Adds the Values as separate Attribute Modifiers to the item",
			"{warning_valueMissing}");

		operationAddNumber = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Number");
		modifyLore(
			operationAddNumber,
			ChatColor.DARK_AQUA + "Adds the specified number to the attributes base value",
			ChatColor.YELLOW + "Click to add another modifier for {currentAttribute}",
			ChatColor.GRAY + "Current value: {currentValue}"
		);

		operationAddScalar = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Scalar");
		modifyLore(
			operationAddScalar,
			ChatColor.DARK_AQUA + "Applies the modifier amount as a fraction of the base value",
			ChatColor.YELLOW + "Click to add another modifier for {currentAttribute}",
			ChatColor.GRAY + "Current value: {currentValue}"
		);

		operationMultiplyScalarOne = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add Scalar + One");
		modifyLore(
			operationMultiplyScalarOne,
			ChatColor.DARK_AQUA + "After all base and scalar additions, scales the final value",
			ChatColor.YELLOW + "Click to add another modifier for {currentAttribute}",
			ChatColor.GRAY + "Current value: {currentValue}"
		);
	}
}
