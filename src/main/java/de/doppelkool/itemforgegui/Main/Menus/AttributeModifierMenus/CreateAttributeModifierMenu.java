package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CreateAttributeModifierMenu extends Menu {

	public CreateAttributeModifierMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Create Attribute Modifier";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {

	}

	@Override
	public void setMenuItems() {
		addMenuBorder();



		setFillerGlass();
	}
}
