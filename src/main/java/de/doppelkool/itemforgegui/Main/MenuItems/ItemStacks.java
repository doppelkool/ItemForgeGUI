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
public class ItemStacks {
	public static final ItemStack FILLER_GLASS;

	public static final ItemStack editName;
	public static final ItemStack editLore;
	public static final ItemStack editEnchantments;
	public static final ItemStack editDurability;
	public static final ItemStack editSpecials;
	public static final ItemStack editAmount;
	public static final ItemStack editColor;

	public static final ItemStack itemFlags;
	public static final ItemStack customItemFlags;
	public static final ItemStack preventionFlags;
	public static final ItemStack armorEffects;
	public static final ItemStack itemDrop;
	public static final ItemStack itemCraft;
	public static final ItemStack itemSmelt;
	public static final ItemStack itemFramePlace;
	public static final ItemStack throwItem;
	public static final ItemStack eatItem;
	public static final ItemStack placeItem;
	public static final ItemStack equipItem;
	public static final ItemStack destroyItem;
	public static final ItemStack repairItem;
	public static final ItemStack enchantItem;
	public static final ItemStack disenchantItem;
	public static final ItemStack upgradeItem;
	public static final ItemStack renameItem;

	public static final ItemStack hideEnchantments;
	public static final ItemStack hideAttributes;
	public static final ItemStack hideUnbreakable;
	public static final ItemStack hideDestroys;
	public static final ItemStack hidePlacedOn;
	public static final ItemStack hideAdditionalToolTip;
	public static final ItemStack hideDye;
	public static final ItemStack hideArmorTrim;

	public static final ItemStack itemFlagHide;
	public static final ItemStack preventionFlagHide;
	public static final ItemStack armorEffectHide;

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

	public static final ItemStack itemIdentity;
	public static final ItemStack editItemImmutability;
	public static final ItemStack openItemUniquenessSettings;
	public static final ItemStack copyUniqueIdentifier;
	public static final ItemStack itemUniquenessSettingsShowID;
	public static final ItemStack editUniqueIdentifier;

	public static final ItemStack activatedEnchantments;
	public static final ItemStack deactivatedEnchantments;
	public static final ItemStack activatedArmorEffects;
	public static final ItemStack deactivatedArmorEffects;

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

