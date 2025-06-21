package de.doppelkool.itemforgegui.Main.VersionDependency;

import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class VersionMapper {

	@Getter
	private static VersionMapper instance;

	public static String VERSION = null;

	private VersionMapper() {
		getServerVersion();
		Bukkit.getLogger().info("Version: " + VERSION);
	}

	private void getServerVersion() {
		// "1.21.1" from "1.21.1-R0.1-SNAPSHOT"
		String fullVersion = Bukkit.getBukkitVersion();
		VERSION = fullVersion.split("-")[0];
	}

	/**
	 * Compare Minecraft versions in "1.21.1" format.
	 */
	public boolean isAtLeastVersion(String required) {
		String[] current = VersionMapper.VERSION.split("\\.");
		String[] target = required.split("\\.");

		for (int i = 0; i < Math.min(current.length, target.length); i++) {
			int cur = Integer.parseInt(current[i]);
			int req = Integer.parseInt(target[i]);
			if (cur > req) return true;
			if (cur < req) return false;
		}
		return current.length >= target.length;
	}

	public static void init() {
		instance = new VersionMapper();
	}
}
