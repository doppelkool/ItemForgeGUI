package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.openItemUniquenessSettings;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.setImmutabilityItemTypeByHasTag;

/**
 * Class Description
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
		return 9*3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 18) {
			handleClose();
			return;
		}
		if (e.getSlot() == 19) {
			handleBack();
			return;
		}

		//ToDo reactivate
		//if(e.getSlot() == 10) {
		//	handleToggleImmutability();
		//	return;
		//}

		if(e.getSlot() == 13) {
			new ItemUniquenessSettingsMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		//ToDo reactivate
		//ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		//if (ConfigManager.getInstance().isItemImmutabilityEnabled()) {
		//	this.inventory.setItem(10, setImmutabilityItemTypeByHasTag(item));
		//}

		if (ConfigManager.getInstance().isUniqueIdOnEditedItemEnabled()) {
			this.inventory.setItem(13, openItemUniquenessSettings);
		}

		setFillerGlass();
	}

	private void handleToggleImmutability() {
		if (!ConfigManager.getInstance().isItemImmutabilityEnabled()) {
			return;
		}

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemStackHelper.setImmutability(
			itemInMainHand,
			!ItemStackHelper.hasImmutability(itemInMainHand));
		this.inventory.setItem(10, setImmutabilityItemTypeByHasTag(itemInMainHand));
	}
}
