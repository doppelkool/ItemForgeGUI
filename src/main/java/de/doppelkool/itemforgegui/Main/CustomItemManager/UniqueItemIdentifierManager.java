package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
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

		String uniqueId;
		if (itemMeta.getPersistentDataContainer().has(Main.getPlugin().getCustomTagUID())) {
			uniqueId = getUniqueItemIdentifierOrEmptyString(itemMeta); //Empty String case not possible without prior errors
		} else {
			uniqueId = UUID.randomUUID().toString();
			itemMeta.getPersistentDataContainer().set(
				Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING,
				uniqueId);
		}

		setUniqueItemIdentifier(itemStack, uniqueId);
		return uniqueId;
	}

	public static String getOrSetUniqueItemIdentifier(Block block) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());

		String uniqueId;
		if (customBlockData.has(Main.getPlugin().getCustomTagUID())) {
			uniqueId = getUniqueItemIdentifierOrEmptyString(customBlockData); //Empty String case not possible without prior errors
		} else {
			uniqueId = UUID.randomUUID().toString();
			customBlockData.set(
				Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING,
				uniqueId);
		}

		setUniqueItemIdentifier(block, uniqueId);
		return uniqueId;
	}

	public static boolean isUniqueItem(ItemStack itemStack) {
		return itemStack != null
			&& itemStack.getItemMeta() != null
			&& !getUniqueItemIdentifierOrEmptyString(itemStack.getItemMeta())
			.isEmpty();
	}

	public static boolean isUniqueItem(CustomBlockData blockData) {
		return blockData != null
			&& !getUniqueItemIdentifierOrEmptyString(blockData)
			.isEmpty();
	}

	public static boolean isUniqueItem(Entity entity) {
		return !getUniqueItemIdentifierOrEmptyString(entity)
			.isEmpty();
	}

	public static String getUniqueItemIdentifierOrEmptyString(PersistentDataHolder itemMeta) {
		return getUniqueItemIdentifierOrEmptyString(itemMeta.getPersistentDataContainer());
	}

	public static String getUniqueItemIdentifierOrEmptyString(PersistentDataContainer dataContainer) {
		String s = dataContainer
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

	public static void setUniqueItemIdentifier(Block block, String uniqueId) {
		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());
		customBlockData.set(
			Main.getPlugin().getCustomTagUID(),
			PersistentDataType.STRING,
			uniqueId);
	}

	public static void sendCopyUniqueIdentifier(Player pl) {
		ItemStack itemStack = pl.getInventory().getItemInMainHand();

		TextComponent msg0 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_INFORMATION));

		TextComponent msg1 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_COPY_BUTTON, false));
		msg1.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getOrSetUniqueItemIdentifier(itemStack)));

		TextComponent msg2 = new TextComponent(" ");

		TextComponent msg3 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_MANUAL_BUTTON, false));
		msg3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getOrSetUniqueItemIdentifier(itemStack)));

		pl.spigot().sendMessage(msg0, msg1, msg2, msg3);
	}
}
