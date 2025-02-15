package de.doppelkool.itemforgegui.Main.Menus;

import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.EnchantmentStacks;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class DeactivatedEnchantmentsMenu extends PaginatedMenu {

	private ArrayList<Enchantment> deactivatedEnchantmentsToStrength = new ArrayList<>();

	public DeactivatedEnchantmentsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Deactivated Enchantments";
	}

	@Override
	public int getSlots() {
		return 9*6;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (e.getSlot() == 45) {
			handleClose();
			return;
		}
		if (e.getSlot() == 46) {
			handleBack();
			return;
		}

		if (e.getSlot() == 48) {
			if (page != 0) {
				page = page - 1;
				super.open();
			}
			return;
		}

		if (e.getSlot() == 50) {
			int nextPageStartIndex = (page + 1) * getMaxItemsPerPage();
			if (nextPageStartIndex < deactivatedEnchantmentsToStrength.size()) {
				page++;
				super.open();
			}
			return;
		}

		if(e.getSlot() == 52) {
			new ActivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			int slot = e.getSlot();
			ItemStack item = this.inventory.getItem(slot);
			Enchantment enchantment = EnchantmentStacks.enchantmentsToItemStack.entrySet().stream()
				.filter(entry -> entry.getValue().equals(item))
				.map(Map.Entry::getKey)
				.findFirst()
				.get(); //Handled every other case
			this.playerMenuUtility.setTargetEnchantment(enchantment);

			new SingleEnchantmentMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomEnchantmentMenuFilling();

		if (this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta().hasEnchants()) {
			this.inventory.setItem(52, ItemStacks.activatedEnchantments);
		} else {
			this.inventory.setItem(52, notAvailable(ItemStacks.activatedEnchantments));
		}
		this.inventory.setItem(53, ItemStacks.FILLER_GLASS);

		fillMenuWithDeactivatedEnchantments();
	}

	private void fillMenuWithDeactivatedEnchantments() {
		deactivatedEnchantmentsToStrength = new ArrayList<>(
			EnchantmentStacks.getAllDeactivatedEnchantments(
					this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()
				)
				.stream()
				.sorted(Comparator.comparing(e -> {
					if (e.equals(Enchantment.BINDING_CURSE)) {
						return "Curse of Binding";
					} else if (e.equals(Enchantment.VANISHING_CURSE)) {
						return "Curse of Vanishing";
					} else {
						return ItemStackHelper.formatCAPSName(e.getTranslationKey());
					}
				}))
				.collect(Collectors.toList())
		);

		int startIndex = getMaxItemsPerPage() * page;
		int endIndex = Math.min(startIndex + getMaxItemsPerPage(), deactivatedEnchantmentsToStrength.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			Enchantment deactivatedEnchantment = deactivatedEnchantmentsToStrength.get(i);
			ItemStack deactivatedEnchantmentStack = EnchantmentStacks.enchantmentsToItemStack.get(deactivatedEnchantment);

			if (deactivatedEnchantmentStack != null) {
				int inventorySlot = getInventorySlot(slotIndex);
				inventory.setItem(inventorySlot, deactivatedEnchantmentStack);
				slotIndex++;
			}
		}
	}
}