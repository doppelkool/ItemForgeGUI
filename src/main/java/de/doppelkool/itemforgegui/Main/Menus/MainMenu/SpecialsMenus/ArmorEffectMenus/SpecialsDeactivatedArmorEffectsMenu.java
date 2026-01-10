package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.ArmorEffectMenus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.ArmorEffectItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.ArmorEffectStacksMap;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuServices.ItemStackCreateHelper.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsDeactivatedArmorEffectsMenu extends PaginatedMenu {

	private final int switchToActivatedArmorEffectsItemSlot;

	private ArrayList<PotionEffectType> deactivatedPotionEffectTypesToStrength = new ArrayList<>();

	public SpecialsDeactivatedArmorEffectsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
		switchToActivatedArmorEffectsItemSlot = this.getSlots() - 2;
	}

	@Override
	public String getMenuName() {
		return "Deactivated ArmorEffects";
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			return;
		}
		if (super.handleBack(e.getSlot(), null, SpecialsMenu::new)) {
			return;
		}
		if(super.pageBack(e.getSlot())) {
			return;
		}
		if(super.pageForward(e.getSlot(), deactivatedPotionEffectTypesToStrength.size())) {
			return;
		}

		if (isActivatedArmorEffectsItemAvailable() && switchToActivatedArmorEffectsItemSlot == e.getSlot()) {
			openActivatedArmorEffectsMenu();
			return;
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			int slot = e.getSlot();
			ItemStack item = this.inventory.getItem(slot);
			PotionEffectType potionEffectType = ArmorEffectStacksMap.potionEffectTypeToItemStack.entrySet().stream()
				.filter(entry -> entry.getValue().equals(item))
				.map(Map.Entry::getKey)
				.findFirst()
				.get(); //Handled every other case

			if (ConfigManager.getInstance().isDifferCappedEffectsEnabled() && ArmorEffectManager.isCappedEffect(potionEffectType)) {
				ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
				ArmorEffectManager.addArmorEffect(itemInMainHand, potionEffectType, 1);
				new ItemInfoManager(itemInMainHand).updateItemInfo();
				new SpecialsActivatedArmorEffectsMenu(playerMenuUtility)
					.open();
				return;
			}

			this.playerMenuUtility.setTargetPotionEffectType(potionEffectType);
			new SingleArmorEffectTypeMenu(this.playerMenuUtility)
				.open();
			return;
		}

	}

	private void openActivatedArmorEffectsMenu() {
		new SpecialsActivatedArmorEffectsMenu(this.playerMenuUtility)
			.open();
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		if (isActivatedArmorEffectsItemAvailable()) {
			this.inventory.setItem(switchToActivatedArmorEffectsItemSlot, ArmorEffectItems.activatedArmorEffects);
		} else {
			this.inventory.setItem(switchToActivatedArmorEffectsItemSlot, notAvailable(ArmorEffectItems.activatedArmorEffects));
		}
		this.inventory.setItem(53, GlobalItems.FILLER_GLASS);

		fillMenuWithDeactivatedArmorEffects();
	}

	private boolean isActivatedArmorEffectsItemAvailable() {
		ItemStack itemInMainHand = this.playerMenuUtility.getItemInHand().get();
		ArrayList<ForgeArmorEffect> allDeactivatedPotionEffectTypes = ArmorEffectManager.getAllActivatedPotionEffectTypesAsList(itemInMainHand);
		return !allDeactivatedPotionEffectTypes.isEmpty();
	}

	private void fillMenuWithDeactivatedArmorEffects() {
		deactivatedPotionEffectTypesToStrength = ArmorEffectManager.getAllDeactivatedPotionEffectTypes(
				this.playerMenuUtility.getItemInHand().get()
			)
			.stream()
			.sorted(Comparator.comparing(e -> {
				if (e.equals(PotionEffectType.SPEED)) {
					return "Swiftness";
				} else {
					return ItemStackModifyHelper.formatTranslationalNames(e.getTranslationKey());
				}
			})).collect(Collectors.toCollection(ArrayList::new));

		int startIndex = this.maxItemsPerPage * page;
		int endIndex = Math.min(startIndex + this.maxItemsPerPage, deactivatedPotionEffectTypesToStrength.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			PotionEffectType deactivatedPotionEffectTypes = deactivatedPotionEffectTypesToStrength.get(i);
			ItemStack deactivatedPotionEffectTypesStack = ArmorEffectStacksMap.potionEffectTypeToItemStack.get(deactivatedPotionEffectTypes);

			if (deactivatedPotionEffectTypesStack != null) {
				int inventorySlot = getInventorySlot(slotIndex);
				inventory.setItem(inventorySlot, deactivatedPotionEffectTypesStack);
				slotIndex++;
			}
		}
	}
}
