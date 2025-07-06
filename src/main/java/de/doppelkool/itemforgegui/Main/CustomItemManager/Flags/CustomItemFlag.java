package de.doppelkool.itemforgegui.Main.CustomItemManager.Flags;

import lombok.Getter;

/**
 * <p>Item with Flag:
 * <p>HIDE_ITEM_FLAGS            - God flag to hide item flags.
 * <p>HIDE_PREVENTION_FLAGS      - God flag to hide prevention flags.
 * <p>HIDE_ARMOR_EFFECTS         - God flag to hide armor effects.
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public enum CustomItemFlag {

	//Hide parts of the items lore
	HIDE_ITEM_FLAGS("Item Flags"),
	HIDE_PREVENTION_FLAGS("Prevention Flags"),
	HIDE_ARMOR_EFFECTS("Armor Effects"),

	;

	private final String itemDescription;

	CustomItemFlag(String itemDescription) {
		this.itemDescription = itemDescription;
	}
}
