package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlag;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Pair;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CustomItemFlagsMenu extends Menu {
	public CustomItemFlagsMenu(PlayerMenuUtility playerMenuUtility) {
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
		if (super.handleBack(e.getSlot(), null, SpecialsMenu::new)) {
			return;
		}

		Pair<CustomItemFlag, ItemStack> customItemFlagItemStackPair = CustomItemFlagManager.getInstance().getSlotToFlag().get(e.getSlot());
		if (customItemFlagItemStackPair == null) {
			return;
		}

		customFlagClicked(e.getCurrentItem(), customItemFlagItemStackPair.getA());

		ItemStack item = this.playerMenuUtility.getItemInHand().get();
		new ItemInfoManager(item).updateItemInfo();
		playerMenuUtility.getItemInHand().set(item);
		return;
	}

	private void customFlagClicked(ItemStack currentItem, CustomItemFlag clickedCustomFlag) {
		boolean newStatus = !ItemStackModifyHelper.hasGlow(currentItem);

		ItemStackModifyHelper.setActivated(currentItem, newStatus);

		ItemStack itemStack = this.playerMenuUtility.getItemInHand().get();
		CustomItemFlagManager.getInstance().toggleItemFlag(
			itemStack,
			clickedCustomFlag,
			newStatus);
		playerMenuUtility.getItemInHand().set(itemStack);
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
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
