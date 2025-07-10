package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.MainMenuItems;
import de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus.ActivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus.DeactivatedEnchantmentsMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

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
		ItemStack itemInMainHand = playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		if (!UniqueItemIdentifierManager.isUniqueItem(itemInMainHand)) {
			CustomItemFlagManager.getInstance().initCustomItemFlags(itemInMainHand);
			new ItemInfoManager(itemInMainHand).initItemDescription();
		}

		UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(
			itemInMainHand);
	}

	@Override
	public String getMenuName() {
		return "Edit: " + ItemStackModifyHelper.formatCAPSName(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType().getTranslationKey());
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
			editLoreProcess();
			return;
		}
		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
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

		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
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

	private void editLoreProcess() {
		ItemStackModifyHelper.swapItemInHandWithEditAttributeBook(this.playerMenuUtility, Main.getPlugin().getCustomLoreEditBookKey());
		playerMenuUtility.getOwner().closeInventory();
		MessageManager.message(playerMenuUtility.getOwner(), Messages.BOOK_EDITOR_INFORMATION);
	}

	private void editNameProcess() {
		new AnvilGUI.Builder()
			.onClick((slot, stateSnapshot) -> {
				if (slot != AnvilGUI.Slot.OUTPUT) {
					return Collections.emptyList();
				}

				return Arrays.asList(
					AnvilGUI.ResponseAction.run(() -> {
						ItemStack outputItem = stateSnapshot.getOutputItem();
						ItemMeta outputItemMeta = outputItem.getItemMeta();
						String translatedText = ChatColor.translateAlternateColorCodes('&', outputItemMeta.getDisplayName());
						outputItemMeta.setDisplayName(translatedText);
						outputItem.setItemMeta(outputItemMeta);
						this.playerMenuUtility.getOwner().getInventory().setItemInMainHand(outputItem);
					}),
					AnvilGUI.ResponseAction.close(),
					AnvilGUI.ResponseAction.openInventory(this.inventory));
			})
			.itemLeft(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand())
			.plugin(Main.getPlugin())
			.preventClose()
			.title("Rename the item")
			.open(this.playerMenuUtility.getOwner());
	}
}
