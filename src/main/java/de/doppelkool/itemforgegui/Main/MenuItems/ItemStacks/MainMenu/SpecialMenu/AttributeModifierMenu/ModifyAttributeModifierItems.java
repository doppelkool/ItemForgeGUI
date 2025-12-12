package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyStoredSlot;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ModifyAttributeModifierItems {

	public static final ItemStack headSlot;
	public static final ItemStack chestSlot;
	public static final ItemStack legSlot;
	public static final ItemStack bootSlot;
	public static final ItemStack mainHandSlot;
	public static final ItemStack offHandSlot;

	static {
		headSlot = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Head Slot");
		modifyLore(headSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Head");
		modifyStoredSlot(headSlot, EquipmentSlot.HEAD);
		modifyItemFlags(headSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		chestSlot = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Chest Slot");
		modifyLore(chestSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Chest");
		modifyStoredSlot(chestSlot, EquipmentSlot.CHEST);
		modifyItemFlags(chestSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		legSlot = makeItem(Material.GOLDEN_LEGGINGS, ChatColor.GREEN + "Leg Slot");
		modifyLore(legSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Legs");
		modifyStoredSlot(legSlot, EquipmentSlot.LEGS);
		modifyItemFlags(legSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		bootSlot = makeItem(Material.NETHERITE_BOOTS, ChatColor.GREEN + "Boot Slot");
		modifyLore(bootSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Feet");
		modifyStoredSlot(bootSlot, EquipmentSlot.FEET);
		modifyItemFlags(bootSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		mainHandSlot = makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Main Hand Slot");
		modifyLore(mainHandSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Main Hand");
		modifyStoredSlot(mainHandSlot, EquipmentSlot.HAND);
		modifyItemFlags(mainHandSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		offHandSlot = makeItem(Material.SHIELD, ChatColor.GREEN + "Off Hand Slot");
		modifyLore(offHandSlot, ChatColor.YELLOW + "Click to modify attribute modifier for", "-> Attribute: {currentAttribute}", "-> Slot: Off Hand");
		modifyStoredSlot(offHandSlot, EquipmentSlot.OFF_HAND);
		modifyItemFlags(offHandSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
	}

	public static final ItemStack minus20;
	public static final ItemStack minus10;
	public static final ItemStack minus1;
	public static final ItemStack minusPoint1;
	public static final ItemStack customValue;
	public static final ItemStack plusPoint1;
	public static final ItemStack plus1;
	public static final ItemStack plus10;
	public static final ItemStack plus20;

	static {
		minus20 = makeItem(Material.PLAYER_HEAD,
			ChatColor.RED + "-20",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.RED + "-20");
		modifyToCustomHead(minus20, SkullData.REDSTONE_TWENTY);

		minus10 = makeItem(Material.PLAYER_HEAD,
			ChatColor.RED + "-10",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.RED + "-10");
		modifyToCustomHead(minus10, SkullData.REDSTONE_TEN);

		minus1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.RED + "-1",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.RED + "-1");
		modifyToCustomHead(minus1, SkullData.REDSTONE_ONE);

		minusPoint1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.RED + "-0.1",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.RED + "-0.1");
		modifyToCustomHead(minusPoint1, SkullData.REDSTONE_POINT_ONE);

		customValue = makeItem(Material.WRITABLE_BOOK,
			ChatColor.GREEN + "Custom value",
			ChatColor.YELLOW + "Set the value by directly typing it");

		plusPoint1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.RED + "+0.1",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.GREEN + "+0.1");
		modifyToCustomHead(plusPoint1, SkullData.LIME_POINT_ONE);

		plus1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.GREEN + "+1",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.GREEN + "+1");
		modifyToCustomHead(plus1, SkullData.LIME_ONE);

		plus10 = makeItem(Material.PLAYER_HEAD,
			ChatColor.GREEN + "+10",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.GREEN + "+10");
		modifyToCustomHead(plus10, SkullData.LIME_TEN);

		plus20 = makeItem(Material.PLAYER_HEAD,
			ChatColor.GREEN + "+20",
			ChatColor.YELLOW + "Result" + ChatColor.GRAY + ": " + ChatColor.WHITE + "{currentValue} " + ChatColor.GREEN + "+20");
		modifyToCustomHead(plus20, SkullData.LIME_TWENTY);
	}

	public static final ItemStack confirmValues_deactivated;
	public static final ItemStack confirmValues;

	static {
		confirmValues_deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(confirmValues_deactivated,
			ChatColor.YELLOW + "Confirm Value Selection",
			ChatColor.RED + "Deactivated: One of the values has to be not 0");

		confirmValues = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Confirm");
		modifyLore(confirmValues, ChatColor.YELLOW + "Confirm Slot Selection");
		modifyToCustomHead(confirmValues, SkullData.QUARTZ_CHECK);
	}

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
