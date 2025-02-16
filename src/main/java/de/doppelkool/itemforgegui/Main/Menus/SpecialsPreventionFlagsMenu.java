package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.DisallowedActionsManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.HashMap;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.setGlow;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsPreventionFlagsMenu extends Menu {

	private static final HashMap<Integer, Pair<ForgeAction, ItemStack>> slotToAction = new HashMap<>();

	static {
		slotToAction.put(10, new Pair<>(ForgeAction.DROP, ItemStacks.itemDrop));
		slotToAction.put(11, new Pair<>(ForgeAction.CRAFT, ItemStacks.itemCraft));
		slotToAction.put(12, new Pair<>(ForgeAction.ITEM_FRAME_PLACE, ItemStacks.itemFramePlace));
		slotToAction.put(13, new Pair<>(ForgeAction.LAUNCH, ItemStacks.throwItem));//ENDERPEARL+ARROW,..
		slotToAction.put(14, new Pair<>(ForgeAction.EAT, ItemStacks.eatItem));
		slotToAction.put(15, new Pair<>(ForgeAction.PLACE, ItemStacks.placeItem));
		slotToAction.put(16, new Pair<>(ForgeAction.EQUIP, ItemStacks.equipItem));
		slotToAction.put(19, new Pair<>(ForgeAction.BURN, ItemStacks.burnItem));
		slotToAction.put(20, new Pair<>(ForgeAction.USE_TOOL, ItemStacks.useTool));
		slotToAction.put(21, new Pair<>(ForgeAction.REPAIR, ItemStacks.repairItem));
		slotToAction.put(22, new Pair<>(ForgeAction.ENCHANT, ItemStacks.enchantItem));
		slotToAction.put(23, new Pair<>(ForgeAction.DISENCHANT, ItemStacks.disenchantitem));
		slotToAction.put(24, new Pair<>(ForgeAction.UPGRADE, ItemStacks.upgradeItem));
		slotToAction.put(25, new Pair<>(ForgeAction.RENAME, ItemStacks.renameItem));
	}


	public SpecialsPreventionFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Prevention Flags";
	}

	@Override
	public int getSlots() {
		return 9*5;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 36) {
			handleClose();
			return;
		}
		if (e.getSlot() == 37) {
			new SpecialsMenu(playerMenuUtility)
				.open();
			return;
		}

		ForgeAction clickedAction = slotToAction.get(e.getSlot()).getA();

		if(clickedAction == null) {
			return;
		}

		boolean newStatus = !hasGlow(e.getCurrentItem());
		DisallowedActionsManager.toggleAllowedAction(
			this.playerMenuUtility.getOwner().getInventory().getItemInMainHand(),
			clickedAction,
			newStatus);
		setGlow(e.getCurrentItem(), newStatus);
		return;
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();

		slotToAction.forEach((slot, pair) -> {
			ItemStack itemStackClone = pair.getB().clone();
			setGlow(itemStackClone, DisallowedActionsManager.isActionPrevented(itemInMainHand, pair.getA()));
			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}
}
