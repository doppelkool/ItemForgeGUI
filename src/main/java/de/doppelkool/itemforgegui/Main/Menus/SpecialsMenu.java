package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus.SpecialsActivatedArmorEffectsMenu;
import de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus.SpecialsDeactivatedArmorEffectsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;


/**
 * Submenu as part of the main function of this plugin.
 * It divides between the item flags that change the items appearance
 * and the prevention flags to prevent specific behaviour
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsMenu extends Menu {

	private final List<SlotItemWrapper.SlotItemExecute> SLOT_TO_ITEMS = List.of(
		new SlotItemWrapper.SlotItemExecute(10, ItemStacks.itemFlags, () -> new Specials_MinecraftItemFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(11, ItemStacks.customItemFlags, () -> new Specials_CustomItemFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(13, ItemStacks.preventionFlags, () -> new SpecialsPreventionFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(15, ItemStacks.armorEffects, () -> armorEffectsClicked())
	);

	public SpecialsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Edit Specials";
	}

	@Override
	public int getSlots() {
		return 9 * 3;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
			return;
		}

		SLOT_TO_ITEMS.stream()
			.filter(slotItemExecute -> slotItemExecute.slot() == e.getSlot())
			.findAny()
			.orElseThrow()
			.menuConsumer()
			.run();
	}

	private void armorEffectsClicked() {
		ItemStack item = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ArmorEffectManager.initPDCVariable(item);
		if (ArmorEffectManager.getAllActivatedPotionEffectTypesAsList(item)
			.isEmpty()) {
			new SpecialsDeactivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		} else {
			new SpecialsActivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		SLOT_TO_ITEMS.forEach((slotItem) -> this.inventory.setItem(slotItem.slot(), slotItem.item()));
		setFillerGlass();
	}
}
