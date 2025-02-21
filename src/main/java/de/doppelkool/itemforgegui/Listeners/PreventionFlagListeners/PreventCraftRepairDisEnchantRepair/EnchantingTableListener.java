package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.DuplicateEventManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EnchantingTableListener extends DuplicateEventManager<PrepareItemEnchantEvent> implements Listener {

	@EventHandler
	public void preventEnchanting(PrepareItemEnchantEvent e) {
		duplicateExecutionSafeProcess(e.getEnchanter().getUniqueId(), e);
	}

	@Override
	protected boolean eventLogic(PrepareItemEnchantEvent event) {
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		ItemStack toEnchant = event.getItem();

		// If the item already has enchantments, let it pass.
		if (toEnchant.getItemMeta() != null && toEnchant.getItemMeta().hasEnchants()) {
			return false;
		}

		// Check if enchanting is disallowed for this item.
		if (!DisallowedActionsManager.isActionPrevented(toEnchant, ForgeAction.ENCHANT)) {
			return false;
		}

		return true;
	}
}