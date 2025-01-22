package de.doppelkool.itemforgegui.Main.MenuItems;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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
}
