package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyStoredSlot;

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
}
