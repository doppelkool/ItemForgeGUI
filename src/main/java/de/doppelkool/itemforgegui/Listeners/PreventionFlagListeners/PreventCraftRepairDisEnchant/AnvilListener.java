package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchant;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AnvilListener implements Listener {

	private final Map<Inventory, Boolean> pendingAnvilProcessing = new WeakHashMap<>();

	@EventHandler
	public void onPrepareAnvil(PrepareAnvilEvent e) {
		Inventory anvilInventory = e.getInventory();

		if (pendingAnvilProcessing.containsKey(anvilInventory)) {
			return;
		}
		pendingAnvilProcessing.put(anvilInventory, true);

		//Next tick, remove the inventory again
		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {

			// Get the two input items: slot 0 (left) and slot 1 (right)
			ItemStack leftItem = anvilInventory.getItem(0);
			ItemStack rightItem = anvilInventory.getItem(1);

			// If both inputs are null, nothing to process.
			if (leftItem == null && rightItem == null) {
				removeAnvilInventory(anvilInventory);
				return;
			}

			// Combine prevention flags for each operation type.
			boolean preventEnchantAny =
					DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.DISENCHANT)
					|| DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.DISENCHANT);
			boolean preventRepairAny =
					DisallowedActionsManager.isActionPrevented(leftItem, ForgeAction.REPAIR)
					|| DisallowedActionsManager.isActionPrevented(rightItem, ForgeAction.REPAIR);

			// If neither prevention is active, allow the operation.
			if (!preventEnchantAny && !preventRepairAny) {
				removeAnvilInventory(anvilInventory);
				return;
			}

			// Determine if either input is enchanted (indicating an enchanting operation).
			boolean leftEnchanted = leftItem != null && !leftItem.getEnchantments().isEmpty();
			boolean rightEnchanted = rightItem != null && !rightItem.getEnchantments().isEmpty();
			boolean isEnchant = leftEnchanted || rightEnchanted;

			// Determine if this is a repair operation: both inputs exist and are of the same type.
			boolean isRepair = leftItem != null && rightItem != null && leftItem.getType() == rightItem.getType();

			/*
			 * Prevention logic:
			 * 1. If the operation is clearly an enchantment (non-repair) and the enchant prevention flag is active, cancel.
			 * 2. If the operation is clearly a repair (non-enchant) and the repair prevention flag is active, cancel.
			 * 3. If the operation is ambiguous (both or neither clear), cancel the operation.
			 */
			if ((isEnchant && !isRepair && preventEnchantAny) ||
				(isRepair && !isEnchant && preventRepairAny) ||
				((isEnchant && isRepair) || (!isEnchant && !isRepair))) {

				//Setting the result does not work in seemingly random occasions, so set the result item manually to null as well
				e.setResult(null);
				e.getInventory().setItem(2, null);

				e.getView().getPlayer().sendMessage(Main.prefix + "You are not allowed to do this.");
			}

			removeAnvilInventory(anvilInventory);
		});
	}

	private void removeAnvilInventory(Inventory anvilInv) {
		pendingAnvilProcessing.remove(anvilInv);
	}
}
