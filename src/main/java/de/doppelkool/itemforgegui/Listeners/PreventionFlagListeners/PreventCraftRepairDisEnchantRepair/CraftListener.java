package de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
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
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {
		if (!(e.getInventory() instanceof CraftingInventory inv)) return;

		if (shouldPreventCraft(
			inv.getMatrix(),
			getRecipeKey(e.getRecipe()))) {
			inv.setResult(null);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!(e.getInventory() instanceof CraftingInventory inv)) return;

		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
			if (shouldPreventCraft(
				inv.getMatrix(),
				getRecipeKey(inv.getRecipe()))) {
				inv.setResult(null);
			}
		});

	}

	@EventHandler
	public void onInventoryDrag(InventoryDragEvent e) {
		if (!(e.getInventory() instanceof CraftingInventory inv)) return;

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

			if (!PreventionFlagManager.getInstance().isFlagApplied(item, ForgeAction.CRAFT)) continue;

			PreventionFlagManager.CraftingPreventionFlag activePrevention = PreventionFlagManager.getInstance().getActiveCraftingPrevention(item);

			if (activePrevention == PreventionFlagManager.CraftingPreventionFlag.ALL) {
				return true;
			}
			if (activePrevention == PreventionFlagManager.CraftingPreventionFlag.DEFAULT_RECIPES && isDefaultRecipe) {
				return true;
			}
			if (activePrevention == PreventionFlagManager.CraftingPreventionFlag.CUSTOM_RECIPES && !isDefaultRecipe) {
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
