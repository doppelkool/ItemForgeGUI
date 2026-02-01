package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.ItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Pair;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper.setActivated;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to apply item flags to change the items appearance.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MinecraftItemFlagsMenu extends Menu {
	public MinecraftItemFlagsMenu(PlayerMenuUtility playerMenuUtility) {
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
		if (super.handleBack(e.getSlot(), null, SpecialsMenu::new)) {
			return;
		}

		Pair<ItemFlag, ItemStack> itemFlagItemStackPair = ItemFlagManager.getInstance().getSlotToFlag().get(e.getSlot());
		if (itemFlagItemStackPair == null) {
			return;
		}

		ItemStack item = this.playerMenuUtility.getItemInHand().get();
		CustomItemFlagManager.getInstance().initShowMinecraftItemFlagsFlag(item);
		itemFlagClicked(e.getCurrentItem(), itemFlagItemStackPair.getA());

		new ItemInfoManager(item).updateItemInfo();
		playerMenuUtility.getItemInHand().set(item);
		return;
	}

	private void itemFlagClicked(ItemStack currentItem, ItemFlag itemFlag) {
		ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		if (hasGlow(currentItem)) {
			itemMeta.removeItemFlags(itemFlag);
		} else {
			itemMeta.addItemFlags(itemFlag);
		}
		itemInMainHand.setItemMeta(itemMeta);
		playerMenuUtility.getItemInHand().set(itemInMainHand);

		//Only change to activated dye if flag has been applied
		setActivated(currentItem, itemInMainHand.getItemMeta().hasItemFlag(itemFlag));
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
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
