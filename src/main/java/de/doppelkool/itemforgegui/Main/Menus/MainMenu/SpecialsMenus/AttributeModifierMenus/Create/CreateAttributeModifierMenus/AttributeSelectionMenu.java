package de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenus;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.GlobalItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeCategory;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeItem;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistry;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuServices.ItemStackModifyHelper;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.ConfirmableMenu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class AttributeSelectionMenu extends ConfirmableMenu {

	private AttributeCategory selectedCategoryInMenu;
	private AttributeItem selectedAttributeInMenu;

	private static final Set<Integer> blackPaneSlots = Set.of(
		0, 8,
		9, 10, 11, 12, 13, 14, 15, 16, 17,
		36, 37, 38, 39, 40, 41, 42, 43, 44,
		47, 48, 49, 50, 51, 52
	);

	private static final Set<Integer> attributeItemSlots = Set.of(
		18, 19, 20, 21, 22, 23, 24, 25, 26,
		27, 28, 29, 30, 31, 32, 33, 34, 35
	);

	public AttributeSelectionMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility, 53);
		loadAttributeAndCategoryFromUtility();
	}

	@Override
	public String getMenuName() {
		return "Attribute Selection: ";
	}

	@Override
	public int getSlots() {
		return 9 * 6;
	}

	@Override
	public void handleMenu(InventoryClickEvent e) {
		if (super.handleClose(e.getSlot())) {
			this.playerMenuUtility.setAttributeStorage(null);
			return;
		}
		if (super.handleBack(
			e.getSlot(),
			() -> this.playerMenuUtility.getAttributeStorage().setAttribute(null),
			CreateAttributeModifierMenu::new)) {
			return;
		}
		if (super.handleConfirm(e.getSlot(),
			() -> this.playerMenuUtility.getAttributeStorage().setAttribute(selectedAttributeInMenu.getAttribute()),
			CreateAttributeModifierMenu::new)) {
			return;
		}

		Arrays.stream(AttributeCategory.values())
			.filter(attributeCategory -> attributeCategory.getSlotItem().slot() == e.getSlot())
			.findAny()
			.ifPresentOrElse(attributeCategory -> {
				this.selectedCategoryInMenu = attributeCategory;
				loadCategories();
				loadItemsForSelectedCategory();
			}, () -> {
				attributeClicked(e.getSlot());
			});

		updateConfirmSlot();
	}

	private void attributeClicked(int slot) {
		AttributeRegistry.REGISTRY.getItems(this.selectedCategoryInMenu).stream()
			.filter((attributeItem) -> attributeItem.getSlot() == slot).findAny()
			.ifPresent(attributeItem -> this.selectedAttributeInMenu = attributeItem);
		loadItemsForSelectedCategory();
	}

	@Override
	protected boolean isConfirmable() {
		return selectedAttributeInMenu != null;
	}

	@Override
	protected ItemStack getConfirmableItem() {
		return isConfirmable()
			? AttributeSelectionItems.confirmSlots.clone()
			: AttributeSelectionItems.confirmSlots_deactivated.clone();
	}

	@Override
	public void setMenuItems() {
		loadMenuSkeleton();
		loadCategories();
		loadItemsForSelectedCategory();
	}

	private void loadMenuSkeleton() {
		for (int slot : blackPaneSlots) {
			inventory.setItem(slot, GlobalItems.FILLER_GLASS);
		}

		inventory.setItem(45, GlobalItems.closeInventory);
		inventory.setItem(46, GlobalItems.backInventory);
		inventory.setItem(53, getConfirmableItem());
	}

	private void loadCategories() {
		Arrays.stream(AttributeCategory.values()).forEach((attributeCategory) -> {
			ItemStack item = attributeCategory.getSlotItem().item().clone();
			if(this.selectedCategoryInMenu == attributeCategory) {
				ItemStackModifyHelper.setGlow(item, true);
			}
			this.inventory.setItem(attributeCategory.getSlotItem().slot(), item);
		});
	}

	private void loadItemsForSelectedCategory() {
		emptyAttributeItems();

		Multimap<Attribute, AttributeModifier> attributeModifiers = this.playerMenuUtility.getItemInHand().get()
			.getItemMeta()
			.getAttributeModifiers();
		Set<Attribute> alreadyAppliedAttributes = attributeModifiers == null || attributeModifiers.isEmpty() ? new HashSet<>() : attributeModifiers
			.asMap()
			.keySet();

		AttributeRegistry.REGISTRY.getItems_AlreadyAppliedAreBarriers(this.selectedCategoryInMenu, alreadyAppliedAttributes)
			.forEach((attributeItem) -> {
				ItemStack item = attributeItem.getItem().clone();
				if(this.selectedAttributeInMenu != null && attributeItem.getAttribute() == this.selectedAttributeInMenu.getAttribute()) {
					ItemStackModifyHelper.setGlow(item, true);
				}
				this.inventory.setItem(attributeItem.getSlot(), item);
			}
		);
	}

	private void emptyAttributeItems() {
		attributeItemSlots.forEach(attributeItem -> this.inventory.clear(attributeItem));
	}

	private void loadAttributeAndCategoryFromUtility() {
		Attribute attribute = this.playerMenuUtility.getAttributeStorage().getAttribute();
		if(attribute != null) {
			this.selectedCategoryInMenu = AttributeRegistry.REGISTRY.getCategory(attribute);
			this.selectedAttributeInMenu = AttributeRegistry.REGISTRY.getItem(attribute);
		} else {
			this.selectedCategoryInMenu = AttributeCategory.DEFENSE_AND_DURABILITY;
			this.selectedAttributeInMenu = null;
		}
	}
}
