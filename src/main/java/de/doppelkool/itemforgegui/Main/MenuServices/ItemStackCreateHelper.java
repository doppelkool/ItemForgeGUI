package de.doppelkool.itemforgegui.Main.MenuServices;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SkullData;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
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
		modifyLore(itemStack, List.of(lore));
	}

	public static void modifyLore(ItemStack itemStack, List<String> lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
	}

	public enum LoreVariable {
		CURRENT_VALUE("currentValue"),
		CURRENT_ATTRIBUTE("currentAttribute"),
		MISSING_VALUE("warning_valueMissing"),
		CURRENT_OPERATION("currentOperation"),
		CURRENT_VALUE__ADD_NUMBER("currentValue_addNumber"),
		CURRENT_VALUE__ADD_SCALAR("currentValue_addScalar"),
		CURRENT_VALUE__MULTIPLY_SCALAR_1("currentValue_multiplyScalar1"),
		CURRENT_OPERATION_EXPLANATION("currentOperationExplanation"),
		CURRENT_OPERATION_EXAMPLE("currentOperationExample"),
		OPERATION_VALUE("operationValue"),

		;

		private final String loreVariable;

		LoreVariable(String loreVariable) {
			this.loreVariable = loreVariable;
		}
	}

	public static void modifyCurrentValueVariableInDisplayName(ItemStack itemToChangeLore, LoreVariable variableString, String currentValue) {
		ItemMeta itemMeta = itemToChangeLore.getItemMeta();
		String futureDisplayName = itemMeta.getDisplayName();
		futureDisplayName = futureDisplayName.replace("{" + variableString.loreVariable + "}", currentValue);
		itemMeta.setDisplayName(futureDisplayName);
		itemToChangeLore.setItemMeta(itemMeta);
	}

	public static void modifyCurrentValueVariableInLore(ItemStack itemToChangeLore, LoreVariable variableString, String currentValue) {
		ItemMeta itemMeta = itemToChangeLore.getItemMeta();
		List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
		lore.replaceAll(s -> s.replace("{" + variableString.loreVariable + "}", currentValue));

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

	public static void modifyStoredSlot(ItemStack itemStack, EquipmentSlot toBeStored) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.getPersistentDataContainer().set(
			Main.getPlugin().getCustomEquipmentSlotIDKey(),
			PersistentDataType.STRING,
			toBeStored.name()
		);

		itemStack.setItemMeta(itemMeta);
	}

	public static void modifyAttributeStringInPDC(ItemStack itemStack, Attribute toBeStored) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		itemMeta.getPersistentDataContainer().set(
			Main.getPlugin().getCustomAttributeModifierKey_AttributeString(),
			PersistentDataType.STRING,
			toBeStored.getKey().getKey()
		);

		itemStack.setItemMeta(itemMeta);
	}

	public static void modifyPotionType(ItemStack fireProtectionItem, PotionType potionType) {
		PotionMeta potionMeta = (PotionMeta) fireProtectionItem.getItemMeta();
		potionMeta.setBasePotionType(potionType);
		fireProtectionItem.setItemMeta(potionMeta);
	}

	/**
	 * @implNote Only give leather items, or items which metas are of type LeatherArmorMeta
	 *
	 * @return Color of the leather item
	 */
	public static Color getColorOfLeatherItem(ItemStack leatherArmorMeta) {
		return ((ColorableArmorMeta)leatherArmorMeta.getItemMeta()).getColor();
	}

	public static void modifyColor(ItemStack stack, Color color) {
		ColorableArmorMeta itemMeta = (ColorableArmorMeta) stack.getItemMeta();
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

	public static void modifyToCustomHead(ItemStack skull, SkullData skullData) {
		try {
			SkullMeta meta = (SkullMeta) skull.getItemMeta();

			PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID(), "CustomHead");

			PlayerTextures textures = profile.getTextures();
			textures.setSkin(new URL(SkullData.extractSkinUrl(skullData.getBase64encoded())));
			profile.setTextures(textures);

			meta.setOwnerProfile(profile);
			skull.setItemMeta(meta);
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

	public static Set<EquipmentSlotGroup> getEquippableSlotGroups(ItemStack item) {
		Set<EquipmentSlotGroup> slots = new HashSet<>(Set.of(
			EquipmentSlotGroup.MAINHAND,
			EquipmentSlotGroup.OFFHAND
		));

		Material type = item.getType();
		String name = type.name();

		// HEAD
		if (name.endsWith("_HELMET")
			|| type == Material.CARVED_PUMPKIN
			|| type == Material.PLAYER_HEAD
			|| type == Material.CREEPER_HEAD
			|| type == Material.DRAGON_HEAD
			|| type == Material.SKELETON_SKULL
			|| type == Material.WITHER_SKELETON_SKULL
			|| type == Material.ZOMBIE_HEAD) {
			slots.add(EquipmentSlotGroup.HEAD);
		}

		// CHEST
		if (name.endsWith("_CHESTPLATE") || type == Material.ELYTRA) {
			slots.add(EquipmentSlotGroup.CHEST);
		}

		// LEGS
		if (name.endsWith("_LEGGINGS")) {
			slots.add(EquipmentSlotGroup.LEGS);
		}

		// FEET
		if (name.endsWith("_BOOTS")) {
			slots.add(EquipmentSlotGroup.FEET);
		}

		return slots;
	}

	public static ItemStack fillEditValueItemWithLoreValues(SlotItemWrapper.SlotItemOperationValueEdit slotItem, Double currentValueForOperation) {
		ItemStack item = slotItem.item().clone();

		String loreValueRepresent;
		if (currentValueForOperation != null) {
			if(currentValueForOperation > 0) {
				loreValueRepresent = ChatColor.GREEN + currentValueForOperation.toString();
			} else {
				loreValueRepresent = ChatColor.RED + currentValueForOperation.toString();
			}
		} else {
			loreValueRepresent = ChatColor.GRAY + "0";
		}

		String operationValueRepresent = ItemStackModifyHelper.formatCAPSNames(slotItem.operation().name()) + ChatColor.GRAY + ": ";
		String exampleLoreExtension;
		if(slotItem.valueEdit() > 0) {
			exampleLoreExtension = ChatColor.GREEN + "+" + slotItem.valueEdit();
		} else {
			exampleLoreExtension = ChatColor.RED + "" + slotItem.valueEdit();
		}

		ItemStackCreateHelper.modifyCurrentValueVariableInDisplayName(item, LoreVariable.CURRENT_OPERATION_EXAMPLE, operationValueRepresent + exampleLoreExtension);
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE, loreValueRepresent);
		return item;
	}

	public static ItemStack fillInfoBookItemWithValues(SlotItemWrapper.SlotItemOperationExplanationValueEdit infoBook, Double currentValueForOperation) {
		ItemStack item = infoBook.item().clone();

		AttributeModifier.Operation operation = infoBook.operation();
		String operationValueRepresent = ItemStackModifyHelper.formatCAPSNames(operation.name());

		String loreValueRepresent;
		if (currentValueForOperation != null) {
			loreValueRepresent = currentValueForOperation.toString();
			ItemStackModifyHelper.setGlow(item, true);
		} else {
			loreValueRepresent = ChatColor.GRAY + "-";
		}

		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_OPERATION, operationValueRepresent);
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_VALUE, loreValueRepresent);
		ItemStackCreateHelper.modifyCurrentValueVariableInLore(item, ItemStackCreateHelper.LoreVariable.CURRENT_OPERATION_EXPLANATION, infoBook.explanationLoreString().get());

		return item;
	}
}
