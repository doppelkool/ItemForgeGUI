package de.doppelkool.itemforgegui.Main.CustomItemManager;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum ForgeAction {

	ITEM_DROP,
	ITEM_FRAME_PLACE,
	LAUNCH,      // -> Anything used with right clicking. Egg, Snowball, Enderpearl but also Arrows through bow and crossbow
	EAT,        // -> CONSUMABLE + Cake
	PLACE,      // -> Block and BlockEntitys
	EQUIP,      // -> Through Inventory and quick-equip and armorstand
	BURN,       // -> Fire/Lava
	USE_TOOL,   // -> Any tool
	REPAIR,     // -> Survival Grid, Crafting Table, Grindstone, Anvil, Enchantment Table
	UPGRADE,    // -> Smithing Table

	;

}
