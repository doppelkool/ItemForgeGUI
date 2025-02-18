package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DisallowedInvsManager {
	public static List<InventoryType> mapNotAllowedInventoryTypes(ItemStack itemStack) {
		PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
		if (!persistentDataContainer.has(
			Main.getPlugin().getCustomTagItemNotAllowedInInvType())) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				persistentDataContainer.get(Main.getPlugin().getCustomTagItemNotAllowedInInvType(), PersistentDataType.STRING)
					.split(";"))
			.map(type -> InventoryType.valueOf(type.trim()))
			.toList();
	}
}
