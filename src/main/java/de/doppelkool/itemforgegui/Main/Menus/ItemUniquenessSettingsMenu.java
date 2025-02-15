package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SignNumberEditor;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyCurrentValueVariableInLore;

/**
 * Class Description
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
		return 9*3;
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

		if(e.getSlot() == 12) {
			UniqueItemIdentifierManager.sendCopyUniqueIdentifier(this.playerMenuUtility.getOwner());
			this.playerMenuUtility.getOwner().closeInventory();
			return;
		}

		if(e.getSlot() == 14) {
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

	private void editUniqueIdentifierProcess() {
		String message = Main.prefix + "\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " Please edit the content to the items future identifier and click \"Done\".\n" +
			ChatColor.GRAY + "[" + ChatColor.RED + "Warning" + ChatColor.GRAY + "] The maximum characters per Line is 15.\n" +
			ChatColor.GRAY + "[" + ChatColor.RED + "Warning" + ChatColor.GRAY + "] Whitespaces will be replaced with underscores.";

		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.setSignNumberEditor(new SignNumberEditor(playerMenuUtility.getOwner())
			.editItemID(
				UniqueItemIdentifierManager.getUniqueItemIdentifierOrEmptyString(
					this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta()))
			.openSign());
		playerMenuUtility.getOwner().sendMessage(message);
	}
}
