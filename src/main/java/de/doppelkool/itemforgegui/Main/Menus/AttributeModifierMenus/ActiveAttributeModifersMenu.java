package de.doppelkool.itemforgegui.Main.Menus.AttributeModifierMenus;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.CustomItemManager.AttributeModifierManager;
import de.doppelkool.itemforgegui.Main.Main;
import de.doppelkool.itemforgegui.Main.MenuComponents.PaginatedMenu;
import de.doppelkool.itemforgegui.Main.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeItem;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeModifierItems;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.AttributeRegistry;
import de.doppelkool.itemforgegui.Main.Menus.SpecialsMenu;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.doppelkool.itemforgegui.Main.MenuItems.ItemStackCreateHelper.notAvailable;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ActiveAttributeModifersMenu extends PaginatedMenu {

	private final int addAttributeModifierItemSlot = this.getSlots() - 1;

	public ActiveAttributeModifersMenu(PlayerMenuUtility playerMenuUtility) {
		super(playerMenuUtility);
	}

	@Override
	public String getMenuName() {
		return "Active Attribute Modifiers";
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
		Multimap<Attribute, AttributeModifier> attributeModifiers = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta().getAttributeModifiers();
		if(super.pageForward(e.getSlot(), attributeModifiers.size())) {
			return;
		}

		if (isCreateNewAttributeItemAvailable() && addAttributeModifierItemSlot == e.getSlot()) {
			this.playerMenuUtility.setAttributeStorage(new PlayerMenuUtility.AttributeStorage());
			new CreateAttributeModifierMenu(this.playerMenuUtility)
				.open();
		}

		if (emptyInvSpace.contains(e.getSlot())) {
			openModifyClickedAttributeModifierMenu(e.getSlot());
		}
	}

	private void openModifyClickedAttributeModifierMenu(int clickedSlot) {
		String attributeString = this.inventory.getItem(clickedSlot)
			.getItemMeta()
			.getPersistentDataContainer()
			.get(Main.getPlugin().getCustomAttributeModifierKey_AttributeString(), PersistentDataType.STRING);
		Attribute attribute = AttributeRegistry.REGISTRY.getByKeyString(attributeString);

		PlayerMenuUtility.ModifyAttributeStorage modifyAttributeStorage = new PlayerMenuUtility.ModifyAttributeStorage();
		modifyAttributeStorage.setAttribute(attribute);
		this.playerMenuUtility.setModifyAttributeStorage(modifyAttributeStorage);

		new ModifyAttributeModifierMenu(this.playerMenuUtility)
			.open();
	}

	@Override
	public void setMenuItems() {
		addPaginatedItems();
		addCustomMenuFillingForEffects();

		if (isCreateNewAttributeItemAvailable()) {
			this.inventory.setItem(addAttributeModifierItemSlot, AttributeModifierItems.addNewAttributeModifier);
		} else {
			this.inventory.setItem(addAttributeModifierItemSlot, notAvailable(AttributeModifierItems.addNewAttributeModifier));
		}

		fillMenuWithActivatedAttributes();
	}

	private boolean isCreateNewAttributeItemAvailable() {
		return AttributeModifierManager.canMore(this.playerMenuUtility.getOwner().getInventory().getItemInMainHand().getItemMeta());
	}

	private void fillMenuWithActivatedAttributes() {
		ItemStack itemInMainHand = this.playerMenuUtility.getOwner().getInventory().getItemInMainHand();
		Map<Attribute, List<AttributeModifier>> entries = itemInMainHand.getItemMeta().getAttributeModifiers().asMap()
			.entrySet()
			.stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				e -> new ArrayList<>(e.getValue())
			));

		int startIndex = this.maxItemsPerPage * page;
		int endIndex = Math.min(startIndex + this.maxItemsPerPage, entries.size());
		int slotIndex = 0;

		for (int i = startIndex; i < endIndex; i++) {
			Map.Entry<Attribute, List<AttributeModifier>> attributeModifierEntry = entries.entrySet().stream().toList().get(i);

			AttributeItem attributeItemFromAttribute = AttributeRegistry.REGISTRY.getItem(attributeModifierEntry.getKey());
			ItemStack attributeItemStackclone = attributeItemFromAttribute.item().clone();

			AttributeModifierManager.insertValues(
				attributeItemStackclone,
				attributeItemFromAttribute.attribute(),
				attributeModifierEntry
			);

			ItemStackCreateHelper.modifyAttributeStringInPDC(attributeItemStackclone, attributeItemFromAttribute.attribute());

			int inventorySlot = getInventorySlot(slotIndex);
			inventory.setItem(inventorySlot, attributeItemStackclone);
			slotIndex++;
		}
	}
}
