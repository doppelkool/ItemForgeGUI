package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.setGlow;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsPreventionFlagsMenu extends Menu {
	public SpecialsPreventionFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Prevention Flags";
	}

	@Override
	public int getSlots() {
		return 9*4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 27) {
			handleClose();
			return;
		}
		if (e.getSlot() == 28) {
			new ItemEditMenu(playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 10) {
			return;
		}
		if (e.getSlot() == 11) {
			return;
		}


	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		ItemStack itemDropClone = ItemStacks.itemDrop.clone();
		setGlow(itemDropClone, UniqueItemIdentifierManager.isActionPrevented(itemInMainHand, ForgeAction.ITEM_DROP));
		this.inventory.setItem(10, itemDropClone);

		ItemStack itemFramePlaceClone = ItemStacks.itemFramePlace.clone();
		setGlow(itemFramePlaceClone, UniqueItemIdentifierManager.isActionPrevented(itemInMainHand, ForgeAction.ITEM_FRAME_PLACE));
		this.inventory.setItem(11, itemFramePlaceClone);

		setFillerGlass();
	}
}
