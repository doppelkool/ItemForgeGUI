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
 * <p>REPAIR           - cannot be repaired in any way. Neither by Ingots/Membranes or similar nor by the same type of item
 * <p>UPGRADE          - cannot be upgraded from diamond to netherite
 * <p>CRAFT            - cannot be used as a crafting material
 * <p>ENCHANT          - cannot be enchanted
 * <p>DISENCHANT       - cannot be disenchanted
 * <p>RENAME           - cannot be renamed
 * <br><br>
 * Coming soon:
 * <p>ALTER_BLOCKS     - cannot be used to alter blocks (E.g. block breaking, wood stripping, ignite TNT)
 * <p>ALTER_ENTITIES   - cannot be used to alter entities (E.g. sheep shearing, dying a sheep)
 * <p>NO_INTERACT      - cannot be used to interact with anything at all (E.g. Shield blocking, glow ink usage on a sign)
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum ForgeAction {

	DROP,
	ITEM_FRAME_PLACE,
	LAUNCH,
	EAT,
	PLACE,
	EQUIP,
	BURN,
	REPAIR,
	UPGRADE,
	CRAFT,
	ENCHANT,
	DISENCHANT,
	RENAME,

	;

}
