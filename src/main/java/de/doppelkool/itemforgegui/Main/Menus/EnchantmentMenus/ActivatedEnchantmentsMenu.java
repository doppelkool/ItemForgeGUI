package de.doppelkool.itemforgegui.Main.Menus.EnchantmentMenus;

import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.EnchantmentMenu.EnchantmentMenuItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.EnchantmentMenu.EnchantmentStacksMap;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

/**
 * Submenu as part of the main function of this plugin.
 * It provides the way to target a specific enchantment and remove it from the item
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ActivatedEnchantmentsMenu extends PaginatedMenu {

	private final int activatedEnchantmentsSlot;

	private HashMap<Enchantment, Integer> activatedEnchantmentsToStrength = new HashMap<>();

	public ActivatedEnchantmentsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		activatedEnchantmentsSlot = this.getSlots() - 1;
	}

	@Override
	public String getMenuName() {
		return "Activated Enchantments";
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
		if(super.pageForward(e.getSlot(), activatedEnchantmentsToStrength.size())) {
			return;
		}

		if (activatedEnchantmentsSlot == e.getSlot()) {
			new DeactivatedEnchantmentsMenu(this.playerMenuUtility)
				.open();
			return;
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			openModifyActivatedEnchantmentMenu(e.getSlot());
			return;
		}
	}

	private void openModifyActivatedEnchantmentMenu(int clickedSlot) {
		Integer itemStackID = this.inventory.getItem(clickedSlot)
			.getItemMeta()
			.getPersistentDataContainer()
			.get(Main.getPlugin().getCustomEnchantmentStackIDKey(), PersistentDataType.INTEGER);
		Enchantment enchantment = EnchantmentStacksMap.itemStackIDToEnchantment.get(itemStackID);

		this.playerMenuUtility.setTargetEnchantment(enchantment);
		new SingleEnchantmentMenu(this.playerMenuUtility)
			.open();
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		if (EnchantmentStacksMap.getAllDeactivatedEnchantments(itemInMainHand)
			.isEmpty()) {
			this.inventory.setItem(activatedEnchantmentsSlot, notAvailable(EnchantmentMenuItems.deactivatedEnchantments));
		} else {
			this.inventory.setItem(activatedEnchantmentsSlot, EnchantmentMenuItems.deactivatedEnchantments);
		}
		this.inventory.setItem(52, GlobalItems.FILLER_GLASS);

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
					return ItemStackModifyHelper.formatCAPSName(e.getTranslationKey());
				}
			})).toList();

		int startIndex = this.maxItemsPerPage * page;
		int endIndex = Math.min(startIndex + this.maxItemsPerPage, enchantmentList.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			Enchantment activatedEnchantment = enchantmentList.get(i);
			Integer strength = activatedEnchantmentsToStrength.get(activatedEnchantment);
			ItemStack activatedEnchantmentStack = EnchantmentStacksMap.enchantmentsToItemStack.get(activatedEnchantment).clone();

			int inventorySlot = getInventorySlot(slotIndex);

			ItemMeta itemMeta = activatedEnchantmentStack.getItemMeta();
			itemMeta.setLore(List.of("Strength: " + strength));
			activatedEnchantmentStack.setItemMeta(itemMeta);

			inventory.setItem(inventorySlot, activatedEnchantmentStack);
			slotIndex++;
		}
	}
}