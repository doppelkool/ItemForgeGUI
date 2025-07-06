package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public abstract class CustomFlagManager<T extends Enum<T>> {

	protected final Map<Integer, Pair<T, ItemStack>> slotToFlag = new HashMap<>();

	protected abstract NamespacedKey getNameSpacedKey();

	protected abstract Class<T> getDeclaringClass();

	public ArrayList<T> mapItemFlags(PersistentDataContainer dataContainer) {
		String flags = extractItemFlags(dataContainer);

		if (flags.isEmpty()) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				flags.split(";"))
			.map(s -> Enum.valueOf(getDeclaringClass(), s.trim()))
			.collect(Collectors.toCollection(ArrayList::new));
	}

	public void toggleItemFlag(ItemStack itemStack, T itemFlag, boolean newStatus) {
		ArrayList<T> itemFlags = mapItemFlags(itemStack.getItemMeta().getPersistentDataContainer());

		//Already applied status
		boolean itemFlagAlreadyApplied = itemFlags.contains(itemFlag);
		if ((itemFlagAlreadyApplied && newStatus) || (!itemFlagAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			itemFlags.add(itemFlag);
		} else {
			itemFlags.remove(itemFlag);
		}

		applyFlagToItemStack(itemStack, itemFlags);
	}

	public void toggleItemFlag(Block block, T itemFlag, boolean newStatus) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		ArrayList<T> itemFlags = mapItemFlags(customBlockData);

		boolean itemFlagAlreadyApplied = itemFlags.contains(itemFlag);
		if ((itemFlagAlreadyApplied && newStatus) || (!itemFlagAlreadyApplied && !newStatus)) {
			return;
		}

		if (newStatus) {
			itemFlags.add(itemFlag);
		} else {
			itemFlags.remove(itemFlag);
		}

		applyFlagToBlock(block, itemFlags);
	}

	private void applyFlagToItemStack(ItemStack itemStack, List<T> itemFlag) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.getPersistentDataContainer()
			.set(getNameSpacedKey(),
				PersistentDataType.STRING,
				serializeItemFlags(itemFlag));
		itemStack.setItemMeta(itemMeta);
	}

	private void applyFlagToBlock(Block block, List<T> itemFlag) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		customBlockData
			.set(getNameSpacedKey(),
				PersistentDataType.STRING,
				serializeItemFlags(itemFlag));
	}

	private String serializeItemFlags(List<T> itemFlag) {
		return itemFlag
			.stream().map(T::toString)
			.collect(Collectors.joining(";"));
	}

	public String extractItemFlags(PersistentDataContainer dataContainer) {
		if (!dataContainer.has(getNameSpacedKey(), PersistentDataType.STRING)) {
			return "";
		}

		String s = dataContainer.get(getNameSpacedKey(), PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public boolean isFlagApplied(@NotNull PersistentDataContainer dataContainer, @NotNull T action) {
		return mapItemFlags(dataContainer)
			.contains(action);
	}

	public boolean isFlagApplied(@Nullable ItemStack item, @NotNull T action) {
		return item != null
			&& item.getItemMeta() != null
			&& UniqueItemIdentifierManager.isUniqueItem(item)
			&& isFlagApplied(item.getItemMeta().getPersistentDataContainer(), action);
	}


}
