package de.doppelkool.itemforgegui.Main;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
@Setter
public class PlayerMenuUtility {

	private Player owner;
	private ItemStack tempStoredItem;
	private int storedSlot;
	private Enchantment targetEnchantment;

	public PlayerMenuUtility(Player pl) {
		this.owner = pl;
	}
}
