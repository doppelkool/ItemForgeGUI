package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyCurrentValueVariableInLore;

/**
 * Submenu as part of the main function of this plugin.
 * Currently, it's a middle man to provide future-proof design for further submenus
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemIdentityMenu extends Menu {

	public ItemIdentityMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Item Entity Settings";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
			return;
		}

		if (e.getSlot() == 13) {
			new ItemUniquenessSettingsMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		if (ConfigManager.getInstance().isUniqueIdOnEditedItemEnabled()) {
			ItemStack clone = ItemStacks.openItemUniquenessSettings.clone();
			modifyCurrentValueVariableInLore(
				clone,
				UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(item));
			this.inventory.setItem(13, clone);
		}

		setFillerGlass();
	}
}
