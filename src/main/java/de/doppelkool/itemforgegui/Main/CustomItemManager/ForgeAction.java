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
 * <p>ALTER_BLOCKS     - cannot be used to alter blocks (E.g. block breaking, wood stripping, ignite TNT)
 * <p>ALTER_ENTITIES   - cannot be used to alter entities (E.g. sheep shearing, dying a sheep)
 * <p>NO_INTERACT      - cannot be used to interact with anything at all (E.g. Shield blocking, glow ink usage on a sign)
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
	EQUIP,
	BURN,

	//region ToDo
	ALTER_BLOCKS,
	/*
		- Breaking Blocks (Tools, TNT explosion, bed explosion in end and nether) (BlockBreakEvent, EntityExplodeEvent)
		- Pistons (BlockPistonExtendEvent, BlockPistonRetractEvent)
		- Filling and emptying buckets (PlayerBucketFillEvent, PlayerBucketEmptyEvent)
		- Wood stripping with axe (PlayerInteractEvent)
		- Dirt/Grass to Farmland with hoe (PlayerInteractEvent)
		- Coarse dirt to dirt with hoe (PlayerInteractEvent)
		- ** to Path blocks with shovel (PlayerInteractEvent)
		- Carving pumpkin with shears (PlayerInteractEvent)
		- Removing snow (BlockBreakEvent, PlayerInteractEvent)
		- FNS to ignite TNT/portals and "place" fire (BlockIgniteEvent, PlayerInteractEvent)
		- Observer to dont send a signal (BlockRedstoneEvent)
		- Saplings/Crops/Vines/ dont grow (BlockGrowEvent, StructureGrowEvent)
		- Sculk spreading (BlockSpreadEvent)
	 */

	ALTER_ENTITIES,
	/*
		- Damaging an entity (EntityDamageEvent, EntityDamageByEntityEvent)
		- Projectile hitting an entity (ProjectileHitEvent, EntityDamageByEntityEvent)
		- Rod hook an entity (?, add fishing here or on NO_INTERACT?) (PlayerFishEvent)
		- Shear a sheep (PlayerInteractEntityEvent)
		- Shear a snow golem (PlayerInteractEntityEvent)
		- Shear a mushroom cow (PlayerInteractEntityEvent)
		- Using dye on sheep (PlayerInteractEntityEvent)
		- Naming entity with a name tag (PlayerInteractEntityEvent)
		- Feeding/breeding animals (wheat,seeds) (PlayerInteractEntityEvent)
		- Healing animals (PlayerInteractEntityEvent)
		- Taming wolves with bones; ocelots/cats with fish; horse with apples/sugar (PlayerInteractEntityEvent)
		- Golden apple to cure a zombie villager (PlayerInteractEntityEvent)
		- Trident villager to witch; pig to pigman (EntityDamageByEntityEvent)
		- Splash/Lingering Potion to affect entity (PotionSplashEvent, LingeringPotionSplashEvent)
		- Potion to affect player (PotionConsumeEvent, PlayerInteractEvent)
		- Spawn egg to prevent spawning (PlayerInteractEvent)
		- Attaching leads to entity (PlayerInteractEntityEvent)
		- Milk a cow/mushroom cow (PlayerInteractEntityEvent)
	 */

	NO_INTERACT,
	/*
		- Shield blocking (PlayerInteractEvent)
		- Throw rod (PlayerFishEvent)
		- Spyglass usage (PlayerInteractEvent)
		- Chests/barrels/shulker boxes/hoppers/lectern/? (InventoryOpenEvent)
		- Using carrot or fungungs on a rod to lead pig/strider (PlayerInteractEntityEvent)
		- Anvil, Grindstone, Loom, Cartography table, smithing table, enchanting table (InventoryOpenEvent)
		- Open Doors, trapdoors, fence gates (PlayerInteractEvent)
		- Pressing buttons, pulling levers (PlayerInteractEvent)
		- Ringing bells (PlayerInteractEvent)
		- Glow ink on sign (?, re-edit a sign?) (PlayerEditSignEvent, SignChangeEvent)
		- Ride minecart/boat (VehicleEnterEvent)
		- Map creation(right click) (PlayerInteractEvent)
		- Bed to sleep and set spawnpoint (PlayerBedEnterEvent)
		- Lead on a fence post (PlayerInteractEvent)
		- Jukebox/note block sounds (PlayerInteractEvent)
	 */
	//endregion

	REPAIR,
	UPGRADE,
	CRAFT,
	ENCHANT,
	DISENCHANT,
	RENAME,

	;

}
