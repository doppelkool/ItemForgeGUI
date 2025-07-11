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

	static {
		addNewAttributeModifier = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Add new Attribute Modifier");
		modifyLore(addNewAttributeModifier, ChatColor.YELLOW + "Add a new Attribute Modifier to the item");
		modifyToCustomHead(addNewAttributeModifier, SkullData.QUARTZ_PLUS);
	}
}
