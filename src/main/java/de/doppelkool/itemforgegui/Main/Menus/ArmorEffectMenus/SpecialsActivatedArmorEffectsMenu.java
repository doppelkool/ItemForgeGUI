package de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.ArmorEffectItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.ArmorEffectStacksMap;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsActivatedArmorEffectsMenu extends PaginatedMenu {

	private final int switchToDeactivatedArmorEffectsItemSlot;

	private HashMap<PotionEffectType, Integer> activatedPotionEffectTypesToStrength = new HashMap<>();

	public SpecialsActivatedArmorEffectsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		switchToDeactivatedArmorEffectsItemSlot = this.getSlots() - 1;
	}

	@Override
	public String getMenuName() {
		return "Activated ArmorEffects";
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
		if(super.handleBack(e.getSlot(), SpecialsMenu::new)) {
			return;
		}
		if(super.pageBack(e.getSlot())) {
			return;
		}
		if(super.pageForward(e.getSlot(), activatedPotionEffectTypesToStrength.size())) {
			return;
		}

		if (switchToDeactivatedArmorEffectsItemSlot == e.getSlot()) {
			new SpecialsDeactivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			int slot = e.getSlot();
			ItemStack item = this.inventory.getItem(slot);
			PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
			Integer itemStackID = persistentDataContainer.get(Main.getPlugin().getCustomArmorEffectsKeyStackIDKey(), PersistentDataType.INTEGER);
			PotionEffectType potionEffectType = ArmorEffectStacksMap.itemStackIDToPotionEffectType.get(itemStackID);

			if (ConfigManager.getInstance().isDifferCappedEffectsEnabled() && ArmorEffectManager.isCappedEffect(potionEffectType)) {
				ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
				ArmorEffectManager.removeArmorEffect(itemInMainHand, potionEffectType);
				new ItemInfoManager(itemInMainHand).updateItemInfo();

				if (ArmorEffectManager.hasArmorEffects(itemInMainHand)) {
					new SpecialsActivatedArmorEffectsMenu(this.playerMenuUtility)
						.open();
				} else {
					new SpecialsDeactivatedArmorEffectsMenu(this.playerMenuUtility)
						.open();
				}
				return;
			}

			this.playerMenuUtility.setTargetPotionEffectType(potionEffectType);
			new SingleArmorEffectTypeMenu(this.playerMenuUtility)
				.open();
		}
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		if (!ArmorEffectManager.getAllDeactivatedPotionEffectTypes(itemInMainHand)
			.isEmpty()) {
			this.inventory.setItem(switchToDeactivatedArmorEffectsItemSlot, ArmorEffectItems.deactivatedArmorEffects);
		} else {
			this.inventory.setItem(switchToDeactivatedArmorEffectsItemSlot, notAvailable(ArmorEffectItems.deactivatedArmorEffects));
		}
		this.inventory.setItem(52, GlobalItems.FILLER_GLASS);

		fillMenuWithActivatedEnchantments();
	}

	private void fillMenuWithActivatedEnchantments() {
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		Map<PotionEffectType, Integer> enchants = ArmorEffectManager.getAllActivatedPotionEffectTypesAsMap(itemInMainHand);
		this.activatedPotionEffectTypesToStrength = new HashMap<>(enchants);

		List<PotionEffectType> enchantmentList = activatedPotionEffectTypesToStrength.keySet()
			.stream()
			.sorted(Comparator.comparing(e -> {
				if (e.equals(PotionEffectType.SPEED)) {
					return "Swiftness";
				} else {
					return ItemStackModifyHelper.formatCAPSName(e.getTranslationKey());
				}
			})).toList();

		int startIndex = this.maxItemsPerPage * page;
		int endIndex = Math.min(startIndex + this.maxItemsPerPage, enchantmentList.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			PotionEffectType potionEffectType = enchantmentList.get(i);
			Integer strength = activatedPotionEffectTypesToStrength.get(potionEffectType);
			ItemStack activatedPotionEffectTypesStack = ArmorEffectStacksMap.potionEffectTypeToItemStack.get(potionEffectType).clone();

			int inventorySlot = getInventorySlot(slotIndex);

			ItemMeta itemMeta = activatedPotionEffectTypesStack.getItemMeta();
			itemMeta.setLore(List.of("Strength: " + strength));
			activatedPotionEffectTypesStack.setItemMeta(itemMeta);

			inventory.setItem(inventorySlot, activatedPotionEffectTypesStack);
			slotIndex++;
		}
	}
}

