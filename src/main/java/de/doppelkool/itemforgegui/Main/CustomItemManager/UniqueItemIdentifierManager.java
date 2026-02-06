package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class UniqueItemIdentifierManager {

	public static String getRandomUID() {
		return UUID.randomUUID().toString();
	}

	private static void getMetaEditApply(ItemStack itemStack, Consumer<PersistentDataContainer> edit) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		edit.accept(itemMeta.getPersistentDataContainer());
		itemStack.setItemMeta(itemMeta);
	}

	private static void getMetaEditApply(Block block, Consumer<PersistentDataContainer> edit) {
		edit.accept(new CustomBlockData(block, Main.getPlugin()));
	}

	public static boolean isUnique(ItemStack itemStack) {
		return itemStack.getItemMeta().getPersistentDataContainer().has(Main.getPlugin().getCustomTagUID());
	}

	public static boolean isUnique(Block block) {
		return new CustomBlockData(block, Main.getPlugin()).has(Main.getPlugin().getCustomTagUID());
	}

	public static Optional<String> getUID(ItemStack itemStack) {
		if (!isUnique(itemStack)) {
			return Optional.empty();
		}

		return Optional.of(
			getUniqueItemIdentifier(itemStack.getItemMeta().getPersistentDataContainer()));
	}

	public static Optional<String> getUID(Block block) {
		if (!isUnique(block)) {
			return Optional.empty();
		}

		return Optional.of(
			getUniqueItemIdentifier(
				new CustomBlockData(block, Main.getPlugin())));
	}

	private static String getUniqueItemIdentifier(PersistentDataContainer persistentDataContainer) {
		return persistentDataContainer.get(Main.getPlugin().getCustomTagUID(), PersistentDataType.STRING);
	}

	public static void setUID(ItemStack itemStack, String uid) {
		getMetaEditApply(itemStack, (persistentDataContainer) -> {
			persistentDataContainer.set(
				Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING,
				uid
			);
		});
	}

	public static void setUID(Block block, String uid) {
		getMetaEditApply(block, (persistentDataContainer) -> {
			persistentDataContainer.set(
				Main.getPlugin().getCustomTagUID(),
				PersistentDataType.STRING,
				uid
			);
		});
	}

	public static String ensureUID(ItemStack itemStack) {
		return getUID(itemStack).orElseGet(() -> {
			String randomUID = getRandomUID();
			setUID(itemStack, randomUID);
			return randomUID;
		});
	}

	public static String ensureUID(Block block) {
		return getUID(block).orElseGet(() -> {
			String randomUID = getRandomUID();
			setUID(block, randomUID);
			return randomUID;
		});
	}

	public static String formatUID(ItemStack itemStack) {
		return getUID(itemStack).orElse("-");
	}

	public static void sendCopyUniqueIdentifier(Player pl, ItemStack itemInMainHand) {
		String uID = ensureUID(itemInMainHand);

		TextComponent msg0 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_INFORMATION));

		TextComponent msg1 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_COPY_BUTTON, false));
		msg1.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, uID));

		TextComponent msg2 = new TextComponent(" ");

		TextComponent msg3 = new TextComponent(MessageManager.format(Messages.MISC_COPY_UNIQUE_IDENTIFIER_MANUAL_BUTTON, false));
		msg3.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uID));

		pl.spigot().sendMessage(msg0, msg1, msg2, msg3);
	}
}
