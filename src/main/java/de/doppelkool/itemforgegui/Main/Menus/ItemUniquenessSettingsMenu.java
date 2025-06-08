package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyCurrentValueVariableInLore;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to change settings that regard the uniqueness of the item.
 * Including the plugins UID for a forged item, copying it and editing it.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemUniquenessSettingsMenu extends Menu {
	public ItemUniquenessSettingsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Item Uniqueness Settings";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 18) {
			handleClose();
			return;
		}
		if (e.getSlot() == 19) {
			new ItemIdentityMenu(this.playerMenuUtility)
				.open();
			return;
		}

		if (e.getSlot() == 12) {
			UniqueItemIdentifierManager.sendCopyUniqueIdentifier(this.playerMenuUtility.getOwner());
			this.playerMenuUtility.getOwner().closeInventory();
			return;
		}

		if (e.getSlot() == 14) {
			editUniqueIdentifierProcess();
			return;
		}

	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		ItemStack clone = ItemStacks.itemUniquenessSettingsShowID.clone();
		modifyCurrentValueVariableInLore(
			clone,
			UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(item));

		this.inventory.setItem(4, clone);
		this.inventory.setItem(12, ItemStacks.copyUniqueIdentifier);
		this.inventory.setItem(14, ItemStacks.editUniqueIdentifier);

		setFillerGlass();
	}

	private void editUniqueIdentifierProcess() {;
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editItemID(
				UniqueItemIdentifierManager.getUniqueItemIdentifierOrEmptyString(
					this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta()))
			.openSign());
		MessageManager.message(playerMenuUtility.getOwner(), Messages.SIGN_EDITOR_EDIT_UNIQUEID_INFORMATION);
	}
}
