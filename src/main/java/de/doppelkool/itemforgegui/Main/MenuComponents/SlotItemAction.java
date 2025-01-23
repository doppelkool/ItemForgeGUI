package de.doppelkool.itemforgegui.Main.MenuComponents;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public record SlotItemAction(int slot, ItemStack item, Consumer<InventoryClickEvent> onClick) {}
