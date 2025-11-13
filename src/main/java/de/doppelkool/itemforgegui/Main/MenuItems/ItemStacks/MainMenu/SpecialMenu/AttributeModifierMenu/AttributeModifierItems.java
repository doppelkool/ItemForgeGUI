package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

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
public class AttributeModifierItems {

	public static final ItemStack addNewAttributeModifier;
	public static final ItemStack confirmAddNewAttributeModifier_Activated;
	public static final ItemStack confirmAddNewAttributeModifier_Deactivated;

	static {
		addNewAttributeModifier = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add new Attribute Modifier");
		modifyLore(addNewAttributeModifier, ChatColor.YELLOW + "Add a new Attribute Modifier to the item");
		modifyToCustomHead(addNewAttributeModifier, SkullData.QUARTZ_PLUS);

		confirmAddNewAttributeModifier_Activated = makeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Confirm");
		modifyLore(confirmAddNewAttributeModifier_Activated, ChatColor.YELLOW + "Adds the new Attribute Modifier to the item");

		confirmAddNewAttributeModifier_Deactivated = makeItem(Material.BARRIER, ChatColor.GREEN + "Confirm");
		modifyLore(confirmAddNewAttributeModifier_Deactivated,
			ChatColor.YELLOW + "Add a new Attribute Modifier to the item",
			"{warning_valueMissing}");
	}
}
