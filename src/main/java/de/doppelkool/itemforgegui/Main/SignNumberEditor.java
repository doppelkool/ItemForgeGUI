package de.doppelkool.itemforgegui.Main;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
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
public class SignNumberEditor {

	private Player pl;
	private Sign sign;
	private Location signLocation;
	private NUMBER_EDIT_TYPE type;

	public SignNumberEditor(Player pl) throws IllegalStateException {
		this.pl = pl;

		signLocation = pl.getLocation();

		Material type = signLocation.getBlock().getType();
		if(type != Material.AIR) {
			pl.sendMessage(Main.prefix + ChatColor.RED + "There is already a block at your location");
			throw new IllegalStateException(pl.getName() + "-" + type + "-This material is in the way of a sign placement");
		}

		signLocation.getBlock().setType(Material.BIRCH_SIGN);
		this.sign = (Sign) signLocation.getBlock().getState();
	}

	public SignNumberEditor editAmount(int amountBefore) {
		type = NUMBER_EDIT_TYPE.AMOUNT;
		sign.getSide(Side.FRONT).setLine(0, "" + amountBefore);
		sign.update();
		return this;
	}

	public SignNumberEditor editDurability(int damage, int maxDamage) {
		type = NUMBER_EDIT_TYPE.DURABILITY;
		sign.getSide(Side.FRONT).setLine(0, damage + "/" + maxDamage);
		sign.getSide(Side.FRONT).setLine(1, "damage/maxDamage");
		sign.update();
		return this;
	}

	public SignNumberEditor editEnchantment(ItemStack itemToBeEnchanted, Enchantment enchantment) {
		type = NUMBER_EDIT_TYPE.ENCHANTMENT;
		sign.getSide(Side.FRONT).setLine(0, "" + itemToBeEnchanted.getEnchantmentLevel(enchantment));
		sign.update();
		return this;
	}

	public SignNumberEditor openSign() {
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
			pl.openSign(sign);
		}, 2L);
		return this;
	}

	public enum NUMBER_EDIT_TYPE {
		AMOUNT,
		DURABILITY,
		ENCHANTMENT
	}

	public static boolean isSameBlockLocation(Location loc1, Location loc2) {
		return
			loc1.getBlockX() == loc2.getBlockX() &&
				loc1.getBlockY() == loc2.getBlockY() &&
				loc1.getBlockZ() == loc2.getBlockZ();
	}

	public static Integer parseInteger(String line) {
		try {
			return Integer.parseInt(line);
		}   catch (NumberFormatException e){
			return null;
		}
	}
}
