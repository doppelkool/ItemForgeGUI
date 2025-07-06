package de.doppelkool.itemforgegui.Main.VersionDependency;

import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class Materials {

	@Getter
	private static Materials instance;

	private final Map<String, Material> cachedMaterials = new HashMap<>();

	// ────── 1.21.2 ──────
	private static final List<String> to_two = new ArrayList<>();
	static {
		to_two.add("BLACK_BUNDLE");
		to_two.add("BLUE_BUNDLE");
		to_two.add("BROWN_BUNDLE");
		to_two.add("CYAN_BUNDLE");
		to_two.add("GRAY_BUNDLE");
		to_two.add("GREEN_BUNDLE");
		to_two.add("LIGHT_BLUE_BUNDLE");
		to_two.add("LIGHT_GRAY_BUNDLE");
		to_two.add("LIME_BUNDLE");
		to_two.add("MAGENTA_BUNDLE");
		to_two.add("ORANGE_BUNDLE");
		to_two.add("PINK_BUNDLE");
		to_two.add("PURPLE_BUNDLE");
		to_two.add("RED_BUNDLE");
		to_two.add("WHITE_BUNDLE");
		to_two.add("YELLOW_BUNDLE");
		to_two.add("BORDURE_INDENTED_BANNER_PATTERN");
		to_two.add("FIELD_MASONED_BANNER_PATTERN");
	}

	// ────── 1.21.4 ──────
	private static final List<String> to_four = new ArrayList<>();
	static {
		to_four.add("BLOCK_OF_RESIN");
		to_four.add("CHISELED_RESIN_BRICKS");
		to_four.add("CLOSED_EYEBLOSSOM");
		to_four.add("OPEN_EYEBLOSSOM");
		to_four.add("PALE_HANGING_MOSS");
		to_four.add("PALE_MOSS_BLOCK");
		to_four.add("PALE_MOSS_CARPET");
		to_four.add("PALE_OAK_BOAT");
		to_four.add("PALE_OAK_BOAT_WITH_CHEST");
		to_four.add("PALE_OAK_BUTTON");
		to_four.add("PALE_OAK_DOOR");
		to_four.add("PALE_OAK_FENCE");
		to_four.add("PALE_OAK_FENCE_GATE");
		to_four.add("PALE_OAK_HANGING_SIGN");
		to_four.add("PALE_OAK_LEAVES");
		to_four.add("PALE_OAK_LOG");
		to_four.add("PALE_OAK_PLANKS");
		to_four.add("PALE_OAK_PRESSURE_PLATE");
		to_four.add("PALE_OAK_SAPLING");
		to_four.add("PALE_OAK_SIGN");
		to_four.add("PALE_OAK_SLAB");
		to_four.add("PALE_OAK_STAIRS");
		to_four.add("PALE_OAK_TRAPDOOR");
		to_four.add("PALE_OAK_WOOD");
		to_four.add("RESIN_BRICK");
		to_four.add("RESIN_BRICK_SLAB");
		to_four.add("RESIN_BRICK_STAIRS");
		to_four.add("RESIN_BRICK_WALL");
		to_four.add("RESIN_BRICKS");
		to_four.add("RESIN_CLUMP");
		to_four.add("STRIPPED_PALE_OAK_LOG");
		to_four.add("STRIPPED_PALE_OAK_WOOD");
		to_four.add("PALE_OAK_WALL_HANGING_SIGN");
		to_four.add("PALE_OAK_WALL_SIGN");
		to_four.add("POTTED_CLOSED_EYEBLOSSOM");
		to_four.add("POTTED_OPEN_EYEBLOSSOM");
		to_four.add("POTTED_PALE_OAK_SAPLING");
	}

	// ────── 1.21.5 ──────
	private static final List<String> to_five = new ArrayList<>();
	static {
		to_five.add("BUSH");
		to_five.add("CACTUS_FLOWER");
		to_five.add("FIREFLY_BUSH");
		to_five.add("LEAF_LITTER");
		to_five.add("WILDFLOWERS");
		to_five.add("SHORT_DRY_GRASS");
		to_five.add("TALL_DRY_GRASS");
		to_five.add("TEST_BLOCK");
		to_five.add("TEST_INSTANCE_BLOCK");
		to_five.add("BLUE_EGG");
		to_five.add("BROWN_EGG");
	}

	// ────── 1.21.6 ──────
	private static final List<String> to_six = new ArrayList<>();
	static {
		to_six.add("DRIED_GHAST");
		to_six.add("HARNESS");
		to_six.add("RED_HARNESS");
		to_six.add("ORANGE_HARNESS");
		to_six.add("YELLOW_HARNESS");
		to_six.add("LIME_HARNESS");
		to_six.add("GREEN_HARNESS");
		to_six.add("CYAN_HARNESS");
		to_six.add("LIGHT_BLUE_HARNESS");
		to_six.add("BLUE_HARNESS");
		to_six.add("PURPLE_HARNESS");
		to_six.add("MAGENTA_HARNESS");
		to_six.add("PINK_HARNESS");
		to_six.add("BROWN_HARNESS");
		to_six.add("GRAY_HARNESS");
		to_six.add("LIGHT_GRAY_HARNESS");
		to_six.add("WHITE_HARNESS");
		to_six.add("BLACK_HARNESS");
	}

	// ────── 1.21.6 ──────
	private static final List<String> to_seven = new ArrayList<>();
	static {
		to_seven.add("MUSIC_DISC_LAVA_CHICKEN");
	}

	private Materials() {
		loadIfAtLeast("1.21.2", to_two);
		loadIfAtLeast("1.21.4", to_four);
		loadIfAtLeast("1.21.5", to_five);
		loadIfAtLeast("1.21.6", to_six);
		loadIfAtLeast("1.21.7", to_seven);
	}

	private void loadIfAtLeast(String version, List<String> materialNames) {
		if (!VersionMapper.getInstance().isAtLeastVersion(version)) return;
		for (String name : materialNames) {
			try {
				Material mat = Material.valueOf(name);
				cachedMaterials.put(name, mat);
			} catch (IllegalArgumentException | NoSuchFieldError ignored) {}
		}
	}

	public boolean isLoaded(String materialName) {
		return cachedMaterials.containsKey(materialName);
	}

	public static void init() {
		instance = new Materials();
	}

}
