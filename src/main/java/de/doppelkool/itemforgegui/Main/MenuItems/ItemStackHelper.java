package de.doppelkool.itemforgegui.Main.MenuItems;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
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
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemStackHelper {
	public static Set<Material> aNoDyedVariationExist = Set.of(
		Material.BUNDLE,
		Material.SHULKER_BOX,
		Material.TERRACOTTA,
		Material.GLASS,
		Material.GLASS_PANE,
		Material.CANDLE
	);

	private static final ArrayList<String> onlyDyeColoarableNoMixingsList = new ArrayList<>();
	static {
		onlyDyeColoarableNoMixingsList.add("_SHULKER_BOX");
		onlyDyeColoarableNoMixingsList.add("_DYE");
		onlyDyeColoarableNoMixingsList.add("_WOOL");
		onlyDyeColoarableNoMixingsList.add("_BUNDLE");
		onlyDyeColoarableNoMixingsList.add("_CANDLE");
		onlyDyeColoarableNoMixingsList.add("_CONCRETE_POWDER");
		onlyDyeColoarableNoMixingsList.add("_CONCRETE");
		onlyDyeColoarableNoMixingsList.add("_GLAZED_TERRACOTTA");
		onlyDyeColoarableNoMixingsList.add("_TERRACOTTA");
		onlyDyeColoarableNoMixingsList.add("_STAINED_GLASS_PANE");
		onlyDyeColoarableNoMixingsList.add("_STAINED_GLASS");

		onlyDyeColoarableNoMixingsList.add("_BED"); //exclution for "BEDROCK" in place
		onlyDyeColoarableNoMixingsList.add("_CARPET"); //exclution for "MOSS"y carpet variations in place
	}

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

	public static boolean isOnlyDyeColorableWithoutMixins(Material itemMaterial) {

		//Colorable item category, but given material is a variation with no color
		if(aNoDyedVariationExist.contains(itemMaterial)) {
			return true;
		}

		if(itemMaterial == Material.BEDROCK ||
			itemMaterial == Material.MOSS_CARPET ||
			itemMaterial == Material.PALE_MOSS_CARPET
		) {
			return false;
		}

		for(String items : onlyDyeColoarableNoMixingsList) {
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
		if(key.equals(Main.getPlugin().getCustomLoreEditBookKey())) {
			nextItem = createCustomLoreBook(itemInMainHand);
		}
		inventory.setItem(heldItemSlot, nextItem);
	}

	private static ItemStack createCustomLoreBook(ItemStack itemInMainHand) {
		ItemMeta itemMeta = itemInMainHand.getItemMeta();

		ItemStack book = new ItemStack(Material.WRITABLE_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();

		if (itemMeta.hasLore()) {
			setBookPagesFromExistingLore(meta, itemMeta.getLore());
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

	public static boolean hasImmutability(ItemStack stack) {
		return stack.getItemMeta().getPersistentDataContainer()
			.has(Main.getPlugin().getCustomTagItemImmutabilityKey());
	}

	public static void setImmutability(ItemStack stack, boolean toggle) {
		ItemMeta itemMeta = stack.getItemMeta();
		PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
		if(toggle) {
			//Boolean value is not used, but necessary
			persistentDataContainer.set(Main.getPlugin().getCustomTagItemImmutabilityKey(), PersistentDataType.BOOLEAN, true);
		} else {
			persistentDataContainer.remove(Main.getPlugin().getCustomTagItemImmutabilityKey());
		}
		stack.setItemMeta(itemMeta);
	}

	public static boolean hasGlow(ItemStack item) {
		return item.getItemMeta().hasEnchants()
			&& (item.getItemMeta().getEnchantLevel(Enchantment.LUCK_OF_THE_SEA) == 1);
	}

	public static void setGlow(ItemStack item, boolean active) {
		String activatedLorePart = "Activated";
		String deactivatedLorePart = "Deactivated";

		ItemMeta itemMeta = item.getItemMeta();

		ArrayList<String> lore = new ArrayList<>();
		if(itemMeta.hasLore()) {
			lore.addAll(itemMeta.getLore());

			if(lore.getFirst().contains(activatedLorePart) ||
				lore.getFirst().contains(deactivatedLorePart)) {
				lore.remove(0);
			}
		}

		if(active) {
			itemMeta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
			lore.add(0, ChatColor.GREEN + "" + ChatColor.ITALIC + activatedLorePart);
			itemMeta.setLore(lore);
		} else {
			itemMeta.removeEnchant(Enchantment.LUCK_OF_THE_SEA);
			lore.add(0, ChatColor.RED + "" + ChatColor.ITALIC + deactivatedLorePart);
			itemMeta.setLore(lore);
		}
		item.setItemMeta(itemMeta);
	}
}
