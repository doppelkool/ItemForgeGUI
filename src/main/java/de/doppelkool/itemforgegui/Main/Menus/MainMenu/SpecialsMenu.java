package de.doppelkool.itemforgegui.Main.Menus.MainMenu;

import com.google.common.collect.Multimap;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.AttributeModifierManager;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Menu;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.Pair;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.PlayerMenuUtility;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.SpecialMenuItems;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.ArmorEffectMenus.SpecialsActivatedArmorEffectsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.ArmorEffectMenus.SpecialsDeactivatedArmorEffectsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.PreventionFlagsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.CustomItemFlagsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.MinecraftItemFlagsMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Modify.ActiveAttributeModifersMenu;
import de.doppelkool.itemforgegui.Main.Menus.MainMenu.SpecialsMenus.AttributeModifierMenus.Create.CreateAttributeModifierMenu;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import de.doppelkool.itemforgegui.Main.Messages.Messages;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Attr;

import java.util.*;


/**
 * Submenu as part of the main function of this plugin.
 * It divides between the item flags that change the items appearance
 * and the prevention flags to prevent specific behaviour
 *
 * @author doppelkool | github.com/doppelkool
 */
public class SpecialsMenu extends Menu {

	private final List<SlotItemWrapper.SlotItemExecute> SLOT_TO_ITEMS = List.of(
		new SlotItemWrapper.SlotItemExecute(10, SpecialMenuItems.itemFlags, () -> new MinecraftItemFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(11, SpecialMenuItems.customItemFlags, () -> new CustomItemFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(13, SpecialMenuItems.preventionFlags, () -> new PreventionFlagsMenu(playerMenuUtility).open()),
		new SlotItemWrapper.SlotItemExecute(15, SpecialMenuItems.armorEffects, () -> armorEffectsClicked()),
		new SlotItemWrapper.SlotItemExecute(16, SpecialMenuItems.attributeModifiers, () -> attributeModifiersClicked())
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
			.runnable()
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

	private void attributeModifiersClicked() {
		Multimap<Attribute, AttributeModifier> attributeModifiers = this.playerMenuUtility.getOwner()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getAttributeModifiers();

		if(attributeModifiers == null || attributeModifiers.isEmpty()) {
			new CreateAttributeModifierMenu(this.playerMenuUtility)
				.open();
			return;
		} else {
			sendWarningForNotSupportedFeature();
			new ActiveAttributeModifersMenu(this.playerMenuUtility)
				.open();
			return;
		}
	}

	public void sendWarningForNotSupportedFeature() {
		Map<Key, Integer> seen = new HashMap<>();

		for (Map.Entry<Attribute, Collection<AttributeModifier>> entry : this.playerMenuUtility.getOwner()
			.getInventory()
			.getItemInMainHand()
			.getItemMeta()
			.getAttributeModifiers()
			.asMap()
			.entrySet())
		{
			for (AttributeModifier modifier : entry.getValue()) {
				Key key = new Key(entry.getKey(), modifier.getSlotGroup(), modifier.getOperation());
				if (seen.merge(key, 1, Integer::sum) > 1) {
					this.playerMenuUtility.getOwner().sendMessage(MessageManager.format(Messages.ATTRIBUTE_MODIFIER_WARNING_WITH_SAME_DETAILS, true));
					return;
				}
			}
		}
	}

	private record Key(Attribute attribute, EquipmentSlotGroup slotGroup, AttributeModifier.Operation operation) {
		@Override
		public int hashCode() {
			return Objects.hash(attribute, slotGroup, operation);
		}
	}

	@Override
	public void setMenuItems() {
		addMenuBorder();
		SLOT_TO_ITEMS.forEach((slotItem) -> this.inventory.setItem(slotItem.slot(), slotItem.item()));
		setFillerGlass();
	}
}
