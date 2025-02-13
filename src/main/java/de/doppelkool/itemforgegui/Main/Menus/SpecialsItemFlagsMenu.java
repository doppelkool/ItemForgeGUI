package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.setGlow;

/**
 * Class Description
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
		return 9*4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 27) {
			handleClose();
			return;
		}
		if (e.getSlot() == 28) {
			handleBack();
			return;
		}

		ItemFlag clickedFlag = switch (e.getSlot()) {
			case 11 -> ItemFlag.HIDE_ENCHANTS;
			case 12 -> ItemFlag.HIDE_ATTRIBUTES;
			case 13 -> ItemFlag.HIDE_UNBREAKABLE;
			case 14 -> ItemFlag.HIDE_DESTROYS;
			case 15 -> ItemFlag.HIDE_PLACED_ON;
			case 21 -> ItemFlag.HIDE_ADDITIONAL_TOOLTIP;
			case 22 -> ItemFlag.HIDE_DYE;
			case 23 -> ItemFlag.HIDE_ARMOR_TRIM;
			default -> null;
		};

		if(clickedFlag == null) {
			return;
		}

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		if (hasGlow(e.getCurrentItem())) {
			itemMeta.removeItemFlags(clickedFlag);
		} else {
			itemMeta.addItemFlags(clickedFlag);
		}
		itemInMainHand.setItemMeta(itemMeta);

		//Only change to activated dye if flag has been applied
		setGlow(e.getCurrentItem(), itemInMainHand.getItemMeta().hasItemFlag(clickedFlag));
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		ItemStack hideEnchantmentsClone = ItemStacks.hideEnchantments.clone();
		setGlow(hideEnchantmentsClone, itemMeta.hasItemFlag(ItemFlag.HIDE_ENCHANTS));
		this.inventory.setItem(11, hideEnchantmentsClone);

		ItemStack hideAttributesClone = ItemStacks.hideAttributes.clone();
		setGlow(hideAttributesClone, itemMeta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES));
		this.inventory.setItem(12, hideAttributesClone);

		ItemStack hideUnbreakableClone = ItemStacks.hideUnbreakable.clone();
		setGlow(hideUnbreakableClone, itemMeta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE));
		this.inventory.setItem(13, hideUnbreakableClone);

		ItemStack hideDestroysClone = ItemStacks.hideDestroys.clone();
		setGlow(hideDestroysClone, itemMeta.hasItemFlag(ItemFlag.HIDE_DESTROYS));
		this.inventory.setItem(14, hideDestroysClone);

		ItemStack hidePlacedOnClone = ItemStacks.hidePlacedOn.clone();
		setGlow(hidePlacedOnClone, itemMeta.hasItemFlag(ItemFlag.HIDE_PLACED_ON));
		this.inventory.setItem(15, hidePlacedOnClone);

		ItemStack hideAdditionalToolTipClone = ItemStacks.hideAdditionalToolTip.clone();
		setGlow(hideAdditionalToolTipClone, itemMeta.hasItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP));
		this.inventory.setItem(21, hideAdditionalToolTipClone);

		ItemStack hideDyeClone = ItemStacks.hideDye.clone();
		setGlow(hideDyeClone, itemMeta.hasItemFlag(ItemFlag.HIDE_DYE));
		this.inventory.setItem(22, hideDyeClone);

		ItemStack hideArmorTrimClone = ItemStacks.hideArmorTrim.clone();
		setGlow(hideArmorTrimClone, itemMeta.hasItemFlag(ItemFlag.HIDE_ARMOR_TRIM));
		this.inventory.setItem(23, hideArmorTrimClone);

		setFillerGlass();
	}
}
