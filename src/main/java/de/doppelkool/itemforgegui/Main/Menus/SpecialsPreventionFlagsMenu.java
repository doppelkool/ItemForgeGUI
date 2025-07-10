package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.Resources;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the ability to set PreventionFlags on the item that prevent specific behaviour as described in {@link ForgeAction}
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsPreventionFlagsMenu extends Menu {

	public SpecialsPreventionFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Prevention Flags";
	}

	@Override
	public int getSlots() {
		return 9 * 5;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot(), SpecialsMenu::new)) {
			return;
		}

		ForgeAction clickedForgeAction = PreventionFlagManager.SLOT_TO_ACTION.get(e.getSlot()).getA();
		if (clickedForgeAction == null) {
			return;
		}

		forgeActionClicked(e.getCurrentItem(), clickedForgeAction);

		new ItemInfoManager(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()).updateItemInfo();
	}

	private void forgeActionClicked(ItemStack currentItem, ForgeAction clickedAction) {
		boolean newStatus;
		if (clickedAction == ForgeAction.CRAFT) {
			PreventionFlagManager.CraftingPreventionFlag activeCraftingPrevention = PreventionFlagManager.getInstance().getActiveCraftingPrevention(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand());
			PreventionFlagManager.CraftingPreventionFlag nextCraftingPrevention = activeCraftingPrevention != null
				? activeCraftingPrevention.cycle()
				: PreventionFlagManager.CraftingPreventionFlag.ALL;

			PreventionFlagManager.getInstance().updateCraftPreventionType(
				this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
				nextCraftingPrevention
			);
			ItemStackModifyHelper.updateCraftingPreventionInMenuItemLore(currentItem, nextCraftingPrevention);
			newStatus = nextCraftingPrevention != null;
		} else {
			newStatus = !ItemStackModifyHelper.hasGlow(currentItem);
		}

		ItemStackModifyHelper.setActivated(currentItem, newStatus);

		PreventionFlagManager.getInstance().toggleItemFlag(
			this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
			clickedAction,
			newStatus);
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		PreventionFlagManager.SLOT_TO_ACTION.forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();

			if (isLogicallyApplyable(itemInMainHand, pair.getA())) {

				boolean actionPrevented = PreventionFlagManager.getInstance().isFlagApplied(itemInMainHand, pair.getA());

				if (pair.getA() == ForgeAction.CRAFT) {
					PreventionFlagManager.CraftingPreventionFlag activeCraftingPrevention = PreventionFlagManager.getInstance().getActiveCraftingPrevention(itemInMainHand);
					ItemStackModifyHelper.updateCraftingPreventionInMenuItemLore(itemStackClone, activeCraftingPrevention);
				}

				ItemStackModifyHelper.setActivated(itemStackClone, actionPrevented);
			} else {
				itemStackClone = ItemStackCreateHelper.notAvailable(itemStackClone);
			}

			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}

	private boolean isLogicallyApplyable(ItemStack itemInMainHand, ForgeAction a) {

		if (a == ForgeAction.DROP
			|| a == ForgeAction.ITEM_FRAME_PLACE
			|| a == ForgeAction.DESTROY
			|| a == ForgeAction.RENAME
			//ToDo(Check) Craft can be put on everything for now, the action will get prevented on event trigger
			|| a == ForgeAction.CRAFT) {
			return true;
		}

		if (a == ForgeAction.SMELT && (itemInMainHand.getType().isFuel()
			|| Resources.smeltableItems.contains(itemInMainHand.getType()))) {
			return true;
		}

		if (a == ForgeAction.PLACE && (itemInMainHand.getType().isBlock()
			|| Resources.PLACEABLE_ITEMS.contains(itemInMainHand.getType()))) {
			return true;
		}

		if (a == ForgeAction.LAUNCH
			&& Resources.LAUNCHABLE_ITEMS.contains(itemInMainHand.getType())) {
			return true;
		}

		if (a == ForgeAction.EAT
			&& itemInMainHand.getType().isEdible()) {
			return true;
		}

		if (a == ForgeAction.EQUIP &&
			(itemInMainHand.getItemMeta() instanceof ArmorMeta
				|| Resources.EQUIPABLE_ITEMS_WITHOUT_HUMAN_ARMOR.contains(itemInMainHand.getType()))) {
			return true;
		}

		if (a == ForgeAction.REPAIR
			&& itemInMainHand.getType().getMaxDurability() > 0) {
			return true;
		}

		if (a == ForgeAction.UPGRADE
			&& Resources.UPGRADABLE_ITEMS.contains(itemInMainHand.getType())) {
			return true;
		}

		if (a == ForgeAction.ENCHANT
			&& Resources.VANILLA_ENCHANTABLE_MATERIALS.contains(itemInMainHand.getType())) {
			return true;
		}

		return false;
	}

}