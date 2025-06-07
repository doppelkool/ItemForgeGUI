package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventEatListener implements Listener {

	@EventHandler
	public void preventEat(PlayerItemConsumeEvent e) {
		ItemStack item = e.getItem();

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.EAT)) {
			return;
		}

		e.setCancelled(true);
		//action-prevented-item-consumption
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
		return;
	}

	@EventHandler
	public void onCakeInteract(PlayerInteractEvent e) {
		// Check if the action is right-clicking a block
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Block block = e.getClickedBlock();
		if (block == null || block.getType() != Material.CAKE) {
			return;
		}

		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());

		if (!UniqueItemIdentifierManager.isUniqueItem(customBlockData)) {
			return;
		}

		if (!PreventionFlagManager.isActionPrevented(customBlockData, ForgeAction.EAT)) {
			return;
		}

		e.setCancelled(true);
		//action-prevented-cake-consumption
		e.getPlayer().sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	@EventHandler
	public void preventEatCake_PlaceCakeWithPF(BlockPlaceEvent e) {
		ItemStack item = e.getItemInHand();

		if(item.getType() != Material.CAKE) {
			return;
		}

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.EAT)) {
			return;
		}

		UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(e.getBlockPlaced());
		PreventionFlagManager.toggleAllowedAction(e.getBlockPlaced(), ForgeAction.EAT, true);

		//action-prevented-cake-with-pf-placed
		e.getPlayer().sendMessage(Main.prefix + "Cake placed, but can never be eaten!");
	}

	@EventHandler
	public void preventEatCake_BreakStoredCake(BlockBreakEvent e) {
		Block block = e.getBlock();

		if(block.getType() != Material.CAKE) {
			return;
		}

		CustomBlockData customBlockData = new CustomBlockData(block, Main.getPlugin());

		if (!UniqueItemIdentifierManager.isUniqueItem(customBlockData)) {
			return;
		}

		if (!PreventionFlagManager.isActionPrevented(customBlockData, ForgeAction.EAT)) {
			return;
		}

		//action-prevented-cake-with-pf-destroyed
		e.getPlayer().sendMessage(Main.prefix + "You destroyed a non-edible cake!");
		e.setDropItems(false);

		block.setType(Material.AIR);
		ItemStack cakeDrop = new ItemStack(Material.CAKE, 1);
		UniqueItemIdentifierManager.getOrSetUniqueItemIdentifier(cakeDrop);
		PreventionFlagManager.toggleAllowedAction(cakeDrop, ForgeAction.EAT, true);
		block.getWorld().dropItemNaturally(block.getLocation(), cakeDrop);
	}
}
