package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.event.inventory.InventoryClickEvent;


/**
 * Submenu as part of the main function of this plugin.
 * It divides between the item flags that change the items appearance
 * and the prevention flags to prevent specific behaviour
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsMenu extends Menu {
	public SpecialsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Specials";
	}

	@Override
	public int getSlots() {
		return 9*3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 18) {
			handleClose();
			return;
		}
		if (e.getSlot() == 19) {
			new ItemEditMenu(playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 12) {
			new SpecialsItemFlagsMenu(playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 14) {
			new SpecialsPreventionFlagsMenu(playerMenuUtility)
				.open();
			return;
		}

	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		this.inventory.setItem(12, ItemStacks.itemFlags);
		this.inventory.setItem(14, ItemStacks.preventionFlags);

		setFillerGlass();
	}
}
