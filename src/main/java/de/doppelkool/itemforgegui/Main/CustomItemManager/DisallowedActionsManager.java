package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.block.Block;
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

	public static ArrayList<ForgeAction> mapNotAllowedForgeActions(PersistentDataContainer dataContainer) {
		String notAllowedForgeActions = getNotAllowedForgeActions(dataContainer);

		if(notAllowedForgeActions.isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				notAllowedForgeActions.split(";"))
			.map(type -> ForgeAction.valueOf(type.trim()))
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public static void toggleAllowedAction(ItemStack itemStack, ForgeAction forgeAction, boolean newStatus) {
		ArrayList<ForgeAction> forgeActions = mapNotAllowedForgeActions(itemStack.getItemMeta().getPersistentDataContainer());

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
	}

	public static void toggleAllowedAction(Block block, ForgeAction forgeAction, boolean newStatus) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		ArrayList<ForgeAction> forgeActions = mapNotAllowedForgeActions(customBlockData);

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

		applyActionsToItemStack(block, forgeActions);
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

	private static void applyActionsToItemStack(Block block, List<ForgeAction> forgeActions) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		customBlockData
			.set(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(),
				PersistentDataType.STRING,
				serializeForgeActions(forgeActions));
	}

	private static String serializeForgeActions(List<ForgeAction> forgeActions) {
		return forgeActions
			.stream().map(ForgeAction::toString)
			.collect(Collectors.joining(";"));
	}

	public static String getNotAllowedForgeActions(PersistentDataContainer dataContainer) {
		if (!dataContainer.has(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING)) {
			return "";
		}

		String s = dataContainer.get(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static boolean isActionPrevented(@NotNull PersistentDataHolder dataHolder, @NotNull ForgeAction action) {
		return isActionPrevented(dataHolder.getPersistentDataContainer(), action);
	}

	public static boolean isActionPrevented(@NotNull PersistentDataContainer dataContainer, @NotNull ForgeAction action) {
		return mapNotAllowedForgeActions(dataContainer)
			.contains(action);
	}

	public static boolean isActionPrevented(@Nullable ItemStack item, @NotNull ForgeAction action) {
		return item != null
			&& item.getItemMeta() != null
			&& UniqueItemIdentifierManager.isUniqueItem(item)
			&& isActionPrevented(item.getItemMeta(), action);
	}
}
