package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.CustomItemFlag;
import de.doppelkool.itemforgegui.Main.CustomItemManager.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import oshi.util.tuples.Pair;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.setGlow;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to apply item flags to change the items appearance.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsItemFlagsMenu extends Menu {
	public SpecialsItemFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Itemflags";
	}

	@Override
	public int getSlots() {
		return 9 * 4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 27) {
			handleClose();
			return;
		}
		if (e.getSlot() == 28) {
			new SpecialsMenu(playerMenuUtility)
				.open();
			return;
		}

		Pair<ItemFlag, ItemStack> itemFlagItemStackPair = ItemFlagManager.SLOT_TO_FLAG.get(e.getSlot());
		if (itemFlagItemStackPair != null) {
			itemFlagClicked(e.getCurrentItem(), itemFlagItemStackPair.getA());

			new ItemInfoManager(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()).updateItemInfo();
			return;
		}

		Pair<CustomItemFlag, ItemStack> customItemFlagItemStackPair = CustomItemFlagManager.SLOT_TO_FLAG.get(e.getSlot());
		if (customItemFlagItemStackPair != null) {
			customFlagClicked(e.getCurrentItem(), customItemFlagItemStackPair.getA());

			new ItemInfoManager(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()).updateItemInfo();
			return;
		}
	}

	private void itemFlagClicked(ItemStack currentItem, ItemFlag itemFlag) {
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		if (hasGlow(currentItem)) {
			itemMeta.removeItemFlags(itemFlag);
		} else {
			itemMeta.addItemFlags(itemFlag);
		}
		itemInMainHand.setItemMeta(itemMeta);

		//Only change to activated dye if flag has been applied
		setGlow(currentItem, itemInMainHand.getItemMeta().hasItemFlag(itemFlag));
	}

	private void customFlagClicked(ItemStack currentItem, CustomItemFlag clickedCustomFlag) {
		boolean newStatus;
		if (clickedCustomFlag == CustomItemFlag.HIDE) {
			CustomItemFlagManager.HideFlag activeHideFlag = CustomItemFlagManager.getActiveHideFlag(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand());
			CustomItemFlagManager.HideFlag nextHideFlag = activeHideFlag != null
				? activeHideFlag.cycle()
				: CustomItemFlagManager.HideFlag.HIDE_PREVENTION_FLAGS;

			CustomItemFlagManager.updateActiveHideFlag(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(), nextHideFlag);

			ItemStackHelper.updateCustomItemFlagInMenuItemLore(currentItem, nextHideFlag);
			newStatus = nextHideFlag != null;
		} else {
			newStatus = !ItemStackHelper.hasGlow(currentItem);
		}

		ItemStackHelper.setGlow(currentItem, newStatus);

		CustomItemFlagManager.toggleCustomItemFlag(
			this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
			clickedCustomFlag,
			newStatus);
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		ItemFlagManager.SLOT_TO_FLAG.forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();

			boolean itemFlagApplied = itemMeta.hasItemFlag(pair.getA());
			ItemStackHelper.setGlow(itemStackClone, itemFlagApplied);

			this.inventory.setItem(slot, itemStackClone);
		});

		CustomItemFlagManager.SLOT_TO_FLAG.forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();

			boolean customFlagApplied = CustomItemFlagManager.isCustomFlagApplied(itemMeta, pair.getA());

			if (pair.getA() == CustomItemFlag.HIDE) {
				CustomItemFlagManager.HideFlag activeHideFlag = CustomItemFlagManager.getActiveHideFlag(itemInMainHand);
				ItemStackHelper.updateCustomItemFlagInMenuItemLore(itemStackClone, activeHideFlag);
			}

			ItemStackHelper.setGlow(itemStackClone, customFlagApplied);

			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}
}
