package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.SlotSelectionMenu;

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
public class SlotSelectionItems {

	public static final ItemStack headSlot;
	public static final ItemStack chestSlot;
	public static final ItemStack legSlot;
	public static final ItemStack bootSlot;
	public static final ItemStack mainHandSlot;
	public static final ItemStack offHandSlot;

	public static final ItemStack confirmSlots_deactivated;
	public static final ItemStack confirmSlots;

	static {
		headSlot = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Head Slot");
		modifyLore(headSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(headSlot, EquipmentSlot.HEAD);
		modifyItemFlags(headSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		chestSlot = makeItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.GREEN + "Chest Slot");
		modifyLore(chestSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(chestSlot, EquipmentSlot.CHEST);
		modifyItemFlags(chestSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		legSlot = makeItem(Material.GOLDEN_LEGGINGS, ChatColor.GREEN + "Leg Slot");
		modifyLore(legSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(legSlot, EquipmentSlot.LEGS);
		modifyItemFlags(legSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		bootSlot = makeItem(Material.NETHERITE_BOOTS, ChatColor.GREEN + "Boot Slot");
		modifyLore(bootSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(bootSlot, EquipmentSlot.FEET);
		modifyItemFlags(bootSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		mainHandSlot = makeItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Main Hand Slot");
		modifyLore(mainHandSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(mainHandSlot, EquipmentSlot.HAND);
		modifyItemFlags(mainHandSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		offHandSlot = makeItem(Material.SHIELD, ChatColor.GREEN + "Off Hand Slot");
		modifyLore(offHandSlot, ChatColor.YELLOW + "Click to de/activate");
		modifyStoredSlot(offHandSlot, EquipmentSlot.OFF_HAND);
		modifyItemFlags(offHandSlot, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		confirmSlots_deactivated = makeItem(Material.RED_STAINED_GLASS, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots_deactivated,
			ChatColor.YELLOW + "Confirm Slot Selection",
			ChatColor.RED + "Deactivated: No Slot selected");

		confirmSlots = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Confirm");
		modifyLore(confirmSlots, ChatColor.YELLOW + "Confirm Slot Selection");
		modifyToCustomHead(confirmSlots, SkullData.QUARTZ_CHECK);
	}
}
