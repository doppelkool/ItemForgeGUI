package de.doppelkool.itemforgegui.Main.MenuItems;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Resources;
import de.doppelkool.itemforgegui.Main.VersionDependency.Materials;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemStackHelper {

	private static final Enchantment glowEnchantment = Enchantment.LUCK_OF_THE_SEA;

	public static String formatCAPSName(String name) {
		String[] parts = name.split("\\.", 3);
		String result = parts.length == 3 ? parts[2] : name;

		String[] words = result.split("_");
		StringBuilder formattedName = new StringBuilder();
		for (String word : words) {
			formattedName.append(Character.toUpperCase(word.charAt(0)))
				.append(word.substring(1))
				.append(" ");
		}

		return formattedName.toString().trim();
	}

	public static boolean isLeather(Material itemMaterial) {
		return itemMaterial.name().contains("LEATHER_");
	}

	public static boolean isWolfArmor(Material type) {
		return type == Material.WOLF_ARMOR;
	}

	public static boolean isOnlyDyeColorableWithoutMixins(Material itemMaterial) {

		//Colorable item category, but given material is a variation with no color
		if (Resources.A_NO_DYED_VARIATION_EXIST.contains(itemMaterial)) {
			return true;
		}

		boolean paleMossCarpetLoaded = Materials.getInstance().isLoaded("PALE_MOSS_CARPET");

		if (itemMaterial == Material.BEDROCK ||
			itemMaterial == Material.MOSS_CARPET ||
			(paleMossCarpetLoaded && itemMaterial == Material.PALE_MOSS_CARPET)
		) {
			return false;
		}

		for (String items : Resources.ONLY_DYE_COLOARABLE_NO_MIXINGS_LIST) {
			if (!itemMaterial.name().contains(items)) {
				continue;
			}

			String colorName = itemMaterial.name().replace(items, "");
			try {
				DyeColor.valueOf(colorName);
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

	public static boolean isDamageable(ItemStack item) {
		return item.getType().getMaxDurability() != 0;
	}

	public static void swapItemInHandWithEditAttributeBook(PlayerMenuUtility util, NamespacedKey key) {
		PlayerInventory inventory = util.getOwner().getInventory();
		int heldItemSlot = inventory.getHeldItemSlot();
		util.setStoredSlot(heldItemSlot);

		ItemStack item = inventory.getItem(heldItemSlot);
		util.setTempStoredItem(item);

		ItemStack itemInMainHand = inventory.getItemInMainHand();

		ItemStack nextItem = null;
		if (key.equals(Main.getPlugin().getCustomLoreEditBookKey())) {
			nextItem = createCustomLoreBook(itemInMainHand);
		}
		inventory.setItem(heldItemSlot, nextItem);
	}

	private static ItemStack createCustomLoreBook(ItemStack itemInMainHand) {
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();

		if (itemMeta.hasLore()) {
			setBookPagesFromExistingLore(meta, new ItemInfoManager(itemInMainHand).getItemLore());
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

	public static boolean hasGlow(ItemStack item) {
		return item.getItemMeta().hasEnchants()
			&& (item.getItemMeta().getEnchantLevel(glowEnchantment) == 1);
	}

	public static void setActivated(ItemStack item, boolean active) {
		String activatedLorePart = "Activated";
		String deactivatedLorePart = "Deactivated";

		ItemMeta itemMeta = item.getItemMeta();

		ArrayList<String> lore = new ArrayList<>();
		if (itemMeta.hasLore()) {
			lore.addAll(itemMeta.getLore());

			if (lore.getFirst().contains(activatedLorePart) ||
				lore.getFirst().contains(deactivatedLorePart)) {
				lore.removeFirst();
			}
		}

		if (active) {
			itemMeta.addEnchant(glowEnchantment, 1, true);
			lore.addFirst(ChatColor.GREEN + "" + ChatColor.ITALIC + activatedLorePart);
		} else {
			itemMeta.removeEnchant(glowEnchantment);
			lore.addFirst(ChatColor.RED + "" + ChatColor.ITALIC + deactivatedLorePart);
		}
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
	}

	public static void updateCraftingPreventionInMenuItemLore(ItemStack itemStackClone, PreventionFlagManager.CraftingPreventionFlag activeCraftingPrevention) {
		ItemMeta itemMeta = itemStackClone.getItemMeta();
		List<String> oldLore = itemMeta.getLore();

		List<String> newLore = new ArrayList<>();
		if (oldLore != null && !oldLore.isEmpty()) {
			newLore.add(oldLore.get(0));
			if (oldLore.size() > 1) {
				newLore.add(oldLore.get(1));
			}
		} else {
			newLore.add("");
			newLore.add("");
		}

		for (PreventionFlagManager.CraftingPreventionFlag craftingPrevention : PreventionFlagManager.CraftingPreventionFlag.values()) {
			ChatColor color = craftingPrevention.equals(activeCraftingPrevention) ? ChatColor.GREEN : ChatColor.RED;
			newLore.add(ChatColor.GRAY + "- " + color + craftingPrevention.getItemDescription());
		}

		itemMeta.setLore(newLore);
		itemStackClone.setItemMeta(itemMeta);
	}
}
