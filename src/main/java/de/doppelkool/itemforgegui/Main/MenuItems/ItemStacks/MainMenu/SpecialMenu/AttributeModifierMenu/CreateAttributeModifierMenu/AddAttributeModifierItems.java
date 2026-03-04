package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyToCustomHead;

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

	public static final ItemStack confirmSlots_deactivated;
	public static final ItemStack confirmSlots;

	static {
		attributeSelection = makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Attribute Selection");
		modifyLore(attributeSelection, ChatColor.YELLOW + "Select the attribute to modify", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);

		valueSelection = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Value Selection");
		modifyLore(valueSelection, ChatColor.YELLOW + "Click to edit the values for {currentAttribute}");

		valueSelection_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Value Selection");
		modifyLore(valueSelection_deactivated, ChatColor.YELLOW + "Select the strength of the attribute modifier", "",
			ChatColor.RED + "Deactivated: No Slot selected");

		slotSelection = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Slot Selection");
		modifyLore(slotSelection, ChatColor.YELLOW + "Select the slot the modifier will activate on", "",
			ChatColor.GRAY + "Currently selected: {currentValue}"
		);
		modifyItemFlags(slotSelection, ItemFlag.HIDE_ATTRIBUTES);

		slotSelection_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Slot Selection");
		modifyLore(slotSelection_deactivated, ChatColor.YELLOW + "Select the slot the modifier will activate on", "",
			ChatColor.RED + "Deactivated: No Attribute selected");

		confirmSlots_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots_deactivated,
			ChatColor.YELLOW + "Confirm Attribute Modifier Creation",
			//TODO change respectively to value or slot select, eg. the ones that are not selected
			ChatColor.RED + "Deactivated: No {missingProperty} selected");

		confirmSlots = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots, ChatColor.YELLOW + "Confirm Attribute Modifier Creation");
		modifyToCustomHead(confirmSlots, SkullData.QUARTZ_CHECK);
	}
}