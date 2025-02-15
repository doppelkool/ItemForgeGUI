package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyCurrentValueVariableInLore;
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

		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		//ToDo reactivate
		//if (ConfigManager.getInstance().isItemImmutabilityEnabled()) {
		//	this.inventory.setItem(10, setImmutabilityItemTypeByHasTag(item));
		//}

		if (ConfigManager.getInstance().isUniqueIdOnEditedItemEnabled()) {
			ItemStack clone = ItemStacks.openItemUniquenessSettings.clone();
			modifyCurrentValueVariableInLore(
				clone,
				UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(item));
			this.inventory.setItem(13, clone);
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
