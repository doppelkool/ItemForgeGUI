package de.doppelkool.itemforgegui.Main.CustomItemManager;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum ForgeAction {

	DROP,
	ITEM_FRAME_PLACE,
	LAUNCH,      // -> Anything used with right clicking. Egg, Snowball, Enderpearl but also Arrows through bow and crossbow
	EAT,        // -> CONSUMABLE + Cake
	PLACE,      // -> Block and BlockEntitys
	EQUIP,      // -> Through Inventory and quick-equip and armorstand
	BURN,       // -> Fire/Lava
	USE_TOOL,   // -> Any tool
	REPAIR,     // -> xSurvival Grid, xCrafting Table, xGrindstone, Anvil, Enchantment Table
	UPGRADE,    // -> Smithing Table
	CRAFT,      // -> Craft with
	ENCHANT,     // -> Enchant or enchant with
	DISENCHANT,

	;

}
