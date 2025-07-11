package de.doppelkool.itemforgegui.Main.MenuItems;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemStackCreateHelper {

	public static ItemStack makeItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);

		return item;
	}

	public static void modifyLore(ItemStack itemStack, @NotNull String... lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLore(List.of(lore));
		itemStack.setItemMeta(itemMeta);
	}

	public static void modifyCurrentValueVariableInLore(ItemStack itemToChangeLore, String currentValue) {
		ItemMeta itemMeta = itemToChangeLore.getItemMeta();
		List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
		lore.replaceAll(s -> s.replace("{currentValue}", currentValue));

		itemMeta.setLore(lore);
		itemToChangeLore.setItemMeta(itemMeta);
	}

	public static void modifyItemFlags(ItemStack itemStack, ItemFlag... itemflags) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(itemflags);
		itemStack.setItemMeta(itemMeta);
	}

	public static void modifyStoredEnchantment(ItemStack itemStack, Enchantment enchantment) {
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
		meta.addStoredEnchant(enchantment, 1, true);
		itemStack.setItemMeta(meta);
	}

	public static void modifyPotionType(ItemStack fireProtectionItem, PotionType potionType) {
		PotionMeta potionMeta = (PotionMeta) fireProtectionItem.getItemMeta();
		potionMeta.setBasePotionType(potionType);
		fireProtectionItem.setItemMeta(potionMeta);
	}

	public static void modifyColor(ItemStack stack, Color color) {
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
		itemMeta.setColor(color);
		stack.setItemMeta(itemMeta);
	}

	public static void modifySpecificBannerPattern(ItemStack stack) {
		BannerMeta bannerMeta = (BannerMeta) stack.getItemMeta();
		bannerMeta.setPatterns(List.of(
			new Pattern(DyeColor.RED, PatternType.GRADIENT),
			new Pattern(DyeColor.BLUE, PatternType.STRIPE_DOWNRIGHT)
		));
		stack.setItemMeta(bannerMeta);
	}

	public static void modifyToCustomHead(ItemStack itemToEdit, SkullData skullData) {
		try {
			UUID uuid = UUID.randomUUID();
			GameProfile profile = new GameProfile(uuid, "itemforgegui");
			PropertyMap propertyMap = profile.getProperties();
			propertyMap.put("textures", new Property("textures", skullData.getBase64encoded()));

			Class<?> rpClass = Class.forName("net.minecraft.world.item.component.ResolvableProfile");

			Constructor<?> rpCtor = rpClass.getDeclaredConstructor(
				Optional.class, Optional.class, PropertyMap.class, GameProfile.class
			);

			Object rpInstance = rpCtor.newInstance(
				Optional.of("itemforgegui"),
				Optional.of(uuid),
				propertyMap,
				profile
			);

			SkullMeta meta = (SkullMeta) itemToEdit.getItemMeta();
			Field profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, rpInstance);

			itemToEdit.setItemMeta(meta);

		} catch (Exception e) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to modify skull head:", e);
		}
	}

	public static ItemStack notAvailable(ItemStack itemStack) {
		ItemStack itemStackNotAvailable = itemStack.clone();
		ArrayList<String> lore;
		ItemMeta itemMeta = itemStackNotAvailable.getItemMeta();
		if (itemMeta.hasLore()) {
			lore = new ArrayList<>(itemMeta.getLore());
		} else {
			lore = new ArrayList<>();
		}

		lore.addFirst(ChatColor.RED + "" + ChatColor.ITALIC + "Not available");
		itemMeta.setLore(lore);

		PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
		persistentDataContainer.set(Main.getPlugin().getCustomNotAvailableStackIDKey(), PersistentDataType.BOOLEAN, true);

		itemStackNotAvailable.setItemMeta(itemMeta);
		itemStackNotAvailable.setType(Material.BARRIER);
		return itemStackNotAvailable;
	}
}
