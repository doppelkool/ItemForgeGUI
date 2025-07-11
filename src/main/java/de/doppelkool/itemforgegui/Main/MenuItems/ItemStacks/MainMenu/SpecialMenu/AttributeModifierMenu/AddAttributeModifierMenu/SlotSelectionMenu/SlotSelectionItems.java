package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.SlotSelectionMenu;

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
public class SlotSelectionItems {

	public static final ItemStack headSlot;
	public static final ItemStack chestSlot;
	public static final ItemStack legSlot;
	public static final ItemStack bootSlot;
	public static final ItemStack mainHandSlot;
	public static final ItemStack offHandSlot;

	static {
		headSlot = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Head Slot");
		modifyLore(headSlot, ChatColor.YELLOW + "Click to de/activate");

		chestSlot = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Chest Slot");
		modifyLore(chestSlot, ChatColor.YELLOW + "Click to de/activate");

		legSlot = makeItem(Material.GOLDEN_LEGGINGS, ChatColor.GREEN + "Leg Slot");
		modifyLore(legSlot, ChatColor.YELLOW + "Click to de/activate");

		bootSlot = makeItem(Material.NETHERITE_BOOTS, ChatColor.GREEN + "Boot Slot");
		modifyLore(bootSlot, ChatColor.YELLOW + "Click to de/activate");

		mainHandSlot = makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Main Hand Slot");
		modifyLore(mainHandSlot, ChatColor.YELLOW + "Click to de/activate");

		offHandSlot = makeItem(Material.SHIELD, ChatColor.GREEN + "Off Hand Slot");
		modifyLore(offHandSlot, ChatColor.YELLOW + "Click to de/activate");
	}
}
