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
	public static final ItemStack valueSelection_deactivated;
	public static final ItemStack slotSelection;
	public static final ItemStack slotSelection_deactivated;

	static {
		attributeSelection = makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Attribute Selection");
		modifyLore(attributeSelection, ChatColor.YELLOW + "Select the attribute to modify", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);

		valueSelection = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Value Selection");
		modifyLore(valueSelection, ChatColor.YELLOW + "Click to edit the values for {currentAttribute}");

		valueSelection_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Value Selection");
		modifyLore(valueSelection_deactivated, ChatColor.YELLOW + "Select the strength of the attribute modifier", "",
			ChatColor.RED + "Deactivated: No Attribute selected");

		slotSelection = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Slot Selection");
		modifyLore(slotSelection, ChatColor.YELLOW + "Select the slot the modifier will activate on", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);
		modifyItemFlags(slotSelection, ItemFlag.HIDE_ATTRIBUTES);

		slotSelection_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Slot Selection");
		modifyLore(slotSelection_deactivated, ChatColor.YELLOW + "Select the slot the modifier will activate on", "",
			ChatColor.RED + "Deactivated: No Attribute selected");
	}
}