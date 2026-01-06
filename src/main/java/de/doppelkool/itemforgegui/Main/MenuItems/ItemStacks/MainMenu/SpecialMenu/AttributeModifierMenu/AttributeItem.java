package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import lombok.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Setter
@Getter
public final class AttributeItem implements Cloneable {
	private int slot;
	private ItemStack item;
	private Attribute attribute;

	@Override
	@SneakyThrows
	protected AttributeItem clone() {
		return (AttributeItem) super.clone();
	}
}