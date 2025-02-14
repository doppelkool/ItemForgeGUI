package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeAction;
import de.doppelkool.itemforgegui.Main.CustomItemManager.UniqueItemIdentifierManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.Map;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.hasGlow;
import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper.setGlow;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsPreventionFlagsMenu extends Menu {
	//ENDERPEARL+ARROW,..
	private static final Map<Integer, Pair<ForgeAction, ItemStack>> slotToAction = Map.of(
		10, new Pair<>(ForgeAction.ITEM_DROP, ItemStacks.itemDrop),
		11, new Pair<>(ForgeAction.ITEM_FRAME_PLACE, ItemStacks.itemFramePlace),
		12, new Pair<>(ForgeAction.LAUNCH, ItemStacks.throwItem),
		13, new Pair<>(ForgeAction.EAT, ItemStacks.eatItem),
		14, new Pair<>(ForgeAction.PLACE, ItemStacks.placeItem),
		15, new Pair<>(ForgeAction.EQUIP, ItemStacks.equipItem),
		16, new Pair<>(ForgeAction.BURN, ItemStacks.burnItem),
		21, new Pair<>(ForgeAction.USE_TOOL, ItemStacks.useTool),
		22, new Pair<>(ForgeAction.REPAIR, ItemStacks.repairItem),
		23, new Pair<>(ForgeAction.UPGRADE, ItemStacks.upgradeItem)
	);

	public SpecialsPreventionFlagsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Prevention Flags";
	}

	@Override
	public int getSlots() {
		return 9*4;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 27) {
			handleClose();
			return;
		}
		if (e.getSlot() == 28) {
			new SpecialsMenu(playerMenuUtility)
				.open();
			return;
		}

		ForgeAction clickedAction = slotToAction.get(e.getSlot()).getA();

		if(clickedAction == null) {
			return;
		}

		boolean newStatus = !hasGlow(e.getCurrentItem());
		UniqueItemIdentifierManager.toggleAllowedAction(
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
			setGlow(itemStackClone, UniqueItemIdentifierManager.isActionPrevented(itemInMainHand.getItemMeta(), pair.getA()));
			this.inventory.setItem(slot, itemStackClone);
		});

		setFillerGlass();
	}
}
