package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
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
		//action-prevented-item-enchant
		this.cancelString = Main.prefix + "You are not allowed to do this!";

		ItemStack toEnchant = event.getItem();
		if (toEnchant.getItemMeta() != null && toEnchant.getItemMeta().hasEnchants()) {
			return false;
		}

		//Cancel event if enchanting the first item is prevented
		if (PreventionFlagManager.isActionPrevented(toEnchant, ForgeAction.ENCHANT)) {
			return true;
		}

		ItemStack enchantWith = event.getView().getItem(1);
		//At this point its allowed if the secondItem is null
		if (enchantWith == null) {
			return false;
		}

		//Cancel event if enchanting *with* second item is prevented
		return PreventionFlagManager.isActionPrevented(enchantWith, ForgeAction.ENCHANT);
	}
}