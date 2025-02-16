package de.doppelkool.itemforgegui.Main.CustomItemManager;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum ForgeAction {

	DROP,
	ITEM_FRAME_PLACE,
	LAUNCH,      // -> Anything used with right clicking. xEgg, Snowball, Enderpearl but also xArrows(bow and crossbow)
	EAT,        // -> CONSUMABLE + Cake
	PLACE,      // -> Block and BlockEntitys
	EQUIP,      // -> Through Inventory and quick-equip and xarmorstand
	BURN,       // -> Fire/Lava
	USE_TOOL,   // -> Any tool
	REPAIR,     // -> xSurvival Grid, xCrafting Table, xGrindstone, xAnvil
	UPGRADE,    // -> Smithing Table
	CRAFT,      // -> xCraft with
	ENCHANT,     // -> xEnchant, enchant with
	DISENCHANT,
	RENAME,

	;

}
