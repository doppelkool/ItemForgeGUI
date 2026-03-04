package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.MainMenuItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.LoreProcessManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ObservableObject;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.*;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.EnchantmentMenus.ActivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.EnchantmentMenus.DeactivatedEnchantmentsMenu;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.notAvailable;

/**
 * Main menu to enter the main function of this plugin.
 * It provides the way to alter the item in many ways including change its display name, lore, amount and item flags, etc.
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemEditMenu extends Menu {

	public ItemEditMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		initItemStack();
	}

	private void initItemStack() {
		ItemStack itemToBeEdited;

		if (playerMenuUtility.getAPICallback().isEmpty()) {
			itemToBeEdited = playerMenuUtility.getOwner().getInventory().getItemInMainHand().clone();

			ObservableObject<ItemStack> itemInHand = new ObservableObject<>(itemToBeEdited);
			itemInHand.onChange((item) -> {
				playerMenuUtility.getOwner().getInventory().setItemInMainHand(item);
			});
			playerMenuUtility.setItemInHand(itemInHand);
		} else {
			itemToBeEdited = playerMenuUtility.getItemInHand().get().clone();
		}

		playerMenuUtility.getItemInHand().set(itemToBeEdited);
	}

	@Override
	public String getMenuName() {
		return "Edit: " + ItemStackModifyHelper.formatTranslationalNames(this.playerMenuUtility.getItemInHand().get().getType().getTranslationKey());
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
		if (e.getSlot() == 26) {
			new ItemIdentityMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 10) {
			editNameProcess();
			return;
		}
		if (e.getSlot() == 11) {
			LoreProcessManager.startEditLoreProcess(this.playerMenuUtility);
			return;
		}
		ItemStack item = this.playerMenuUtility.getItemInHand().get();
		if (e.getSlot() == 12) {
			ItemMeta itemMeta = item.getItemMeta();
			if (!itemMeta.hasEnchants()) {
				new DeactivatedEnchantmentsMenu(this.playerMenuUtility)
					.open();
			} else {
				new ActivatedEnchantmentsMenu(this.playerMenuUtility)
					.open();
			}
			return;
		}
		if (e.getSlot() == 13) {
			new DurabilityMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 14) {
			new AmountMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 15 && (ItemStackModifyHelper.isLeather(item.getType()) || ItemStackModifyHelper.isWolfArmor(item.getType()))) {
			new LeatherItemColorMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 15 &&
			ItemStackModifyHelper.isOnlyDyeColorableWithoutMixins(item.getType())) {
			new ColorPickerMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if (e.getSlot() == 16) {
			new SpecialsMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		this.inventory.setItem(26, MainMenuItems.itemIdentity);

		//No back Button in Main Menu
		this.inventory.setItem(this.getSlots() - 8, GlobalItems.FILLER_GLASS);

		this.inventory.setItem(10, MainMenuItems.editName);
		this.inventory.setItem(11, MainMenuItems.editLore);
		this.inventory.setItem(12, MainMenuItems.editEnchantments);

		ItemStack item = this.playerMenuUtility.getItemInHand().get();
		if (ItemStackModifyHelper.isDamageable(item)) {
			this.inventory.setItem(13, MainMenuItems.editDurability);
		} else {
			this.inventory.setItem(13, notAvailable(MainMenuItems.editDurability));
		}

		this.inventory.setItem(14, MainMenuItems.editAmount);

		if (editColorAvailable(item)) {
			this.inventory.setItem(15, MainMenuItems.editColor);
		} else {
			this.inventory.setItem(15, notAvailable(MainMenuItems.editColor));
		}

		this.inventory.setItem(16, MainMenuItems.editSpecials);
		setFillerGlass();

	}

	private boolean editColorAvailable(ItemStack item) {
		return ItemStackModifyHelper.isLeather(item.getType()) ||
			ItemStackModifyHelper.isWolfArmor(item.getType()) ||
			ItemStackModifyHelper.isOnlyDyeColorableWithoutMixins(item.getType());
	}

	private void editNameProcess() {
		ItemStack item = this.playerMenuUtility.getItemInHand().get().clone();
		playerMenuUtility.setMenuTransitioning(true);

		new AnvilGUI.Builder()
			.onClick((slot, stateSnapshot) -> {
				if (slot != AnvilGUI.Slot.OUTPUT) {
					return Collections.emptyList();
				}

				return Arrays.asList(
					AnvilGUI.ResponseAction.run(() -> {
						ItemStack renamedItemToEdit = stateSnapshot.getOutputItem();
						ItemMeta outputItemMeta = renamedItemToEdit.getItemMeta();
						String translatedText = ChatColor.translateAlternateColorCodes('&', outputItemMeta.getDisplayName());

						ItemMeta itemMeta = item.getItemMeta();
						itemMeta.setDisplayName(translatedText);
						item.setItemMeta(itemMeta);
						playerMenuUtility.getItemInHand().set(item);

						playerMenuUtility.setMenuTransitioning(false);
					}),
					AnvilGUI.ResponseAction.close(),
					AnvilGUI.ResponseAction.openInventory(this.inventory));
			})
			.itemLeft(item)
			.plugin(Main.getPlugin())
			.preventClose()
			.title("Rename the item")
			.open(this.playerMenuUtility.getOwner());
	}
}
