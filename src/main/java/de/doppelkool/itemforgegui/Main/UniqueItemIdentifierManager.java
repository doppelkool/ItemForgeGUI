package de.doppelkool.itemforgegui.Main;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

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
		if (persistentDataContainer.has(Main.getPlugin().getItemforgeguiUniqueIdentifier())) {
			uniqueId = getUniqueItemIdentifierOrEmptyString(itemMeta); //Empty String case not possible without prior errors
		} else {
			uniqueId = UUID.randomUUID().toString();
			itemMeta.getPersistentDataContainer().set(
				Main.getPlugin().getItemforgeguiUniqueIdentifier(),
				PersistentDataType.STRING,
				uniqueId);
		}

		itemStack.setItemMeta(itemMeta);
		return uniqueId;
	}

	public static String getUniqueItemIdentifierOrEmptyString(ItemMeta itemMeta) {
		String s = itemMeta
			.getPersistentDataContainer()
			.get(Main.getPlugin().getItemforgeguiUniqueIdentifier(),
				PersistentDataType.STRING);
		return s == null ? "" : s;
	}

	public static void setUniqueItemIdentifier(ItemStack itemStack, String uniqueId) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.getPersistentDataContainer().set(
			Main.getPlugin().getItemforgeguiUniqueIdentifier(),
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
}
