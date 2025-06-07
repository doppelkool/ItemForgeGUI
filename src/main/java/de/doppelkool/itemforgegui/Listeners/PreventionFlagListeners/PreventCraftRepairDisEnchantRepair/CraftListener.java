package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class CraftListener implements Listener {

	@EventHandler
	public void onPrepareItemCraft(PrepareItemCraftEvent event) {
		if (!(event.getInventory() instanceof CraftingInventory inv)) return;

		if (shouldPreventCraft(
				inv.getMatrix(),
				getRecipeKey(event.getRecipe()))) {
			inv.setResult(null);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getInventory() instanceof CraftingInventory inv)) return;

		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
			if (shouldPreventCraft(
					inv.getMatrix(),
					getRecipeKey(inv.getRecipe()))) {
				inv.setResult(null);
			}
		});

	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		if (!(event.getInventory() instanceof CraftingInventory inv)) return;

		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
			if (shouldPreventCraft(
					inv.getMatrix(),
					getRecipeKey(inv.getRecipe()))) {
				inv.setResult(null);
			}
		});
	}

	private boolean shouldPreventCraft(ItemStack[] matrix, NamespacedKey recipeKey) {
		boolean isDefaultRecipe = recipeKey != null && recipeKey.getNamespace().equals("minecraft");

		for (ItemStack item : matrix) {
			if (item == null) continue;

			if (!PreventionFlagManager.isActionPrevented(item, ForgeAction.CRAFT)) continue;

			PreventionFlagManager.CraftingPrevention activePrevention = PreventionFlagManager.getActiveCraftingPrevention(item);

			if (activePrevention == PreventionFlagManager.CraftingPrevention.ALL) {
				return true;
			}
			if (activePrevention == PreventionFlagManager.CraftingPrevention.DEFAULT_RECIPES && isDefaultRecipe) {
				return true;
			}
			if (activePrevention == PreventionFlagManager.CraftingPrevention.CUSTOM_RECIPES && !isDefaultRecipe) {
				return true;
			}
		}
		return false;
	}

	private NamespacedKey getRecipeKey(Recipe recipe) {
		if (recipe instanceof Keyed keyedRecipe) {
			return keyedRecipe.getKey();
		}
		return null;
	}
}
