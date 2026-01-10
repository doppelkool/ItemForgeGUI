package de.doppelkool.itemforgegui.Main.MenuServices;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

/**
 * This manager manages the start of the lore editing process. There are the following two options:
 *	Normal + API:
 *		1. Store main hand slot
 *		2. Store item in main hand slot in PlayerMenuUtility, in LoreStorage
 *		3. Replace item in hand with lore editing book
 *
 * @author doppelkool | github.com/doppelkool
 */
public class LoreProcessManager {

	public static void startEditLoreProcess(PlayerMenuUtility playerMenuUtility) {
		loadEditLoreStorage(playerMenuUtility);
		ItemStack customLoreBook = createCustomLoreBook(playerMenuUtility.getLoreProcessStorage().getItemToReplaceBack());
		setHandItemToLoreEditBook(playerMenuUtility, customLoreBook);

		playerMenuUtility.setMenuTransitioning(true);
		playerMenuUtility.getOwner().closeInventory();
		MessageManager.message(playerMenuUtility.getOwner(), Messages.BOOK_EDITOR_INFORMATION);
	}

	private static void loadEditLoreStorage(PlayerMenuUtility playerMenuUtility) {
		LoreProcessStorage loreStorage = new LoreProcessStorage();
		loreStorage.setSlotToEditLoreOn(playerMenuUtility.getOwner().getInventory().getHeldItemSlot());

		playerMenuUtility.getAPICallback().ifPresentOrElse((apiCallback) -> {
			loreStorage.setItemToReplaceBack(playerMenuUtility.getOwner().getInventory().getItemInMainHand().clone());
		},() -> {
			loreStorage.setItemToReplaceBack(playerMenuUtility.getItemInHand().get().clone());
		});

		playerMenuUtility.setLoreProcessStorage(loreStorage);
	}

	private static ItemStack createCustomLoreBook(ItemStack itemWithOriginalLore) {
		ItemMeta itemMeta = itemWithOriginalLore.getItemMeta();

		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();

		if (itemMeta.hasLore()) {
			setBookPagesFromExistingLore(meta, new ItemInfoManager(itemWithOriginalLore).getItemLore());
		}

		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(Main.getPlugin().getCustomLoreEditBookKey(), PersistentDataType.BOOLEAN, true);

		book.setItemMeta(meta);
		return book;
	}

	private static void setBookPagesFromExistingLore(BookMeta bookMeta, List<String> lore) {
		bookMeta.setTitle("Edit the item lore");
		bookMeta.setAuthor(" ");

		for (int i = 0; i < lore.size(); i += 13) {
			int end = Math.min(i + 13, lore.size());
			List<String> pageLines = lore.subList(i, end);

			String pageContent = String.join("\n", pageLines)
				.replace(ChatColor.COLOR_CHAR, '&');

			bookMeta.addPage(pageContent);
		}
	}

	private static void setHandItemToLoreEditBook(PlayerMenuUtility playerMenuUtility, ItemStack customLoreBook) {
		playerMenuUtility.getOwner()
			.getInventory()
			.setItem(
				playerMenuUtility.getLoreProcessStorage().getSlotToEditLoreOn(),
				customLoreBook);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class LoreProcessStorage {
		private int slotToEditLoreOn;
		private ItemStack itemToReplaceBack;
	}
}
