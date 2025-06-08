package de.doppelkool.itemforgegui.Main.Messages;

import com.tchristofferson.configupdater.ConfigUpdater;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class MessageManager {

	@Getter
	private static MessageManager instance;

	private FileConfiguration messages;
	private final File messagesFile;

	public MessageManager(JavaPlugin plugin) {
		instance = this;
		messagesFile = new File(plugin.getDataFolder(), "messages.yml");

		try (InputStream in = plugin.getResource("messages.yml")) {
			if (!messagesFile.exists()) {
				Files.copy(in, messagesFile.toPath());
			}

			ConfigUpdater.update(plugin,
				"messages.yml",
				messagesFile,
				List.of()
			);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
			Bukkit.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
		}

		messages = YamlConfiguration.loadConfiguration(messagesFile);
	}

	public static String getPrefix() {
		return get(Messages.PREFIX);
	}

	private static String get(Messages key) {
		return instance.messages.getString(key.getKey(), "&cMissing message: " + key);
	}

	public static void message(CommandSender msgRecipient, Messages key) {
		message(msgRecipient, key, Map.of(), true);
	}

	public static void message(CommandSender msgRecipient, Messages key, Map<String, String> placeholders) {
		message(msgRecipient, key, placeholders, true);
	}

	public static void message(CommandSender msgRecipient, Messages key, boolean includePrefix) {
		message(msgRecipient, key, Map.of(), includePrefix);
	}

	public static void message(CommandSender msgRecipient, Messages key, Map<String, String> placeholders, boolean includePrefix) {
		String message = format(key, placeholders, includePrefix);
		if(!message.isEmpty())msgRecipient.sendMessage(message);
	}

	public static String format(Messages key) {
		return format(key, Map.of(), true);
	}

	public static String format(Messages key, Map<String, String> placeholders) {
		return format(key, placeholders, true);
	}

	public static String format(Messages key, boolean includePrefix) {
		return format(key, Map.of(), includePrefix);
	}

	public static String format(Messages key, Map<String, String> placeholders, boolean includePrefix) {
		String messageValue = get(key);
		if(messageValue.isEmpty()) return "";

		String msg = (includePrefix ? getPrefix() : "") + messageValue;

		// Placeholder replacement
		for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			msg = msg.replace("{" + entry.getKey() + "}", entry.getValue());
		}
		// Color code support
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
