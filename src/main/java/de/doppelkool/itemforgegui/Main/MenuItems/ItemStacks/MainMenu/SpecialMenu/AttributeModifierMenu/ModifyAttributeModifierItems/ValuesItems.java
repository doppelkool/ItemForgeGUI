package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.ModifyAttributeModifierItems;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.makeItem;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyItemFlags;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.modifyLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ValuesItems {
	public static final ItemStack infoBook;

	@Getter private static final String addNumberExplanationLoreString = "Adds a fixed value to the base attribute.";
	@Getter private static final String addScalarExplanationLoreString = "Adds a percentage of the base value.";
	@Getter private static final String multiplyScalar1ExplanationLoreString = "Multiplies the final value by (1 + amount).";

	static {
		infoBook = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Information");
		modifyItemFlags(infoBook, ItemFlag.HIDE_ENCHANTS);
		modifyLore(infoBook,
			ChatColor.YELLOW + "Operation" + ChatColor.GRAY + ": " + ChatColor.YELLOW  + "{currentOperation}",
			ChatColor.YELLOW + "Current value" + ChatColor.GRAY + ": " + ChatColor.YELLOW  + "{currentValue}",
			"",
			ChatColor.DARK_GRAY + "{currentOperationExplanation}",
			"",
			ChatColor.AQUA + "" + ChatColor.BOLD + "    ——— Options ———    ",
			ChatColor.GRAY + "Left Click" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Set the value through manual enter",
			ChatColor.GRAY + "Right Click" + ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Set the value to 0"
		);
	}
}
