package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsMenu extends Menu {
	public SpecialsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Specials";
	}

	@Override
	public int getSlots() {
		return 9*4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		ItemFlag clickedFlag = null;

		if (e.getSlot() == 27) {
			this.playerMenuUtility.getOwner().closeInventory();
			return;
		}

		if (e.getSlot() == 28) {
			new ItemEditMenu(this.playerMenuUtility)
				.open();
			return;
		}

		switch (e.getSlot()) {
			case 10,16,19,20,24,25:
				break;
			case 11:
				clickedFlag = ItemFlag.HIDE_ENCHANTS;
				break;
			case 12:
				clickedFlag = ItemFlag.HIDE_ATTRIBUTES;
				break;
			case 13:
				clickedFlag = ItemFlag.HIDE_UNBREAKABLE;
				break;
			case 14:
				clickedFlag = ItemFlag.HIDE_DESTROYS;
				break;
			case 15:
				clickedFlag = ItemFlag.HIDE_PLACED_ON;
				break;
			case 21:
				clickedFlag = ItemFlag.HIDE_ADDITIONAL_TOOLTIP;
				break;
			case 22:
				clickedFlag = ItemFlag.HIDE_DYE;
				break;
			case 23:
				clickedFlag = ItemFlag.HIDE_ARMOR_TRIM;
				break;
		}

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
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		this.inventory.setItem(27, ItemStacks.closeInventory);
		this.inventory.setItem(28, ItemStacks.backInventory);

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

	private boolean hasGlow(ItemStack item) {
		return item.getItemMeta().hasEnchants()
			&& (item.getItemMeta().getEnchantLevel(Enchantment.LUCK_OF_THE_SEA) == 1);
	}
	private void setGlow(ItemStack item, boolean active) {
		ItemMeta itemMeta = item.getItemMeta();

		ArrayList<String> lore;
		if(itemMeta.hasLore()) {
			lore = new ArrayList<>(itemMeta.getLore());
		} else {
			lore = new ArrayList<>();
		}

		if(active) {
			itemMeta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
			lore.clear();
			lore.add(0, ChatColor.GREEN + "" + ChatColor.ITALIC + "Aktiviert");
			itemMeta.setLore(lore);
		} else {
			itemMeta.removeEnchant(Enchantment.LUCK_OF_THE_SEA);
			lore.clear();
			lore.add(0, ChatColor.RED + "" + ChatColor.ITALIC + "Deaktiviert");
			itemMeta.setLore(lore);
		}
		item.setItemMeta(itemMeta);
	}
}
