package de.doppelkool.itemforgegui.Main;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.PrepareInventoryResultEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.stream.Collectors;

/**
 * A temporary Logger class to provide quick debug message access
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
			(itemStack.getItemMeta() != null) ? DisallowedActionsManager.getNotAllowedForgeActions(itemStack.getItemMeta().getPersistentDataContainer()) : "meta null",
			itemStack
		));
	}

	public static void log(Entity e) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		Bukkit.getLogger().info(String.format(
			"ItemStack Log ->\n" +
				"at: %s\n" +
				"etype: %s\n" +
				"PersistentDataContainer: %s\n",
			stackTrace[2].getClassName() + "#" + stackTrace[2].getMethodName(),
			e.getType().getTranslationKey(),
			getKeysAndValuesAsString(e)
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
			getKeysAndValuesAsString(dataHolder),
			dataHolder
		));
	}

	private static String getKeysAndValuesAsString(PersistentDataHolder holder) {
		PersistentDataContainer pdc = holder.getPersistentDataContainer();

		return pdc.getKeys().stream()
			.map(key -> {
				String value = pdc.get(key, PersistentDataType.STRING);
				return "\"" + key.toString() + "\":\"" + (value != null ? value : "null") + "\"";
			})
			.collect(Collectors.joining(";"));
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
