package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlag;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ItemInfoManager {

	private final ItemStack item;
	private final ItemMeta meta;

	private static final String SEPARATOR = ChatColor.DARK_GRAY + "―――";
	private static final String HEADER_COLOR = ChatColor.GRAY + "" + ChatColor.UNDERLINE;
	private static final String ENTRY_COLOR = ChatColor.GRAY + "";
	private static final String ITEM_FLAGS_HEADER = HEADER_COLOR + "Item Flags";
	private static final String ARMOR_EFFECTS_HEADER = HEADER_COLOR + "Armor Effects";
	private static final String PREVENTION_FLAGS_HEADER = HEADER_COLOR + "Prevention Flags";

	private final List<String> itemInfoLines;

	@Getter
	private List<String> itemLore;
	@Getter
	private List<String> itemFlags;
	@Getter
	private List<String> armorEffects;
	@Getter
	private List<String> preventionFlags;

	public ItemInfoManager(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		this.itemInfoLines = (meta != null && meta.hasLore()) ? meta.getLore() : new ArrayList<>();

		parseLore();
	}

	private void parseLore() {
		this.itemLore = new ArrayList<>();
		this.itemFlags = new ArrayList<>();
		this.armorEffects = new ArrayList<>();
		this.preventionFlags = new ArrayList<>();

		for (String itemInfoLine : itemInfoLines) {
			String trimmed = itemInfoLine.trim();

			if (trimmed.equals(SEPARATOR)) {
				break;
			}

			itemLore.add(trimmed);
		}

		loadItemFlags(item);
		loadArmorEffects(item);
		loadPreventionFlags(item);

		//Remove the seperator
		itemLore.remove(SEPARATOR);
	}

	public void initItemDescription() {
		loadItemFlags(item);
		loadArmorEffects(item);
		loadPreventionFlags(item);

		updateItemInfo();
	}

	/**
	 * Loads Item Flags and ensures "Item Flags: Empty" is shown if none exist.
	 */
	private void loadItemFlags(ItemStack item) {
		itemFlags = new ArrayList<>();
		List<ItemFlag> flags = new ArrayList<>(item.getItemMeta().getItemFlags());

		if (flags.isEmpty()) {
			return;
		}

		itemFlags.add(ITEM_FLAGS_HEADER);
		for (ItemFlag effect : flags) {
			itemFlags.add(ENTRY_COLOR + "- " + effect.name());
		}
	}

	/**
	 * Loads Armor Effects and ensures "Armor Effects: Empty" is shown if none exist.
	 */
	private void loadArmorEffects(ItemStack item) {
		armorEffects = new ArrayList<>();
		Map<PotionEffectType, Integer> effects = ArmorEffectManager.getAllActivatedPotionEffectTypesAsMap(item);

		if (effects.isEmpty()) {
			return;
		}

		armorEffects.addFirst(ARMOR_EFFECTS_HEADER);
		for (Map.Entry<PotionEffectType, Integer> effect : effects.entrySet()) {
			String formattedEffectType = ItemStackHelper.formatCAPSName(effect.getKey().getTranslationKey());
			armorEffects.add(ENTRY_COLOR + "- " + formattedEffectType + " " + effect.getValue());
		}
	}

	/**
	 * Loads Prevention Flags and ensures "Prevention Flags: Empty" is shown if none exist.
	 */
	private void loadPreventionFlags(ItemStack item) {
		preventionFlags = new ArrayList<>();
		List<ForgeAction> flags = PreventionFlagManager.getInstance().mapItemFlags(item.getItemMeta().getPersistentDataContainer());

		if (flags.isEmpty()) {
			return;
		}

		preventionFlags.add(PREVENTION_FLAGS_HEADER);
		for (ForgeAction action : flags) {

			String loreDescription = action.getLoreDescription();

			if (action == ForgeAction.CRAFT) {
				PreventionFlagManager.CraftingPreventionFlag activeCraftingPrevention = PreventionFlagManager.getInstance().getActiveCraftingPrevention(item);
				loreDescription += " (" + activeCraftingPrevention.getItemDescription() + ")";
			}

			preventionFlags.add(ENTRY_COLOR + "- " + loreDescription);
		}
	}

	public ItemInfoManager setItemLore(List<String> itemLore) {
		this.itemLore = itemLore;
		return this;
	}

	public void updateItemInfo() {
		List<String> updatedLore = new ArrayList<>();

		// Add user lore if it exists
		if (!itemLore.isEmpty()) {
			updatedLore.addAll(itemLore);
		}

		enforceHideFlag(updatedLore);

		// Update the item meta
		meta.setLore(updatedLore);
		item.setItemMeta(meta);
	}

	private void enforceHideFlag(List<String> updatedLore) {
		PersistentDataContainer container = this.item.getItemMeta().getPersistentDataContainer();

		boolean showItemFlags = !CustomItemFlagManager.getInstance().isFlagApplied(container, CustomItemFlag.HIDE_ITEM_FLAGS);
		boolean showArmorEffects = !CustomItemFlagManager.getInstance().isFlagApplied(container, CustomItemFlag.HIDE_ARMOR_EFFECTS);
		boolean showPreventionFlags = !CustomItemFlagManager.getInstance().isFlagApplied(container, CustomItemFlag.HIDE_PREVENTION_FLAGS);

		if (showItemFlags || showArmorEffects || showPreventionFlags) {
			updatedLore.add(SEPARATOR);

			if (showItemFlags) {
				updatedLore.addAll(itemFlags);
			}
			if (showArmorEffects) {
				updatedLore.addAll(armorEffects);
			}
			if (showPreventionFlags) {
				updatedLore.addAll(preventionFlags);
			}
		}
	}

}

