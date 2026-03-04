package de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SlotItemWrapper {
	public record SlotItem(int slot, ItemStack item) {}

	public record SlotItemEvent(int slot, ItemStack item, Consumer<InventoryClickEvent> onClick) {}

	public record SlotItemExecute(int slot, ItemStack item, Runnable runnable) {}

	public record SlotItemOperationValueEdit(int slot, ItemStack item, AttributeModifier.Operation operation, Double valueEdit) {}

	public record SlotItemOperationExplanationValueEdit(int slot, ItemStack item, AttributeModifier.Operation operation, Supplier<String> explanationLoreString, Double valueEdit) {}
}
