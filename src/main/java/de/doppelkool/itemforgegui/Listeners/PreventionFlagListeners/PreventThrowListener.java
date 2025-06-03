package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class PreventThrowListener extends DuplicateEventManager<PlayerInteractEvent> implements Listener {

	@EventHandler
	public void preventRightclickProjectile(PlayerInteractEvent e) {
		duplicateExecutionSafeProcess(e.getPlayer().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PlayerInteractEvent e) {
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		ItemStack item = e.getItem();
		if (item == null) return false;

		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null
			&& e.getClickedBlock().getType().isInteractable()
			&& !e.getPlayer().isSneaking()
			|| e.getAction() == Action.LEFT_CLICK_AIR
			|| e.getAction() == Action.LEFT_CLICK_BLOCK) {
			return false;
		}

		if (!UniqueItemIdentifierManager.isUniqueItem(item)) {
			return false;
		}

		if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.LAUNCH)) {
			return false;
		}

		return true;
	}

	@EventHandler
	public void preventUniqueArrowShoot(EntityShootBowEvent e) {
		ItemStack shotItem = e.getConsumable();
		if (!UniqueItemIdentifierManager.isUniqueItem(shotItem)) {
			return;
		}

		if (!PreventionFlagManager.isActionPrevented(shotItem, ForgeAction.LAUNCH)) {
			return;
		}

		e.setCancelled(true);

		if (!(e.getEntity() instanceof Player pl)) {
			return;
		}

		//e#setConsumeItem(boolean) is currently non-functional. Workaround readd the item:
		if(pl.getGameMode() != GameMode.CREATIVE) {
			reAddItem(pl, e.getHand(), shotItem);
		}

		pl.sendMessage(Main.prefix + "You are not allowed to do this!");
	}

	private void reAddItem(Player pl, EquipmentSlot es, ItemStack consumedItem) {
		PlayerInventory inventory = pl.getInventory();

		EquipmentSlot toCheck =
			(es == EquipmentSlot.HAND || es == EquipmentSlot.OFF_HAND)
				? (es == EquipmentSlot.HAND
					? EquipmentSlot.OFF_HAND
					: EquipmentSlot.HAND)
			: null;

		if (toCheck != null) {
			ItemStack item = inventory.getItem(toCheck);
			if (item != null && item.isSimilar(consumedItem)) {
				item.setAmount(item.getAmount() + 1);
				return;
			}
		}

		for(Map.Entry<Integer, ? extends ItemStack> entry : inventory.all(consumedItem.getType()).entrySet()) {
			if(entry.getValue().isSimilar(consumedItem)) {
				entry.getValue().setAmount(entry.getValue().getAmount() + 1);
				return;
			}
		}

		inventory.addItem(consumedItem);
		return;
	}
}