package de.doppelkool.itemforgegui.Main.CustomItemManager;

/**
 *
 * <p>Item with Flag:
 * <p>DROP             - cannot be dropped
 * <p>ITEM_FRAME_PLACE - cannot be placed in the two types of item frames
 * <p>LAUNCH           - cannot be launched by hand or bow or crossbow (Including: Snowball, Egg, Enderpearl, Trident, Fire Rockets, Splash-Potions and lingering Potion)
 * <p>EAT              - cannot be eaten. Cake is placable, but not edible. If Cake with PF is mined, it drops
 * <p>PLACE            - cannot be placed.
 * <p>EQUIP            - cannot be equipped on the player. Equipping on an armorstand is also denied
 * <p>BURN             - cannot be burned by fire or lava.
 * <p>USE_TOOL         - are tools, that cannot be used. (To be continued what exactly happens)
 * <p>REPAIR           - cannot be repaired in any way. Neither by Ingots/Membranes or similar nor by the same type of item
 * <p>UPGRADE          - cannot be upgraded from diamond to netherite
 * <p>CRAFT            - cannot be used as a crafting material
 * <p>ENCHANT          - cannot be enchanted
 * <p>DISENCHANT       - cannot be disenchanted
 * <p>RENAME           - cannot be renamed
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum ForgeAction {

	DROP,
	ITEM_FRAME_PLACE,
	LAUNCH,
	EAT,
	PLACE,
	EQUIP,      //Missing equip prevention through users Inventory and quick-equip through rightclick
	BURN,

	ALTER_BLOCKS, // for breaking blocks(BlockBreakEvent)? Axes aswell but + wood stripping(PlayerInteractEvent )?
	//Buckets to cannot be filled or emptied? FNS only ignite TNT(BlockIgniteEvent), Fire(BlockPlaceEvent)?

	ALTER_ENTITIES, // hitting another entity entirely; Shears for snow golem and sheeps; dyes to dye a sheep

	NO_INTERACT, // Rod fishing, Shield blocking, glow ink for signs; Carrot on a Stick to not control pigs



	USE_TOOL,   //Missing use prevention of every tool

	REPAIR,
	UPGRADE,
	CRAFT,
	ENCHANT,     //Missing prevention to use the item to enchant with. Enchanted Books through Anvil; Lapislazuli in enchanting table
	DISENCHANT,
	RENAME,

	;

}
