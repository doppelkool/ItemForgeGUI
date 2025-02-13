package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.Main;
import jline.internal.Nullable;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class UniqueItemIdentifierManager {

	public static String getOrSetUniqueItemIdentifier(ItemStack itemStack) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();

		String uniqueId;
		if (persistentDataContainer.has(Main.getPlugin().getCustomTagUID())) {
			uniqueId = getUniqueItemIdentifierOrEmptyString(itemMeta); //Empty String case not possible without prior errors
		} else {
			uniqueId = UUID.randomUUID().toString();
			itemMeta.getPersistentDataContainer().set(
				Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING,
				uniqueId);
		}

		itemStack.setItemMeta(itemMeta);
		return uniqueId;
	}

	public static boolean isUniqueItem(ItemMeta itemMeta) {
		return !getUniqueItemIdentifierOrEmptyString(itemMeta)
			.isEmpty();
	}

	public static String getUniqueItemIdentifierOrEmptyString(ItemMeta itemMeta) {
		String s = itemMeta
			.getPersistentDataContainer()
			.get(Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static void setUniqueItemIdentifier(ItemStack itemStack, String uniqueId) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.getPersistentDataContainer().set(
			Main.getPlugin().getCustomTagUID(),
			PersistentDataType.STRING,
			uniqueId);
		itemStack.setItemMeta(itemMeta);
	}

	public static void sendCopyUniqueIdentifier(Player pl) {
		ItemStack itemStack = pl.getInventory().getItemInMainHand();

		TextComponent msg0 = new TextComponent(Main.prefix + "Copy the unique identifier by clicking -> ");

		TextComponent msg1 = new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "COPY" + ChatColor.GRAY + "]");
		msg1.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getOrSetUniqueItemIdentifier(itemStack)));

		TextComponent msg2 = new TextComponent(" ");

		TextComponent msg3 = new TextComponent(ChatColor.GRAY + "[" + ChatColor.GREEN + "MANUAL" + ChatColor.GRAY + "]");
		msg3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getOrSetUniqueItemIdentifier(itemStack)));

		pl.spigot().sendMessage(msg0, msg1, msg2, msg3);
	}

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

	public static List<ForgeAction> mapNotAllowedForgeActions(ItemStack itemStack) {
		PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
		if (!persistentDataContainer.has(
			Main.getPlugin().getCustomTagItemNotAllowedForgeActions())) {
			return new ArrayList<>();
		}

		return Arrays.stream(
				getNotAllowedForgeActions(itemStack).split(";"))
			.map(type -> ForgeAction.valueOf(type.trim()))
			.toList();
	}

	public static void modifyDisallowedAction(ItemStack itemStack, ForgeAction forgeAction) {
		List<ForgeAction> forgeActions = mapNotAllowedForgeActions(itemStack);
		if (!forgeActions.contains(forgeAction)) {
			forgeActions.add(forgeAction);
			applyActionsToItemStack(itemStack, forgeActions);
		}
		return;
	}

	public static void modifyAllowedAction(ItemStack itemStack, ForgeAction forgeAction) {
		List<ForgeAction> forgeActions = mapNotAllowedForgeActions(itemStack);
		if (forgeActions.contains(forgeAction)) {
			forgeActions.remove(forgeAction);
			applyActionsToItemStack(itemStack, forgeActions);
		}
		return;
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

	@Nullable
	public static String getNotAllowedForgeActions(ItemStack itemStack) {
		PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
		if (!persistentDataContainer.has(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING)) {
			return null;
		}

		return persistentDataContainer.get(Main.getPlugin().getCustomTagItemNotAllowedForgeActions(), PersistentDataType.STRING);
	}

	public static boolean isActionPrevented(ItemStack itemStack, ForgeAction action) {
		return UniqueItemIdentifierManager.mapNotAllowedForgeActions(itemStack)
			.contains(action);
	}
}
