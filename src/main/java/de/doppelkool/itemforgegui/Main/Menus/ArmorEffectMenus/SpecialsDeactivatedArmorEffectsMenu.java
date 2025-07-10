package de.doppelkool.itemforgegui.Main.Menus.ArmorEffectMenus;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ItemInfoManager;
import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.ArmorEffectItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.ArmorEffectMenu.PotionEffectStacks;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsDeactivatedArmorEffectsMenu extends PaginatedMenu {

	private ArrayList<PotionEffectType> deactivatedPotionEffectTypesToStrength = new ArrayList<>();

	public SpecialsDeactivatedArmorEffectsMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Deactivated ArmorEffects";
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
		if (super.handleBack(e.getSlot(), SpecialsMenu::new)) {
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
			if (nextPageStartIndex < deactivatedPotionEffectTypesToStrength.size()) {
				page++;
				super.open();
			}
			return;
		}

		if (e.getSlot() == 52) {
			new SpecialsActivatedArmorEffectsMenu(this.playerMenuUtility)
				.open();
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			int slot = e.getSlot();
			ItemStack item = this.inventory.getItem(slot);
			PotionEffectType potionEffectType = PotionEffectStacks.potionEffectTypeToItemStack.entrySet().stream()
				.filter(entry -> entry.getValue().equals(item))
				.map(Map.Entry::getKey)
				.findFirst()
				.get(); //Handled every other case

			if (ConfigManager.getInstance().isDifferCappedEffectsEnabled() && ArmorEffectManager.isCappedEffect(potionEffectType)) {
				ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
				ArmorEffectManager.addArmorEffect(itemInMainHand, potionEffectType, 1);
				new ItemInfoManager(itemInMainHand).updateItemInfo();
				new SpecialsActivatedArmorEffectsMenu(playerMenuUtility)
					.open();
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

		ArrayList<ForgeArmorEffect> allDeactivatedPotionEffectTypes = ArmorEffectManager.getAllActivatedPotionEffectTypesAsList(itemInMainHand);

		if (!allDeactivatedPotionEffectTypes.isEmpty()) {
			this.inventory.setItem(52, ArmorEffectItems.activatedArmorEffects);
		} else {
			this.inventory.setItem(52, notAvailable(ArmorEffectItems.activatedArmorEffects));
		}
		this.inventory.setItem(53, GlobalItems.FILLER_GLASS);

		fillMenuWithDeactivatedArmorEffects();
	}

	private void fillMenuWithDeactivatedArmorEffects() {
		deactivatedPotionEffectTypesToStrength = ArmorEffectManager.getAllDeactivatedPotionEffectTypes(
				this.playerMenuUtility.getOwner().getInventory().getItemInMainHand()
			)
			.stream()
			.sorted(Comparator.comparing(e -> {
				if (e.equals(PotionEffectType.SPEED)) {
					return "Swiftness";
				} else {
					return ItemStackModifyHelper.formatCAPSName(e.getTranslationKey());
				}
			})).collect(Collectors.toCollection(ArrayList::new));

		int startIndex = getMaxItemsPerPage() * page;
		int endIndex = Math.min(startIndex + getMaxItemsPerPage(), deactivatedPotionEffectTypesToStrength.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			PotionEffectType deactivatedPotionEffectTypes = deactivatedPotionEffectTypesToStrength.get(i);
			ItemStack deactivatedPotionEffectTypesStack = PotionEffectStacks.potionEffectTypeToItemStack.get(deactivatedPotionEffectTypes);

			if (deactivatedPotionEffectTypesStack != null) {
				int inventorySlot = getInventorySlot(slotIndex);
				inventory.setItem(inventorySlot, deactivatedPotionEffectTypesStack);
				slotIndex++;
			}
		}
	}
}
