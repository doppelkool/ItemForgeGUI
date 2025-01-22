package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.*;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemEditMenu extends Menu {

	public ItemEditMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit: " + ItemStackHelper.formatCAPSName(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getType().getTranslationKey());
	}

	@Override
	public int getSlots() {
		return 9*3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 18) {
			this.playerMenuUtility.getOwner().closeInventory();
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
		if (e.getSlot() == 15 && ItemStackHelper.isLeather(item.getType())) {
			new LeatherItemColorMenu(this.playerMenuUtility)
				.open();
			return;
		}
		if(e.getSlot() == 15 &&
			ItemStackHelper.isOnlyDyeColorableWithoutMixins(item.getType())) {
			new ColorPickerMenu(this.playerMenuUtility)
				.open();
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

		this.inventory.setItem(18, closeInventory);
		this.inventory.setItem(10, editName);
		this.inventory.setItem(11, editLore);
		this.inventory.setItem(12, editEnchantments);

		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		if(ItemStackHelper.isDamageable(item)) {
			this.inventory.setItem(13, editDurability);
		} else {
			this.inventory.setItem(13, notAvailable(editDurability));
		}

		this.inventory.setItem(14, editAmount);

		if(editColorAvailable(item)) {
			this.inventory.setItem(15, editColor);
		} else {
			this.inventory.setItem(15, notAvailable(editColor));
		}

		this.inventory.setItem(16, editSpecials);
		setFillerGlass();

	}

	private boolean editColorAvailable(ItemStack item) {
		return ItemStackHelper.isLeather(item.getType()) ||
			ItemStackHelper.isOnlyDyeColorableWithoutMixins(item.getType());
	}

	private void editLoreProcess() {
		String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "ItemForgeGUI" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
		String message = prefix + "\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " You have received a book\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " Please edit the content according to your futures item lore and click \"Done\"\n" +
			ChatColor.GRAY + "-" + ChatColor.GRAY + " Your item was temporarily stored and will be replaced back after editing the book\n" +
			ChatColor.RED + "Warning" + ChatColor.GRAY + ": Situations with no changes to the item lore won't be detected.";
		//, resulting in no chances

		swapItemInHandWithEditLoreBook();
		playerMenuUtility.getOwner().closeInventory();
		playerMenuUtility.getOwner().sendMessage(message);
	}

	private void swapItemInHandWithEditLoreBook() {
		PlayerInventory inventory = playerMenuUtility.getOwner().getInventory();
		int heldItemSlot = inventory.getHeldItemSlot();
		this.playerMenuUtility.setStoredSlot(heldItemSlot);

		ItemStack item = inventory.getItem(heldItemSlot);
		this.playerMenuUtility.setTempStoredItem(item);
		inventory.setItem(heldItemSlot, createCustomLoreBook());
	}

	private ItemStack createCustomLoreBook() {
		ItemMeta itemMeta = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta();

		List<String> lore = List.of();
		if (itemMeta.hasLore()) {
			lore = itemMeta.getLore();
		}

		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setTitle("Edit the item lore");
		meta.setAuthor(" ");
		meta.addPage(String.join("\n", lore).replace(ChatColor.COLOR_CHAR, '&'));

		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(Main.getPlugin().getCustomLoreEditBookKey(), PersistentDataType.BOOLEAN, true);

		book.setItemMeta(meta);
		return book;
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
