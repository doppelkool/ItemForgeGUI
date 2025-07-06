package de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.EnchantmentStacks;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.notAvailable;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to target a specific enchantment and remove it from the item
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ActivatedEnchantmentsMenu extends PaginatedMenu {

	private HashMap<Enchantment, Integer> activatedEnchantmentsToStrength = new HashMap<>();

	public ActivatedEnchantmentsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Activated Enchantments";
	}

	@Override
	public int getSlots() {
		return 9 * 6;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot())) {
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
			if (nextPageStartIndex < activatedEnchantmentsToStrength.size()) {
				page++;
				super.open();
			}
			return;
		}

		if (e.getSlot() == 53) {
			new DeactivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			int slot = e.getSlot();
			ItemStack item = this.inventory.getItem(slot);
			PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
			Integer itemStackID = persistentDataContainer.get(Main.getPlugin().getCustomEnchantmentStackIDKey(), PersistentDataType.INTEGER);
			Enchantment enchantment = EnchantmentStacks.itemStackIDToEnchantment.get(itemStackID);
			this.playerMenuUtility.setTargetEnchantment(enchantment);

			new SingleEnchantmentMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		if (EnchantmentStacks.getAllDeactivatedEnchantments(itemInMainHand)
			.isEmpty()) {
			this.inventory.setItem(53, notAvailable(ItemStacks.deactivatedEnchantments));
		} else {
			this.inventory.setItem(53, ItemStacks.deactivatedEnchantments);
		}
		this.inventory.setItem(52, ItemStacks.FILLER_GLASS);

		fillMenuWithActivatedEnchantments();
	}

	private void fillMenuWithActivatedEnchantments() {
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		ItemMeta itemInMainHandItemMeta = itemInMainHand.getItemMeta();
		Map<Enchantment, Integer> enchants = itemInMainHandItemMeta.getEnchants();
		this.activatedEnchantmentsToStrength = new HashMap<>(enchants);

		List<Enchantment> enchantmentList = activatedEnchantmentsToStrength.keySet()
			.stream()
			.sorted(Comparator.comparing(e -> {
				if (e.equals(Enchantment.BINDING_CURSE)) {
					return "Curse of Binding";
				} else if (e.equals(Enchantment.VANISHING_CURSE)) {
					return "Curse of Vanishing";
				} else {
					return ItemStackHelper.formatCAPSName(e.getTranslationKey());
				}
			})).toList();

		int startIndex = getMaxItemsPerPage() * page;
		int endIndex = Math.min(startIndex + getMaxItemsPerPage(), enchantmentList.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			Enchantment activatedEnchantment = enchantmentList.get(i);
			Integer strength = activatedEnchantmentsToStrength.get(activatedEnchantment);
			ItemStack activatedEnchantmentStack = EnchantmentStacks.enchantmentsToItemStack.get(activatedEnchantment).clone();

			int inventorySlot = getInventorySlot(slotIndex);

			ItemMeta itemMeta = activatedEnchantmentStack.getItemMeta();
			itemMeta.setLore(List.of("Strength: " + strength));
			activatedEnchantmentStack.setItemMeta(itemMeta);

			inventory.setItem(inventorySlot, activatedEnchantmentStack);
			slotIndex++;
		}
	}
}