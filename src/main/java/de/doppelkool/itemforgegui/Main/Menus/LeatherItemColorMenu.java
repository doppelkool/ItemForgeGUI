package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.Color;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ColorableArmorMeta;

import java.util.List;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.modifyCurrentValueVariableInLore;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class LeatherItemColorMenu extends Menu {

	private static final List<Integer> slotsRow1 = List.of(
		9,10,11,12,    14,15,16,17
	);

	private static final List<Integer> slotsRow2 = List.of(
		18,19,20,21,    23,24,25,26
	);

	private static final List<Integer> slotsRow3 = List.of(
		27,28,29,30,    32,33,34,35
	);

	private static final ItemStack[] editNumbersMinus = new ItemStack[]{
		ItemStacks.minus50,
		ItemStacks.minus20,
		ItemStacks.minus5,
		ItemStacks.minus1,
	};

	private static final ItemStack[] editNumbersPlus = new ItemStack[]{
		ItemStacks.plus1,
		ItemStacks.plus5,
		ItemStacks.plus20,
		ItemStacks.plus50,
	};

	public LeatherItemColorMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public int getSlots() {
		return 9*6;
	}

	@Override
	public String getMenuName() {
		return "Color Picker: " + ItemStackHelper.formatCAPSName(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType().getTranslationKey());
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if(e.getSlot() == 45) {
			this.playerMenuUtility.setTempStoredItem(null);
			handleClose();
			return;
		}

		if(e.getSlot() == 46) {
			this.playerMenuUtility.setTempStoredItem(null);
			handleBack();
			return;
		}

		if(e.getSlot() == 53) {
			if(this.playerMenuUtility.getTempStoredItem() == null) {
				return;
			}

			this.playerMenuUtility.getOwner().getInventory().setItemInMainHand(
				this.playerMenuUtility.getTempStoredItem());

			loadAllRGBCaps();
			return;
		}

		if(slotsRow1.contains(e.getSlot())
				|| slotsRow2.contains(e.getSlot())
				|| slotsRow3.contains(e.getSlot())) {
			ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
			ItemStack newItem = editItem(itemInMainHand, e.getSlot());
			if (newItem == null) {
				return;
			}

			this.playerMenuUtility.getOwner().getInventory().setItemInMainHand(newItem);
			loadAllRGBCaps();
		}
	}

	private ItemStack editItem(ItemStack itemInMainHand, int slot) {
		ItemStack clone = itemInMainHand.clone();

		ColorableArmorMeta colorable = (ColorableArmorMeta) clone.getItemMeta();
		Color color = colorable.getColor();

		int row = slot / 9;
		int column = slot % 9;

		int increment = switch (column) {
			case 0 -> -50;
			case 1 -> -20;
			case 2 -> -5;
			case 3 -> -1;
			case 5 -> +1;
			case 6 -> +5;
			case 7 -> +20;
			case 8 -> +50;
			default -> 0;
		};

		if (increment == 0) {
			return null;
		}

		color = switch (row) {
			case 1 -> color.setRed(clamp(color.getRed() + increment));
			case 2 -> color.setGreen(clamp(color.getGreen() + increment));
			case 3 -> color.setBlue(clamp(color.getBlue() + increment));
			default -> throw new IllegalStateException("Unexpected value: " + row);
		};

		colorable.setColor(color);
		clone.setItemMeta(colorable);

		return clone;
	}

	// ensure 0 <= value <= 255
	int clamp(int val) {
		return Math.max(0, Math.min(255, val));
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		this.playerMenuUtility.setTempStoredItem(itemInMainHand);

		loadAllRGBCaps();
		placeColorRow();

		ItemStack resetBackLeatherItem = ItemStacks.resetBackLeatherItem.clone();
		ColorableArmorMeta itemInMainHandItemMeta = (ColorableArmorMeta)itemInMainHand.getItemMeta();

		resetBackLeatherItem.setType(itemInMainHand.getType());
		ItemStacks.modifyColor(resetBackLeatherItem, itemInMainHandItemMeta.getColor());

		this.inventory.setItem(53, resetBackLeatherItem);

		setFillerGlass();
	}

	private void loadAllRGBCaps() {
		Color color = ((ColorableArmorMeta)this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta())
			.getColor();

		ItemStack redCap = ItemStacks.RED_CAP.clone();
		modifyCurrentValueVariableInLore(
			redCap,
			String.valueOf(color.getRed()));
		this.inventory.setItem(13, redCap);

		ItemStack greenCap = ItemStacks.GREEN_CAP.clone();
		modifyCurrentValueVariableInLore(
			greenCap,
			String.valueOf(color.getGreen()));
		this.inventory.setItem(22, greenCap);

		ItemStack blueCap = ItemStacks.BLUE_CAP.clone();
		modifyCurrentValueVariableInLore(
			blueCap,
			String.valueOf(color.getBlue()));
		this.inventory.setItem(31, blueCap);
	}

	private void placeColorRow() {
		for (int i = 0; i < 4; i++) {
			this.inventory.setItem(slotsRow1.get(i), editNumbersMinus[i]);
			this.inventory.setItem(slotsRow1.get(i + 4), editNumbersPlus[i]);
			this.inventory.setItem(slotsRow2.get(i), editNumbersMinus[i]);
			this.inventory.setItem(slotsRow2.get(i + 4), editNumbersPlus[i]);
			this.inventory.setItem(slotsRow3.get(i), editNumbersMinus[i]);
			this.inventory.setItem(slotsRow3.get(i + 4), editNumbersPlus[i]);
		}
	}
}