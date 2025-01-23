package de.doppelkool.itemforgegui.Main.MenuItems;

import de.doppelkool.itemforgegui.Main.Main;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_21_R3.profile.CraftPlayerProfile;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemStacks {
	public static final ItemStack FILLER_GLASS;
	public static final ItemStack DEVIDE_BARS;

	public static final ItemStack editName;
	public static final ItemStack editLore;
	public static final ItemStack editEnchantments;
	public static final ItemStack editDurability;
	public static final ItemStack editSpecials;
	public static final ItemStack editAmount;
	public static final ItemStack editColor;

	public static final ItemStack activatedDye;
	public static final ItemStack deactivatedDye;

	public static final ItemStack hideEnchantments;
	public static final ItemStack hideAttributes;
	public static final ItemStack hideUnbreakable;
	public static final ItemStack hideDestroys;
	public static final ItemStack hidePlacedOn;
	public static final ItemStack hideAdditionalToolTip;
	public static final ItemStack hideDye;
	public static final ItemStack hideArmorTrim;

	public static final ItemStack minus100;
	public static final ItemStack minus50;
	public static final ItemStack minus20;
	public static final ItemStack minus10;
	public static final ItemStack minus5;
	public static final ItemStack minus1;
	public static final ItemStack plus1;
	public static final ItemStack plus5;
	public static final ItemStack plus10;
	public static final ItemStack plus20;
	public static final ItemStack plus50;
	public static final ItemStack plus100;
	public static final ItemStack toMin;
	public static final ItemStack toMax;
	public static final ItemStack customValue;
	public static final ItemStack toDefault;

	public static final ItemStack paginatedMenuLeft;
	public static final ItemStack paginatedMenuRight;
	public static final ItemStack closeInventory;
	public static final ItemStack backInventory;

	public static final ItemStack activatedEnchantments;
	public static final ItemStack deactivatedEnchantments;

	public static final ItemStack DEFAULT_dye;
	public static final ItemStack WHITE_dye;
	public static final ItemStack ORANGE_dye;
	public static final ItemStack MAGENTA_dye;
	public static final ItemStack LIGHT_BLUE_dye;
	public static final ItemStack YELLOW_dye;
	public static final ItemStack LIME_dye;
	public static final ItemStack PINK_dye;
	public static final ItemStack GRAY_dye;
	public static final ItemStack LIGHT_GRAY_dye;
	public static final ItemStack CYAN_dye;
	public static final ItemStack PURPLE_dye;
	public static final ItemStack BLUE_dye;
	public static final ItemStack BROWN_dye;
	public static final ItemStack GREEN_dye;
	public static final ItemStack RED_dye;
	public static final ItemStack BLACK_dye;

	public static final ItemStack RED_CAP;
	public static final ItemStack GREEN_CAP;
	public static final ItemStack BLUE_CAP;
	public static final ItemStack resetBackLeatherItem;

	static {
		FILLER_GLASS = makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
		DEVIDE_BARS = makeItem(Material.IRON_BARS, " ");

		editName = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Edit Name");
		editLore = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Edit Lore");
		editEnchantments = makeItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Edit Enchantments");
		editDurability = makeItem(Material.DAMAGED_ANVIL, ChatColor.GREEN + "Edit Durability");
		editSpecials = makeItem(Material.END_CRYSTAL, ChatColor.GREEN + "Edit Specialities");
		editAmount = makeItem(Material.CHEST, ChatColor.GREEN + "Edit Amount");
		editColor = makeItem(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "Edit Color");
		modifyColor(editColor,Color.BLUE);
		modifyItemFlags(editColor, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		activatedDye = makeItem(Material.LIME_DYE, ChatColor.GREEN + "Aktiviert");
		deactivatedDye = makeItem(Material.GRAY_DYE, ChatColor.RED + "Deaktiviert");

		hideEnchantments = makeItem(Material.ENCHANTING_TABLE, ChatColor.GREEN + "Hide Enchantments");
		modifyItemFlags(hideEnchantments, ItemFlag.HIDE_ENCHANTS);
		hideAttributes = makeItem(Material.PAPER, ChatColor.GREEN + "Hide Attributes");
		modifyItemFlags(hideAttributes, ItemFlag.HIDE_ENCHANTS);
		hideUnbreakable = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Hide Unbreakable Property");
		modifyItemFlags(hideUnbreakable, ItemFlag.HIDE_ENCHANTS);
		hideDestroys = makeItem(Material.STONE_PICKAXE, ChatColor.GREEN + "Hide Destorys Property");
		modifyItemFlags(hideDestroys, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		hidePlacedOn = makeItem(Material.STONE, ChatColor.GREEN + "Hide Place-On Property");
		modifyItemFlags(hidePlacedOn, ItemFlag.HIDE_ENCHANTS);
		hideAdditionalToolTip = makeItem(Material.EMERALD, ChatColor.GREEN + "Hide Additional ToolTip");
		modifyItemFlags(hideAdditionalToolTip, ItemFlag.HIDE_ENCHANTS);
		hideDye = makeItem(Material.MAGENTA_DYE, ChatColor.GREEN + "Hide Dye");
		modifyItemFlags(hideDye, ItemFlag.HIDE_ENCHANTS);
		hideArmorTrim = makeItem(Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.GREEN + "Hide Armor Trim");
		modifyItemFlags(hideArmorTrim, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

		minus100 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-100");
		modifyToCustomHead(minus100, SkullData.REDSTONE_HUNDRED);
		minus50 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-50");
		modifyToCustomHead(minus50, SkullData.REDSTONE_FIFTY);
		minus20 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-20");
		modifyToCustomHead(minus20, SkullData.REDSTONE_TWENTY);
		minus10 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-10");
		modifyToCustomHead(minus10, SkullData.REDSTONE_TEN);
		minus5 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-5");
		modifyToCustomHead(minus5, SkullData.REDSTONE_FIVE);
		minus1 = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "-1");
		modifyToCustomHead(minus1, SkullData.REDSTONE_ONE);
		plus1 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+1");
		modifyToCustomHead(plus1, SkullData.LIME_ONE);
		plus5 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+5");
		modifyToCustomHead(plus5, SkullData.LIME_FIVE);
		plus10 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+10");
		modifyToCustomHead(plus10, SkullData.LIME_TEN);
		plus20 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+20");
		modifyToCustomHead(plus20, SkullData.LIME_TWENTY);
		plus50 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+50");
		modifyToCustomHead(plus50, SkullData.LIME_FIFTY);
		plus100 = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "+100");
		modifyToCustomHead(plus100, SkullData.LIME_HUNDRED);
		toMin = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Minimum");
		modifyToCustomHead(toMin, SkullData.REDSTONE_M);
		toMax = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Maximum");
		modifyToCustomHead(toMax, SkullData.LIME_M);
		customValue = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Custom");
		toDefault = makeItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ChatColor.GREEN + "Default Stack Size");

		paginatedMenuLeft = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Left");
		modifyToCustomHead(paginatedMenuLeft, SkullData.QUARTZ_ARROW_LEFT);
		paginatedMenuRight = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Right");
		modifyToCustomHead(paginatedMenuRight, SkullData.QUARTZ_ARROW_RIGHT);
		closeInventory = makeItem(Material.PLAYER_HEAD, ChatColor.DARK_RED + "Close");
		modifyToCustomHead(closeInventory, SkullData.REDSTONE_BLOCK_ARROW_LEFT);
		backInventory = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Back");
		modifyToCustomHead(backInventory, SkullData.REDSTONE_BLOCK_LEFT);

		activatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Activated Enchantments");
		modifyToCustomHead(activatedEnchantments, SkullData.QUARTZ_CHECK);
		deactivatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Deactivated Enchantments");
		modifyToCustomHead(deactivatedEnchantments, SkullData.QUARTZ_X);

		DEFAULT_dye = makeItem(Material.PAPER, ChatColor.WHITE + "No Color");
		WHITE_dye = makeItem(Material.WHITE_DYE, ChatColor.WHITE + "White");
		ORANGE_dye = makeItem(Material.ORANGE_DYE, ChatColor.GOLD + "Orange");
		MAGENTA_dye = makeItem(Material.MAGENTA_DYE, ChatColor.WHITE + "Magenta");
		LIGHT_BLUE_dye = makeItem(Material.LIGHT_BLUE_DYE, ChatColor.AQUA + "Light Blue");
		YELLOW_dye = makeItem(Material.YELLOW_DYE, ChatColor.YELLOW + "Yellow");
		LIME_dye = makeItem(Material.LIME_DYE, ChatColor.GREEN + "Lime");
		PINK_dye = makeItem(Material.PINK_DYE, ChatColor.WHITE + "Pink");
		GRAY_dye = makeItem(Material.GRAY_DYE, ChatColor.DARK_GRAY + "Gray");
		LIGHT_GRAY_dye = makeItem(Material.LIGHT_GRAY_DYE, ChatColor.GRAY + "Light Gray");
		CYAN_dye = makeItem(Material.CYAN_DYE, ChatColor.DARK_AQUA + "Cyan");
		PURPLE_dye = makeItem(Material.PURPLE_DYE, ChatColor.WHITE + "Purple");
		BLUE_dye = makeItem(Material.BLUE_DYE, ChatColor.BLUE + "Blue");
		BROWN_dye = makeItem(Material.BROWN_DYE, ChatColor.GOLD + "Brown");
		GREEN_dye = makeItem(Material.GREEN_DYE, ChatColor.DARK_GREEN + "Green");
		RED_dye = makeItem(Material.RED_DYE, ChatColor.RED + "Red");
		BLACK_dye = makeItem(Material.BLACK_DYE, ChatColor.BLACK + "Black");

		RED_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.RED + "Red");
		modifyColor(RED_CAP, Color.fromRGB(255,0,0));
		modifyItemFlags(RED_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		GREEN_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Green");
		modifyColor(GREEN_CAP, Color.fromRGB(0,255,0));
		modifyItemFlags(GREEN_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		BLUE_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.RED + "Blue");
		modifyColor(BLUE_CAP, Color.fromRGB(0,0,255));
		modifyItemFlags(BLUE_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		resetBackLeatherItem = makeItem(Material.LEATHER_BOOTS, ChatColor.RED + "Reset Color to Start");
		modifyItemFlags(resetBackLeatherItem, ItemFlag.HIDE_ATTRIBUTES);
	}

	public static ItemStack makeItem(Material material, String displayName, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);

		return item;
	}

	public static void modifyItemFlags(ItemStack itemStack, ItemFlag... itemflags) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(itemflags);
		itemStack.setItemMeta(itemMeta);
	}

	public static void modifyColor(ItemStack stack, Color color) {
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
		itemMeta.setColor(color);
		stack.setItemMeta(itemMeta);
	}

	public static ItemStack modifyToCustomHead(ItemStack itemToEdit, SkullData skullData) {
		SkullMeta meta = (SkullMeta) itemToEdit.getItemMeta();

		CraftPlayerProfile craftPlayerProfile = new CraftPlayerProfile(UUID.randomUUID(), "thisIsATest");
		try {
			Class<?> propertyClass = Class.forName("com.mojang.authlib.properties.Property");
			Constructor<?> propertyConstructor = propertyClass.getConstructor(String.class, String.class);
			Object propertyInstance = propertyConstructor.newInstance("textures", skullData.getBase64encoded());

			Method setproperty = craftPlayerProfile.getClass().getDeclaredMethod("setProperty", String.class, propertyClass);
			setproperty.setAccessible(true);
			setproperty.invoke(craftPlayerProfile, "textures", propertyInstance);

			ResolvableProfile resolvableProfile = craftPlayerProfile.buildResolvableProfile();

			Field profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, resolvableProfile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to set custom head profile", e);
		}

		itemToEdit.setItemMeta(meta);
		return itemToEdit;
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

		lore.add(0, ChatColor.RED + "" + ChatColor.ITALIC + "Not available");
		itemMeta.setLore(lore);

		PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
		persistentDataContainer.set(Main.getPlugin().getCustomNotAvailableStackIDKey(), PersistentDataType.BOOLEAN, true);

		itemStackNotAvailable.setItemMeta(itemMeta);
		itemStackNotAvailable.setType(Material.BARRIER);
		return itemStackNotAvailable;
	}
}
