package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
	String SEPARATOR = ChatColor.DARK_GRAY + "―――";
	String HEADER_COLOR = ChatColor.GRAY + "" + ChatColor.UNDERLINE;
	String ENTRY_COLOR = ChatColor.GRAY + "";
	String ARMOR_EFFECTS_HEADER = HEADER_COLOR + "Armor Effects";
	String EMPTY_ARMOR_EFFECTS_HEADER = ENTRY_COLOR + "Armor Effects: Empty";
	String PREVENTION_FLAGS_HEADER = HEADER_COLOR + "Prevention Flags";
	String EMPTY_PREVENTION_FLAGS_HEADER = ENTRY_COLOR + "Prevention Flags: Empty";
	private List<String> itemInfoLines;

	@Getter
	private List<String> itemLore;
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
		this.armorEffects = new ArrayList<>();
		this.preventionFlags = new ArrayList<>();

		for (String itemInfoLine : itemInfoLines) {
			String trimmed = itemInfoLine.trim();

			if (trimmed.equals(SEPARATOR)) {
				break;
			}

			itemLore.add(trimmed);
		}

		loadArmorEffects(item);
		loadPreventionFlags(item);

		//Remove the seperator
		itemLore.remove(SEPARATOR);
	}

	public void initItemDescription() {
		loadArmorEffects(item);
		loadPreventionFlags(item);

		updateItemInfo();
	}

	/**
	 * Loads Armor Effects and ensures "Armor Effects: Empty" is shown if none exist.
	 */
	private void loadArmorEffects(ItemStack item) {
		armorEffects = new ArrayList<>();
		Map<PotionEffectType, Integer> effects = ArmorEffectManager.getAllActivatedPotionEffectTypesAsMap(item);

		if (effects.isEmpty()) {
			armorEffects.add(EMPTY_ARMOR_EFFECTS_HEADER);
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
		List<ForgeAction> flags = PreventionFlagManager.mapNotAllowedForgeActions(item.getItemMeta().getPersistentDataContainer());

		if (flags.isEmpty()) {
			preventionFlags.add(EMPTY_PREVENTION_FLAGS_HEADER);
			return;
		}

		preventionFlags.add(PREVENTION_FLAGS_HEADER);
		for (ForgeAction action : flags) {

			String loreDescription = action.getLoreDescription();

			if (action == ForgeAction.CRAFT) {
				PreventionFlagManager.CraftingPrevention activeCraftingPrevention = PreventionFlagManager.getActiveCraftingPrevention(item);
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
		updatedLore.add(SEPARATOR);

		updatedLore.addAll(armorEffects);
		updatedLore.addAll(preventionFlags);

		// Update the item meta
		meta.setLore(updatedLore);
		item.setItemMeta(meta);
	}
}

