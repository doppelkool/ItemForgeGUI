package de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu;

import de.doppelkool.itemforgegui.Main.MenuItems.ItemStacks.MainMenu.SpecialMenu.AttributeModifierMenu.CreateAttributeModifierMenu.AttributeSelectionItems;
import de.doppelkool.itemforgegui.Main.MenuServices.MenuComponents.SlotItemWrapper;
import lombok.Getter;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public enum AttributeCategory {

	DEFENSE_AND_DURABILITY(new SlotItemWrapper.SlotItem(1, AttributeSelectionItems.defenseAndDurability)),
	COMBAT(new SlotItemWrapper.SlotItem(2, AttributeSelectionItems.combat)),
	MOBILITY(new SlotItemWrapper.SlotItem(3, AttributeSelectionItems.mobility)),
	INTERACTION_AND_REACH(new SlotItemWrapper.SlotItem(5, AttributeSelectionItems.interactionAndReach)),
	PERCEPTION_AND_AWARENESS(new SlotItemWrapper.SlotItem(6, AttributeSelectionItems.perceptionAndAwareness)),
	ENV_AND_SURVIVAL(new SlotItemWrapper.SlotItem(7, AttributeSelectionItems.environmentalAndSurvival)),

	;

	private final SlotItemWrapper.SlotItem slotItem;

	AttributeCategory(SlotItemWrapper.SlotItem slotItem) {
		this.slotItem = slotItem;
	}
}
