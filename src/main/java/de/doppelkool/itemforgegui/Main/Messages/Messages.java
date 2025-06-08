package de.doppelkool.itemforgegui.Main.Messages;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum Messages {
	PREFIX("prefix"),

	EDIT_COMMAND_AS_CONSOLE("edit-command.as-console"),
	EDIT_COMMAND_NO_PERMISSIONS("edit-command.no-permissions"),
	EDIT_COMMAND_EMPTY_ITEM("edit-command.empty-item"),

	SIGN_EDITOR_NOT_PLACED_BLOCK_BLOCKADE("sign-editor.not-placed-block-blockade"),

	SIGN_EDITOR_EDIT_AMOUNT_INFORMATION("sign-editor.edit.amount.information"),
	SIGN_EDITOR_EDIT_AMOUNT_EMPTY_INPUT("sign-editor.edit.amount.empty-input"),
	SIGN_EDITOR_EDIT_AMOUNT_INVALID_INPUT("sign-editor.edit.amount.invalid-input"),

	SIGN_EDITOR_EDIT_DURABILITY_EMPTY_INPUT("sign-editor.edit.durability.empty-input"),
	SIGN_EDITOR_EDIT_DURABILITY_INVALID_INPUT("sign-editor.edit.durability.invalid-input"),

	SIGN_EDITOR_EDIT_UNIQUEID_INFORMATION("sign-editor.edit.uniqueID.information"),
	SIGN_EDITOR_EDIT_UNIQUEID_EMPTY_INPUT("sign-editor.edit.uniqueID.empty-input"),
	SIGN_EDITOR_EDIT_UNIQUEID_INPUT_TO_LONG("sign-editor.edit.uniqueID.input-to-long"),

	SIGN_EDITOR_EDIT_ARMOR_EFFECT_INFORMATION("sign-editor.edit.armor-effect.information"),
	SIGN_EDITOR_EDIT_ARMOR_EFFECT_EMPTY_INPUT("sign-editor.edit.armor-effect.empty-input"),
	SIGN_EDITOR_EDIT_ARMOR_EFFECT_INVALID_INPUT("sign-editor.edit.armor-effect.invalid-input"),

	SIGN_EDITOR_EDIT_ENCHANTMENT_INFORMATION("sign-editor.edit.enchantment.information"),
	SIGN_EDITOR_EDIT_ENCHANTMENT_EMPTY_INPUT("sign-editor.edit.enchantment.empty-input"),
	SIGN_EDITOR_EDIT_ENCHANTMENT_INVALID_INPUT("sign-editor.edit.enchantment.invalid-input"),

	BOOK_EDITOR_INFORMATION("book-editor.information"),
	BOOK_EDITOR_LORE_BOOK_DROP_DISALLOWED("book-editor.lore-book-drop-disallowed"),

	ACTION_PREVENTED_ITEM_DROP("action-prevented.item-drop"),
	ACTION_PREVENTED_ITEM_PUT_IN_ITEM_FRAME("action-prevented.item-put-in-item-frame"),
	ACTION_PREVENTED_ITEM_PUT_ON_ARMOR_STAND("action-prevented.item-put-on-armor-stand"),
	ACTION_PREVENTED_ITEM_THROW("action-prevented.item-throw"),
	ACTION_PREVENTED_ITEM_ENCHANT("action-prevented.item-enchant"),
	ACTION_PREVENTED_ITEM_UPGRADE("action-prevented.item-upgrade"),
	ACTION_PREVENTED_ITEM_CONSUMPTION("action-prevented.item-consumption"),
	ACTION_PREVENTED_CAKE_CONSUMPTION("action-prevented.cake-consumption"),
	ACTION_PREVENTED_CAKE_WITH_PF_PLACED("action-prevented.cake-with-pf-placed"),
	ACTION_PREVENTED_CAKE_WITH_PF_DESTROYED("action-prevented.cake-with-pf-destroyed"),
	ACTION_PREVENTED_ARMOR_EQUIP("action-prevented.armor-equip"),
	ACTION_PREVENTED_HORSE_ARMOR_SADDLE_EQUIP("action-prevented.horse-armor-saddle-equip"),
	ACTION_PREVENTED_BLOCK_PLACE("action-prevented.block-place"),
	ACTION_PREVENTED_ENTITY_PLACE("action-prevented.entity-place"),
	ACTION_PREVENTED_ARROW_SHOOT("action-prevented.arrow-shoot"),
	ACTION_PREVENTED_ANVIL_USAGE("action-prevented.anvil-usage"),
	ACTION_PREVENTED_GRINDSTONE_USAGE("action-prevented.grindstone-usage"),

	MISC_COPY_UNIQUE_IDENTIFIER_INFORMATION("misc.copy-unique-identifier.information"),
	MISC_COPY_UNIQUE_IDENTIFIER_COPY_BUTTON("misc.copy-unique-identifier.copy-button"),
	MISC_COPY_UNIQUE_IDENTIFIER_MANUAL_BUTTON("misc.copy-unique-identifier.manual-button"),

	;

	private final String key;

	Messages(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
