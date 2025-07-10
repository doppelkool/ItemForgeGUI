package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlag;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import oshi.util.tuples.Pair;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Specials_CustomItemFlagsMenu extends Menu {
	public Specials_CustomItemFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Custom Item Flags";
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
		if (super.handleBack(e.getSlot(), SpecialsMenu::new)) {
			return;
		}

		Pair<CustomItemFlag, ItemStack> customItemFlagItemStackPair = CustomItemFlagManager.getInstance().getSlotToFlag().get(e.getSlot());
		if (customItemFlagItemStackPair != null) {
			customFlagClicked(e.getCurrentItem(), customItemFlagItemStackPair.getA());

			new ItemInfoManager(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()).updateItemInfo();
			return;
		}
	}

	private void customFlagClicked(ItemStack currentItem, CustomItemFlag clickedCustomFlag) {
		boolean newStatus = !ItemStackModifyHelper.hasGlow(currentItem);

		ItemStackModifyHelper.setActivated(currentItem, newStatus);

		CustomItemFlagManager.getInstance().toggleItemFlag(
			this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
			clickedCustomFlag,
			newStatus);
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		PersistentDataContainer container = itemInMainHand.getItemMeta().getPersistentDataContainer();

		CustomItemFlagManager.getInstance().getSlotToFlag().forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();
			CustomItemFlag flag = pair.getA();

			boolean isActive = CustomItemFlagManager.getInstance().isFlagApplied(container, flag);
			ItemStackModifyHelper.setActivated(itemStackClone, isActive);

			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}

}
