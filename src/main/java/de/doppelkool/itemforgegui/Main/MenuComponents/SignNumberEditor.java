package de.doppelkool.itemforgegui.Main.MenuComponents;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
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
import org.bukkit.potion.PotionEffectType;

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
		if (type != Material.AIR) {
			MessageManager.message(pl, Messages.SIGN_EDITOR_NOT_PLACED_BLOCK_BLOCKADE);
			Bukkit.getLogger().info(ChatColor.RED + pl.getName() + "-" + type + "-This material is in the way of a sign placement");
		}

		signLocation.getBlock().setType(Material.BIRCH_SIGN);
		this.sign = (Sign) signLocation.getBlock().getState();
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
		} catch (NumberFormatException e) {
			return null;
		}
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
		sign.update();
		return this;
	}

	public SignNumberEditor editEnchantment(ItemStack itemToBeEnchanted, Enchantment enchantment) {
		type = NUMBER_EDIT_TYPE.ENCHANTMENT;
		sign.getSide(Side.FRONT).setLine(0, "" + itemToBeEnchanted.getEnchantmentLevel(enchantment));
		sign.update();
		return this;
	}

	public SignNumberEditor editPotionEffect(ItemStack itemToBeEffected, PotionEffectType potionEffectType) {
		type = NUMBER_EDIT_TYPE.ARMOR_EFFECT;
		Integer strength = ArmorEffectManager.getArmorEffect(itemToBeEffected, potionEffectType);
		sign.getSide(Side.FRONT).setLine(0, strength == null ? "0" : "" + strength);
		sign.update();
		return this;
	}

	public SignNumberEditor editItemID(String uniqueItemIdentifierOrEmptyString) {
		type = NUMBER_EDIT_TYPE.ITEM_ID;

		final int chunkSize = 15;
		final int length = uniqueItemIdentifierOrEmptyString.length();

		for (int i = 0; i < length; i += chunkSize) {
			int end = Math.min(i + chunkSize, length);
			sign.getSide(Side.FRONT).setLine(i / chunkSize, uniqueItemIdentifierOrEmptyString.substring(i, end));
		}

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
		ENCHANTMENT,
		ARMOR_EFFECT,
		ITEM_ID,

		;
	}
}
