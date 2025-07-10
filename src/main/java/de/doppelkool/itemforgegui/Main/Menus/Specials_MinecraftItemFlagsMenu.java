package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.ItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import oshi.util.tuples.Pair;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper.setActivated;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to apply item flags to change the items appearance.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Specials_MinecraftItemFlagsMenu extends Menu {
	public Specials_MinecraftItemFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Minecraft Item Flags";
	}

	@Override
	public int getSlots() {
		return 9 * 4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot(), SpecialsMenu::new)) {
			return;
		}

		Pair<ItemFlag, ItemStack> itemFlagItemStackPair = ItemFlagManager.getInstance().getSlotToFlag().get(e.getSlot());
		if (itemFlagItemStackPair != null) {
			itemFlagClicked(e.getCurrentItem(), itemFlagItemStackPair.getA());

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
		setActivated(currentItem, itemInMainHand.getItemMeta().hasItemFlag(itemFlag));
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		ItemFlagManager.getInstance().getSlotToFlag().forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();

			boolean itemFlagApplied = itemMeta.hasItemFlag(pair.getA());
			ItemStackModifyHelper.setActivated(itemStackClone, itemFlagApplied);

			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}
}
