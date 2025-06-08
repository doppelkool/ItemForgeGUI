package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventEquipListener implements Listener {

	@EventHandler
	public void onArmorEquip(ArmorEquipEvent e) {
		final Player pl = e.getPlayer();
		final ItemStack newArmor = e.getNewArmorPiece();

		if (newArmor == null || newArmor.getType() == Material.AIR) return;

		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
			int slotIndex = switch (e.getType()) {
				case HELMET -> 39;
				case CHESTPLATE -> 38;
				case LEGGINGS -> 37;
				case BOOTS -> 36;
			};

			ItemStack equipped = pl.getInventory().getItem(slotIndex);
			if (equipped == null || equipped.getType() != newArmor.getType()) return;

			if (PreventionFlagManager.isActionPrevented(equipped, ForgeAction.EQUIP)) {
				pl.getInventory().setItem(slotIndex, null);

				HashMap<Integer, ItemStack> leftover = pl.getInventory().addItem(equipped);
				for (ItemStack remaining : leftover.values()) {
					pl.getWorld().dropItemNaturally(pl.getLocation(), remaining);
				}

				MessageManager.message(pl, Messages.ACTION_PREVENTED_ARMOR_EQUIP);
			}
		}, 1L);
	}

	@EventHandler
	public void preventArmorDispenseOnEntity(BlockDispenseArmorEvent e) {
		ItemStack item = e.getItem();
		Block block = e.getBlock();

		// Check if the block is a dispenser
		if (!(block.getState() instanceof Dispenser)) {
			return;
		}

		// Check if equipping is prevented
		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.EQUIP)) {
			return;
		}

		e.setCancelled(true);
	}

	@EventHandler
	public void preventHorseArmorAndSaddleEquip(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player pl)) return;
		if (!(e.getView().getTopInventory().getHolder() instanceof Horse horse)) return;

		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
			HorseInventory horseInv = horse.getInventory();
			boolean isArmorPrevented = false;
			boolean isSaddlePrevented = false;

			// Check and handle armor
			ItemStack equippedArmor = horseInv.getArmor();
			if (equippedArmor != null && equippedArmor.getType() != Material.AIR &&
				PreventionFlagManager.isActionPrevented(equippedArmor, ForgeAction.EQUIP)) {

				horseInv.setArmor(null);
				returnItemToPlayer(pl, equippedArmor);
				isArmorPrevented = true;
			}

			// Check and handle saddle
			ItemStack equippedSaddle = horseInv.getSaddle();
			if (equippedSaddle != null && equippedSaddle.getType() != Material.AIR &&
				PreventionFlagManager.isActionPrevented(equippedSaddle, ForgeAction.EQUIP)) {

				horseInv.setSaddle(null);
				returnItemToPlayer(pl, equippedSaddle);
				isSaddlePrevented = true;
			}

			//ToDo Split into 2 messages
			//Send a single message if either armor or saddle was prevented
			if (isArmorPrevented || isSaddlePrevented) {
				MessageManager.message(pl, Messages.ACTION_PREVENTED_HORSE_ARMOR_SADDLE_EQUIP);
			}
		}, 1L);
	}

	private void returnItemToPlayer(Player player, ItemStack item) {
		HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
		for (ItemStack remaining : leftover.values()) {
			player.getWorld().dropItemNaturally(player.getLocation(), remaining);
		}
	}

}
