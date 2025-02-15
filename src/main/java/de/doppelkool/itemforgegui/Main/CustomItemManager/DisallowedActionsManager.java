package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DisallowedActionsManager {
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

	public static ArrayList<ForgeAction> mapNotAllowedForgeActions(PersistentDataHolder dataHolder) {
		String notAllowedForgeActions = getNotAllowedForgeActions(dataHolder);

		if(notAllowedForgeActions.isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				notAllowedForgeActions.split(";"))
			.map(type -> ForgeAction.valueOf(type.trim()))
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void toggleAllowedAction(ItemStack itemStack, ForgeAction forgeAction, boolean newStatus) {
		ArrayList<ForgeAction> forgeActions = mapNotAllowedForgeActions(itemStack.getItemMeta());

		//Already applied status
		boolean forgeActionAlreadyApplied = forgeActions.contains(forgeAction);
		if ((forgeActionAlreadyApplied && newStatus) || (!forgeActionAlreadyApplied && !newStatus)) {
			return;
		}

		if(newStatus) {
			forgeActions.add(forgeAction);
		} else {
			forgeActions.remove(forgeAction);
		}

		applyActionsToItemStack(itemStack, forgeActions);
		Logger.log(itemStack);
	}

	private static void applyActionsToItemStack(ItemStack itemStack, List<ForgeAction> forgeActions) {
		ItemMeta itemMeta = itemStack
			.getItemMeta();
		itemMeta.getPersistentDataContainer()
			.set(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(),
				PersistentDataType.STRING,
				serializeForgeActions(forgeActions));
		itemStack.setItemMeta(itemMeta);
	}

	private static String serializeForgeActions(List<ForgeAction> forgeActions) {
		return forgeActions
			.stream().map(ForgeAction::toString)
			.collect(Collectors.joining(";"));
	}

	public static String getNotAllowedForgeActions(PersistentDataHolder dataHolder) {
		PersistentDataContainer persistentDataContainer = dataHolder.getPersistentDataContainer();
		if (!persistentDataContainer.has(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING)) {
			return "";
		}

		String s = persistentDataContainer.get(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static boolean isActionPrevented(@NotNull PersistentDataHolder dataHolder, @NotNull ForgeAction action) {
		return mapNotAllowedForgeActions(dataHolder)
			.contains(action);
	}

	public static boolean isActionPrevented(@Nullable ItemStack item, @NotNull ForgeAction action) {
		return item != null
			&& item.getItemMeta() != null
			&& UniqueItemIdentifierManager.isUniqueItem(item)
			&& isActionPrevented(item.getItemMeta(), action);
	}
}
