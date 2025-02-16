package de.doppelkool.itemforgegui.Main;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.PrepareInventoryResultEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Logger {

	public static void log(ItemStack itemStack) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"ItemStack Log ->\n" +
				"at: %s\n" +
				"DisplayName: %s\n" +
				"Lore: %s\n" +
				"Type: %s\n" +
				"Disallowed Actions: %s\n" +
				"AsString: %s",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			(itemStack.getItemMeta() != null) ? itemStack.getItemMeta().getDisplayName() : "meta null",
			(itemStack.getItemMeta() != null) ? itemStack.getItemMeta().getLore() : "meta null",
			itemStack.getType(),
			(itemStack.getItemMeta() != null) ? DisallowedActionsManager.getNotAllowedForgeActions(itemStack.getItemMeta()) : "meta null",
			itemStack
		));
	}

	public static void log(PersistentDataHolder dataHolder) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"ItemStack Log ->\n" +
				"at: %s\n" +
				"PersistentDataContainer: %s\n" +
				"AsString: %s",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			getKeysAndValuesAsString(dataHolder.getPersistentDataContainer().getKeys()),
			dataHolder
		));
	}

	private static String getKeysAndValuesAsString(Set<NamespacedKey> keys) {
		return keys.stream().map(NamespacedKey::toString).collect(Collectors.joining(";"));
	}

	public static void log(PlayerEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Event Log ->\n" +
				"at: %s\n" +
				"Name: %s\n" +
				"Player: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName(),
			e.getPlayer().getName() + " : " + e.getPlayer().getUniqueId()
		));
	}

	public static void log(PlayerInteractEntityEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Event Log ->\n" +
				"at: %s\n" +
				"Name: %s\n" +
				"Player: %s\n" +
				"Entity: %s\n" +
				"MainHandItem: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName(),
			e.getPlayer().getName() + " : " + e.getPlayer().getUniqueId(),
			e.getRightClicked(),
			e.getPlayer().getInventory().getItemInMainHand().getType()
		));
	}

	public static void log(PrepareInventoryResultEvent e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"Event Log ->\n" +
				"at: %s\n" +
				"Name: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getEventName()
		));
	}

}
