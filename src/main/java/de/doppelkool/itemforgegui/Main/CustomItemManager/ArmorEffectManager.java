package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuItems.PotionEffectStacks;
import de.doppelkool.itemforgegui.Main.Resources;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ArmorEffectManager {

	public static void initPDCVariable(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();

		if (!itemMeta.getPersistentDataContainer().has(Main.getPlugin().getCustomArmorEffectsKey())) {
			itemMeta.getPersistentDataContainer().set(
				Main.getPlugin().getCustomArmorEffectsKey(),
				Main.getPlugin().getCustomArmorEffectListDataType(),
				new ArrayList<>());

			item.setItemMeta(itemMeta);
		}
	}

	public static ArrayList<PotionEffectType> getAllDeactivatedPotionEffectTypes(ItemStack is) {
		ArrayList<PotionEffectType> list = new ArrayList<>();

		Map<PotionEffectType, Integer> activatedPotionEffectTypes = getAllActivatedPotionEffectTypesAsMap(is);

		if(activatedPotionEffectTypes.isEmpty()) {
			return new ArrayList<>(PotionEffectStacks.potionEffectTypeToItemStack.keySet().stream().toList());
		}

		for (PotionEffectType potionEffectType : PotionEffectStacks.potionEffectTypeToItemStack.keySet()) {
			if (activatedPotionEffectTypes.get(potionEffectType) == null) {
				list.add(potionEffectType);
			}
		}

		return list;
	}

	public static boolean hasArmorEffects(ItemStack itemToBeEnchanted) {
		return !getAllActivatedPotionEffectTypesAsMap(itemToBeEnchanted).isEmpty();
	}

	public static Integer getArmorEffect(ItemStack itemToBeEnchanted, PotionEffectType potionEffectToEdit) {
		return getAllActivatedPotionEffectTypesAsMap(itemToBeEnchanted).get(potionEffectToEdit);
	}

	public static ArrayList<ForgeArmorEffect> getAllActivatedPotionEffectTypesAsList(ItemStack is) {
		if(is == null || is.getItemMeta() == null) {
			return new ArrayList<>();
		}

		ArrayList<ForgeArmorEffect> forgeArmorEffects = is.getItemMeta()
			.getPersistentDataContainer()
			.get(
				Main.getPlugin().getCustomArmorEffectsKey(),
				Main.getPlugin().getCustomArmorEffectListDataType());

		return forgeArmorEffects == null || forgeArmorEffects.isEmpty()
			? new ArrayList<>()
			: forgeArmorEffects;
	}

	public static Map<PotionEffectType, Integer> getAllActivatedPotionEffectTypesAsMap(ItemStack is) {
		ArrayList<ForgeArmorEffect> forgeArmorEffects = getAllActivatedPotionEffectTypesAsList(is);

		if(forgeArmorEffects.isEmpty()) {
			return new HashMap<>();
		}

		return forgeArmorEffects.stream()
			.collect(Collectors.toMap(
				ForgeArmorEffect::getType,
				ForgeArmorEffect::getAmplifier
			));
	}

	public static void addArmorEffect(ItemStack itemToBeEnchanted, PotionEffectType potionEffectToEdit, int strength) {
		ArrayList<ForgeArmorEffect> effects = getAllActivatedPotionEffectTypesAsList(itemToBeEnchanted);

		ForgeArmorEffect foundToChange = null;
		for(ForgeArmorEffect effect : effects) {
			if(effect.getPotionEffect().getType().equals(potionEffectToEdit)) {
				foundToChange = effect;
			}
		}

		if(foundToChange != null) {
			effects.remove(foundToChange);
			foundToChange.setAmplifier(strength);
			effects.add(foundToChange);
		} else {
			effects.add(new ForgeArmorEffect(potionEffectToEdit, strength));
		}

		ItemMeta itemMeta = itemToBeEnchanted.getItemMeta();
		itemMeta.getPersistentDataContainer().set(Main.getPlugin().getCustomArmorEffectsKey(),
			Main.getPlugin().getCustomArmorEffectListDataType(),
			effects);
		itemToBeEnchanted.setItemMeta(itemMeta);
	}

	public static void removeArmorEffect(ItemStack itemToBeEnchanted, PotionEffectType potionEffectToEdit) {
		ArrayList<ForgeArmorEffect> effects = getAllActivatedPotionEffectTypesAsList(itemToBeEnchanted);

		ForgeArmorEffect foundToRemove = null;
		for(ForgeArmorEffect effect : effects) {
			if (!effect.getPotionEffect().getType().equals(potionEffectToEdit)) {
				continue;
			}

			foundToRemove = effect;
			break;
		}

		if (foundToRemove == null) {
			return;
		}

		effects.remove(foundToRemove);

		ItemMeta itemMeta = itemToBeEnchanted.getItemMeta();
		itemMeta.getPersistentDataContainer().set(Main.getPlugin().getCustomArmorEffectsKey(),
			Main.getPlugin().getCustomArmorEffectListDataType(),
			effects);
		itemToBeEnchanted.setItemMeta(itemMeta);
	}

	/**
	 * Re-adds all armor-provided effects to the player.
	 * Uses -1 as our custom duration marker to indicate an armor-applied effect.
	 */
	public static void reapplyArmorEffects(Player player) {
		// Calculate the best (highest amplifier) armor effects from equipped items.
		Map<PotionEffectType, Integer> armorEffects = getBestArmorEffects(player);

		// Reapply each armor effect with our custom marker (-1).
		for (Map.Entry<PotionEffectType, Integer> entry : armorEffects.entrySet()) {
			PotionEffectType type = entry.getKey();
			int amplifier = entry.getValue() - 1; // (same as UnEquipEffectArmorListener) 0 based effects on minecraft end
			player.addPotionEffect(new PotionEffect(type, -1, amplifier, false, false));
		}
	}

	/**
	 * Loops through the player's armor and picks the highest amplifier for each potion effect.
	 */
	public static Map<PotionEffectType, Integer> getBestArmorEffects(Player player) {
		Map<PotionEffectType, Integer> bestEffects = new HashMap<>();
		for (ItemStack armor : player.getInventory().getArmorContents()) {
			if (armor == null) continue;
			// Retrieve armor effects from the item's Persistent Data Container using your helper
			Map<PotionEffectType, Integer> pieceEffects = getAllActivatedPotionEffectTypesAsMap(armor);
			for (Map.Entry<PotionEffectType, Integer> entry : pieceEffects.entrySet()) {
				bestEffects.merge(entry.getKey(), entry.getValue(), Math::max);
			}
		}
		return bestEffects;
	}

	public static boolean isCappedEffect(PotionEffectType potionEffectType) {
		return Resources.CAPPED_POTION_EFFECT_TYPES.contains(potionEffectType);
	}
}
