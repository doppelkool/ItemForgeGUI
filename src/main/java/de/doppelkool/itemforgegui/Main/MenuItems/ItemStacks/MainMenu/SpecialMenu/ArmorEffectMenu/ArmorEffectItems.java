package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.SkullData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ArmorEffectItems {

	public static final ItemStack activatedArmorEffects;
	public static final ItemStack deactivatedArmorEffects;

	static {
		activatedArmorEffects = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Activated ArmorEffects");
		modifyLore(activatedArmorEffects, ChatColor.YELLOW + "Display all active effects on the current armor");
		modifyToCustomHead(activatedArmorEffects, SkullData.QUARTZ_CHECK);
		deactivatedArmorEffects = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Deactivated ArmorEffects");
		modifyLore(deactivatedArmorEffects, ChatColor.YELLOW + "Display all inactive effects on the current armor");
		modifyToCustomHead(deactivatedArmorEffects, SkullData.QUARTZ_X);
	}
}
