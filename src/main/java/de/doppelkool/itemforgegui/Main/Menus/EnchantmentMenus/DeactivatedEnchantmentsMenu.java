package de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus;

import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.EnchantmentMenu.EnchantmentMenuItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.EnchantmentMenu.EnchantmentStacksMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to target a specific enchantment and apply it on the item
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DeactivatedEnchantmentsMenu extends PaginatedMenu {

	private final int activatedEnchantmentsSlot;

	private ArrayList<Enchantment> deactivatedEnchantmentsToStrength = new ArrayList<>();

	public DeactivatedEnchantmentsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		activatedEnchantmentsSlot = this.getSlots() - 2;
	}

	@Override
	public String getMenuName() {
		return "Deactivated Enchantments";
	}

	@Override
	public int getSlots() {
		return super.getSlots();
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
			return;
		}
		if(super.pageBack(e.getSlot())) {
			return;
		}
		if(super.pageForward(e.getSlot(), deactivatedEnchantmentsToStrength.size())) {
			return;
		}

		if (activatedEnchantmentsSlot == e.getSlot()) {
			new ActivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
			return;
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			openAddActivatedEnchantmentMenu(e.getSlot());
			return;
		}
	}

	private void openAddActivatedEnchantmentMenu(int slot) {
		ItemStack item = this.inventory.getItem(slot);
		Enchantment enchantment = EnchantmentStacksMap.enchantmentsToItemStack.entrySet().stream()
			//ToDo same as in Attribute filling
			.filter(entry -> entry.getValue().equals(item))
			.map(Map.Entry::getKey)
			.findAny()
			.orElseThrow();

		this.playerMenuUtility.setTargetEnchantment(enchantment);
		new SingleEnchantmentMenu(this.playerMenuUtility)
			.open();
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		if (this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta().hasEnchants()) {
			this.inventory.setItem(activatedEnchantmentsSlot, EnchantmentMenuItems.activatedEnchantments);
		} else {
			this.inventory.setItem(activatedEnchantmentsSlot, notAvailable(EnchantmentMenuItems.activatedEnchantments));
		}
		this.inventory.setItem(53, GlobalItems.FILLER_GLASS);

		fillMenuWithDeactivatedEnchantments();
	}

	private void fillMenuWithDeactivatedEnchantments() {
		deactivatedEnchantmentsToStrength = EnchantmentStacksMap.getAllDeactivatedEnchantments(
				this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()
			)
			.stream()
			.sorted(Comparator.comparing(e -> {
				if (e.equals(Enchantment.BINDING_CURSE)) {
					return "Curse of Binding";
				} else if (e.equals(Enchantment.VANISHING_CURSE)) {
					return "Curse of Vanishing";
				} else {
					return ItemStackModifyHelper.formatCAPSName(e.getTranslationKey());
				}
			})).collect(Collectors.toCollection(ArrayList::new));

		int startIndex = this.maxItemsPerPage * page;
		int endIndex = Math.min(startIndex + this.maxItemsPerPage, deactivatedEnchantmentsToStrength.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			Enchantment deactivatedEnchantment = deactivatedEnchantmentsToStrength.get(i);
			ItemStack deactivatedEnchantmentStack = EnchantmentStacksMap.enchantmentsToItemStack.get(deactivatedEnchantment);

			if (deactivatedEnchantmentStack != null) {
				int inventorySlot = getInventorySlot(slotIndex);
				inventory.setItem(inventorySlot, deactivatedEnchantmentStack);
				slotIndex++;
			}
		}
	}
}