package de.doppelkool.itemforgegui.Main.MenuComponents;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SlotItemWrapper {
	public record SlotItem(int slot, ItemStack item) {}

	public record SlotItemEvent(int slot, ItemStack item, Consumer<InventoryClickEvent> onClick) {}

	public record SlotItemExecute(int slot, ItemStack item, Runnable runnable) {}

	public record SlotItemConsumer(int slot, ItemStack item, Consumer<Integer> runnable) {}

	public record SlotExecute(int slot, Runnable runnable) {}

	public record SlotItemOperationValueEdit(int slot, ItemStack item, AttributeModifier.Operation operation, Double valueEdit) {}
}
