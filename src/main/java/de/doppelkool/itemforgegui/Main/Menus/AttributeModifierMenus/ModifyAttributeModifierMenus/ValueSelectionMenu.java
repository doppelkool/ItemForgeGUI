package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus.ModifyAttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AddAttributeModifierMenu.AttributeSelectionMenu.AttributeSelectionItems;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ValueSelectionMenu extends ConfirmableMenu {

	private Pair<AttributeModifier.Operation, Double> selectedValue;

	private static final Set<Integer> blackPaneSlots = Set.of(
		0, 1, 2, 6, 7, 8,
		9, 10, 11, 12, 13, 14, 15, 16, 17,
		36, 37, 38, 39, 40, 41, 42, 43, 44,
		47, 48, 49, 50, 51, 52
	);

	public ValueSelectionMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 26);
	}

	@Override
	public String getMenuName() {
		return "Value Selection";
	}

	@Override
	public int getSlots() {
		return 9 * 6;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {

	}

	@Override
	protected boolean isConfirmable() {
		return selectedValue.getB() != 0;
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return null;
	}

	@Override
	public void setMenuItems() {
		loadMenuSkeleton();


	}

	private void loadMenuSkeleton() {
		for (int slot : blackPaneSlots) {
			inventory.setItem(slot, GlobalItems.FILLER_GLASS);
		}

		inventory.setItem(45, GlobalItems.closeInventory);
		inventory.setItem(46, GlobalItems.backInventory);
		inventory.setItem(53, AttributeSelectionItems.confirmSlots);
	}
}
