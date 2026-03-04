package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyToCustomHead;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class GlobalAttributeModifierItems {
	public static final ItemStack minus20;
	public static final ItemStack minus10;
	public static final ItemStack minus1;
	public static final ItemStack minusPoint1;
	public static final ItemStack plusPoint1;
	public static final ItemStack plus1;
	public static final ItemStack plus10;
	public static final ItemStack plus20;

	static {
		minus20 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(minus20, SkullData.REDSTONE_TWENTY);

		minus10 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(minus10, SkullData.REDSTONE_TEN);

		minus1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(minus1, SkullData.REDSTONE_ONE);

		minusPoint1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(minusPoint1, SkullData.REDSTONE_POINT_ONE);

		plusPoint1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(plusPoint1, SkullData.LIME_POINT_ONE);

		plus1 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(plus1, SkullData.LIME_ONE);

		plus10 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
		modifyToCustomHead(plus10, SkullData.LIME_TEN);

		plus20 = makeItem(Material.PLAYER_HEAD,
			ChatColor.YELLOW  + "{currentOperationExample}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": {currentValue}");
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
}