		editName = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Edit Name");
		modifyLore(editName, ChatColor.YELLOW + "Change the display name");
		editLore = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Edit Lore");
		modifyLore(editLore, ChatColor.YELLOW + "Change the lore text");
		editEnchantments = makeItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Edit Enchantments");
		modifyLore(editEnchantments, ChatColor.YELLOW + "Add or Remove enchantments");
		editDurability = makeItem(Material.DAMAGED_ANVIL, ChatColor.GREEN + "Edit Durability");
		modifyLore(editDurability, ChatColor.YELLOW + "Increase or Decrease the durability");
		editSpecials = makeItem(Material.END_CRYSTAL, ChatColor.GREEN + "Edit Specialities");
		modifyLore(editSpecials, ChatColor.YELLOW + "Add or remove special ItemFlags");
		editAmount = makeItem(Material.CHEST, ChatColor.GREEN + "Edit Amount");
		modifyLore(editAmount, ChatColor.YELLOW + "Change the amount of items");
		editColor = makeItem(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "Edit Color");
		modifyLore(editColor, ChatColor.YELLOW + "Change the color of the item");
		modifyColor(editColor, Color.BLUE);
		modifyItemFlags(editColor, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		itemFlags = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Edit Minecraft Item Flags");
		customItemFlags = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Edit Custom Item Flags");
		modifyItemFlags(customItemFlags, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		modifySpecificBannerPattern(customItemFlags);
		hideEnchantments = makeItem(Material.ENCHANTING_TABLE, ChatColor.GREEN + "Hide Enchantments");
		modifyLore(hideEnchantments, ChatColor.YELLOW + "Hides the enchantment in the items description");
		modifyItemFlags(hideEnchantments, ItemFlag.HIDE_ENCHANTS);
		hideAttributes = makeItem(Material.PAPER, ChatColor.GREEN + "Hide Attributes");
		modifyLore(hideAttributes, ChatColor.YELLOW + "Hide the items attributes in the description", ChatColor.YELLOW + "For example the attack damage of a sword");
		modifyItemFlags(hideAttributes, ItemFlag.HIDE_ENCHANTS);
		hideUnbreakable = makeItem(Material.CRYING_OBSIDIAN, ChatColor.GREEN + "Hide Unbreakable Property");
		modifyLore(hideUnbreakable, ChatColor.YELLOW + "Hides the fact that the item is unbreakable");
		modifyItemFlags(hideUnbreakable, ItemFlag.HIDE_ENCHANTS);
		hideDestroys = makeItem(Material.STONE_PICKAXE, ChatColor.GREEN + "Hide Destorys Property");
		modifyLore(hideDestroys, ChatColor.YELLOW + "Hides what the item is able to destroy");
		modifyItemFlags(hideDestroys, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		hidePlacedOn = makeItem(Material.STONE, ChatColor.GREEN + "Hide Place-On Property");
		modifyLore(hidePlacedOn, ChatColor.YELLOW + "Hides what the item is able to be placed on");
		modifyItemFlags(hidePlacedOn, ItemFlag.HIDE_ENCHANTS);
		hideAdditionalToolTip = makeItem(Material.EMERALD, ChatColor.GREEN + "Hide Additional ToolTip");
		modifyLore(hideAdditionalToolTip, ChatColor.YELLOW + "Hide the items additional tooltips in the description", ChatColor.YELLOW + "For example which smithing ingredients you need for an armor trim");
		modifyItemFlags(hideAdditionalToolTip, ItemFlag.HIDE_ENCHANTS);
		hideDye = makeItem(Material.MAGENTA_DYE, ChatColor.GREEN + "Hide Dye");
		modifyLore(hideDye, ChatColor.YELLOW + "Hides the RGB hex value showed in a colored items description");
		modifyItemFlags(hideDye, ItemFlag.HIDE_ENCHANTS);
		hideArmorTrim = makeItem(Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, ChatColor.GREEN + "Hide Armor Trim");
		modifyLore(hideArmorTrim, ChatColor.YELLOW + "Hides the applied armor trim on an armor piece in its description");
		modifyItemFlags(hideArmorTrim, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

		itemFlagHide = makeItem(Material.WHITE_BANNER, ChatColor.GREEN + "Hide Minecraft Item Flags");
		modifyLore(itemFlagHide, ChatColor.YELLOW + "Minecraft Item Flags will not be displayed in the items lore");
		modifyItemFlags(itemFlagHide, ItemFlag.HIDE_ENCHANTS);
		preventionFlagHide = makeItem(Material.STRUCTURE_VOID, ChatColor.GREEN + "Hide Prevention Flags");
		modifyLore(preventionFlagHide, ChatColor.YELLOW + "Prevention Flags will not be displayed in the items lore");
		modifyItemFlags(preventionFlagHide, ItemFlag.HIDE_ENCHANTS);
		armorEffectHide = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Hide Armor Effects");
		modifyLore(armorEffectHide, ChatColor.YELLOW + "Armor Effects will not be displayed in the items lore");
		modifyItemFlags(armorEffectHide, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

		preventionFlags = makeItem(Material.STRUCTURE_VOID, ChatColor.GREEN + "Edit Prevention Flags");
		modifyLore(preventionFlags, ChatColor.YELLOW + "Edit what the player can/cannot do with that item");
		armorEffects = makeItem(Material.SPLASH_POTION, ChatColor.GREEN + "Edit ArmorEffects");
		modifyLore(armorEffects, ChatColor.YELLOW + "Edit the effects the armor applies to the player when worn");
		modifyItemFlags(armorEffects, ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
		itemDrop = makeItem(Material.FEATHER, ChatColor.GREEN + "Disable Item Drop");
		modifyLore(itemDrop, ChatColor.YELLOW + "The player will (not) be able to drop that item");
		modifyItemFlags(itemDrop, ItemFlag.HIDE_ENCHANTS);
		itemCraft = makeItem(Material.CRAFTING_TABLE, ChatColor.GREEN + "Modify Crafting");
		modifyLore(itemCraft, ChatColor.YELLOW + "The player will not be able to craft with that item in:");
		modifyItemFlags(itemCraft, ItemFlag.HIDE_ENCHANTS);
		itemSmelt = makeItem(Material.FURNACE, ChatColor.GREEN + "Disable Smelting");
		modifyLore(itemSmelt, ChatColor.YELLOW + "The player will (not) be able to smelt that item or use it as fuel");
		modifyItemFlags(itemSmelt, ItemFlag.HIDE_ENCHANTS);
		itemFramePlace = makeItem(Material.ITEM_FRAME, ChatColor.GREEN + "Disable Item Frame Placement");
		modifyLore(itemFramePlace, ChatColor.YELLOW + "The player will (not) be able to place that item in an item frame");
		modifyItemFlags(itemFramePlace, ItemFlag.HIDE_ENCHANTS);
		throwItem = makeItem(Material.EGG, ChatColor.GREEN + "Disable Item Throw");
		modifyLore(throwItem, ChatColor.YELLOW + "The player will (not) be able to throw that item (not equal Item Drop)");
		modifyItemFlags(throwItem, ItemFlag.HIDE_ENCHANTS);
		eatItem = makeItem(Material.APPLE, ChatColor.GREEN + "Disable Item Edibility");
		modifyLore(eatItem, ChatColor.YELLOW + "The player will (not) be able to eat that item");
		modifyItemFlags(eatItem, ItemFlag.HIDE_ENCHANTS);
		placeItem = makeItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Disable Item Placement");
		modifyLore(placeItem, ChatColor.YELLOW + "The player will (not) be able to place that item");
		modifyItemFlags(placeItem, ItemFlag.HIDE_ENCHANTS);
		equipItem = makeItem(Material.GOLDEN_LEGGINGS, ChatColor.GREEN + "Disable Item is equippable");
		modifyLore(equipItem, ChatColor.YELLOW + "The player will (not) be able to equip that item on armor slots on himself or on armorstands");
		modifyItemFlags(equipItem, ItemFlag.HIDE_ENCHANTS);
		destroyItem = makeItem(Material.CACTUS, ChatColor.GREEN + "Disable Item can be destroyed");
		modifyLore(destroyItem, ChatColor.YELLOW + "The player will (not) be able to destroy that item");
		modifyItemFlags(destroyItem, ItemFlag.HIDE_ENCHANTS);
		repairItem = makeItem(Material.ANVIL, ChatColor.GREEN + "Disable Item can be repaired");
		modifyLore(repairItem, ChatColor.YELLOW + "The player will (not) be able to repair that item in any way");
		modifyItemFlags(repairItem, ItemFlag.HIDE_ENCHANTS);
		enchantItem = makeItem(Material.ENCHANTING_TABLE, ChatColor.GREEN + "Disable Item can be enchanted or enchanted with");
		modifyLore(enchantItem, ChatColor.YELLOW + "The player will (not) be able to enchant that item or with that item");
		modifyItemFlags(enchantItem, ItemFlag.HIDE_ENCHANTS);
		disenchantItem = makeItem(Material.GRINDSTONE, ChatColor.GREEN + "Disable Item can be disenchanted");
		modifyLore(disenchantItem, ChatColor.YELLOW + "The player will (not) be able to disenchant that item");
		modifyItemFlags(disenchantItem, ItemFlag.HIDE_ENCHANTS);
		upgradeItem = makeItem(Material.NETHERITE_INGOT, ChatColor.GREEN + "Disable Item is upgradable");
		modifyLore(upgradeItem, ChatColor.YELLOW + "The player will (not) be able to use a smithing table to upgrade the item");
		modifyItemFlags(upgradeItem, ItemFlag.HIDE_ENCHANTS);
		renameItem = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Disable Item is renamable");
		modifyLore(renameItem, ChatColor.YELLOW + "The player will (not) be able to rename the item");
		modifyItemFlags(renameItem, ItemFlag.HIDE_ENCHANTS);

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
		modifyLore(toMin, ChatColor.YELLOW + "Sets the absolute minimum of the given value");
		modifyToCustomHead(toMin, SkullData.REDSTONE_M);
		toMax = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Maximum");
		modifyLore(toMax, ChatColor.YELLOW + "Sets the absolute maximum of the given value");
		modifyToCustomHead(toMax, SkullData.LIME_M);
		customValue = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Custom");
		modifyLore(customValue, ChatColor.YELLOW + "Set the value by directly typing it");
		toDefault = makeItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, ChatColor.GREEN + "Default Max. Stack Size");
		modifyLore(toDefault, ChatColor.YELLOW + "Sets the items amount to its natural default maximum stack size");

		paginatedMenuLeft = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Left");
		modifyLore(paginatedMenuLeft, ChatColor.YELLOW + "Go back a page");
		modifyToCustomHead(paginatedMenuLeft, SkullData.QUARTZ_ARROW_LEFT);
		paginatedMenuRight = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Right");
		modifyLore(paginatedMenuRight, ChatColor.YELLOW + "Go forward a page");
		modifyToCustomHead(paginatedMenuRight, SkullData.QUARTZ_ARROW_RIGHT);
		closeInventory = makeItem(Material.PLAYER_HEAD, ChatColor.DARK_RED + "Close");
		modifyLore(closeInventory, ChatColor.YELLOW + "Close the inventory");
		modifyToCustomHead(closeInventory, SkullData.REDSTONE_BLOCK_ARROW_LEFT);
		backInventory = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Back");
		modifyLore(backInventory, ChatColor.YELLOW + "Go back to the parent inventory");
		modifyToCustomHead(backInventory, SkullData.REDSTONE_BLOCK_LEFT);

		itemIdentity = makeItem(Material.ARMOR_STAND, ChatColor.GREEN + "Change Item Identity");
		modifyLore(itemIdentity, ChatColor.YELLOW + "Manages the items identity settings");

		editItemImmutability = makeItem(Material.BEDROCK, ChatColor.GREEN + "Change Item Mutability");
		modifyLore(editItemImmutability, ChatColor.YELLOW + "Toggles whether the item can be modified", ChatColor.YELLOW + "out of the bounds of this plugin");
		modifyItemFlags(editItemImmutability, ItemFlag.HIDE_ENCHANTS);

		openItemUniquenessSettings = makeItem(Material.NETHER_STAR, ChatColor.GREEN + "Item Uniqueness Settings");
		modifyLore(openItemUniquenessSettings,
			ChatColor.YELLOW + "Copy or change the items unique identifier", "",
			ChatColor.GRAY + "Current value:",
			ChatColor.GRAY + "{currentValue}");
		copyUniqueIdentifier = makeItem(Material.PAPER, ChatColor.GREEN + "Copy");
		modifyLore(copyUniqueIdentifier, ChatColor.YELLOW + "Copy the unique identifier to the clipboard");
		itemUniquenessSettingsShowID = makeItem(Material.NAME_TAG, ChatColor.GREEN + "Current Value" + ChatColor.GRAY + ":");
		modifyLore(itemUniquenessSettingsShowID, ChatColor.GRAY + "{currentValue}");
		editUniqueIdentifier = makeItem(Material.WRITABLE_BOOK, ChatColor.GREEN + "Item Uniqueness Settings");
		modifyLore(editUniqueIdentifier, ChatColor.YELLOW + "Edit the unique identifier according to your requirements");

		activatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Activated Enchantments");
		modifyLore(activatedEnchantments, ChatColor.YELLOW + "Display all active enchantments on the current item");
		modifyToCustomHead(activatedEnchantments, SkullData.QUARTZ_CHECK);
		deactivatedEnchantments = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Deactivated Enchantments");
		modifyLore(deactivatedEnchantments, ChatColor.YELLOW + "Display all inactive enchantments on the current item");
		modifyToCustomHead(deactivatedEnchantments, SkullData.QUARTZ_X);

		activatedArmorEffects = makeItem(Material.PLAYER_HEAD, ChatColor.GREEN + "Activated ArmorEffects");
		modifyLore(activatedArmorEffects, ChatColor.YELLOW + "Display all active effects on the current armor");
		modifyToCustomHead(activatedArmorEffects, SkullData.QUARTZ_CHECK);
		deactivatedArmorEffects = makeItem(Material.PLAYER_HEAD, ChatColor.RED + "Deactivated ArmorEffects");
		modifyLore(deactivatedArmorEffects, ChatColor.YELLOW + "Display all inactive effects on the current armor");
		modifyToCustomHead(deactivatedArmorEffects, SkullData.QUARTZ_X);

		DEFAULT_dye = makeItem(Material.PAPER, ChatColor.WHITE + "No Color");
		modifyLore(DEFAULT_dye, ChatColor.YELLOW + "Sets the items type to its natural, uncolored variant");
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
		modifyLore(RED_CAP,
			ChatColor.YELLOW + "Represents the red component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(RED_CAP, Color.fromRGB(255, 0, 0));
		modifyItemFlags(RED_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		GREEN_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Green");
		modifyLore(GREEN_CAP, ChatColor.YELLOW + "Represents the green component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(GREEN_CAP, Color.fromRGB(0, 255, 0));
		modifyItemFlags(GREEN_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		BLUE_CAP = makeItem(Material.LEATHER_HELMET, ChatColor.RED + "Blue");
		modifyLore(BLUE_CAP, ChatColor.YELLOW + "Represents the blue component in the RGB value", "",
			ChatColor.GRAY + "Current value (0-255):",
			ChatColor.GRAY + "{currentValue}"
		);
		modifyColor(BLUE_CAP, Color.fromRGB(0, 0, 255));
		modifyItemFlags(BLUE_CAP, ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);

		resetBackLeatherItem = makeItem(Material.LEATHER_BOOTS, ChatColor.RED + "Reset Color to Start");
		modifyLore(resetBackLeatherItem, ChatColor.YELLOW + "Resets the item to the variant it was when editing began");
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

	public static void modifyColor(ItemStack stack, Color color) {
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) stack.getItemMeta();
		itemMeta.setColor(color);
		stack.setItemMeta(itemMeta);
	}

	private static void modifySpecificBannerPattern(ItemStack stack) {
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
