package de.doppelkool.itemforgegui.Main.VersionDependency;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.semver4j.Semver;

/**
 * Maps and compares Minecraft server versions using semantic versioning.
 *
 * @author doppelkool
 */
public class VersionMapper {

	@Getter
	private static VersionMapper instance = null;
	@Getter
	private static SupportedVersion VERSION = null;

	private Semver currentVersion;

	private VersionMapper() {
		initVersion();
	}

	private void initVersion() {
		// Get version string like "1.21.1" from "1.21.1-R0.1-SNAPSHOT"
		String versionString = stripVersion(Bukkit.getBukkitVersion());

		// Assign enum value if matched
		VERSION = SupportedVersion.getSupportedVersionFromString(versionString);

		// Use Semver for accurate comparison
		this.currentVersion = new Semver(versionString);
	}

	private String stripVersion(String fullVersion) {
		return fullVersion.split("-")[0];  // e.g., "1.21.1-R0.1-SNAPSHOT" -> "1.21.1"
	}

	/**
	 * Checks if the current version is greater than or equal to the required version.
	 *
	 * @param required a version string like "1.21.0"
	 * @return true if the current version is >= required version
	 */
	public boolean isAtLeastVersion(String required) {
		Semver requiredVersion = new Semver(required);
		return currentVersion.isGreaterThanOrEqualTo(requiredVersion);
	}

	public static void init() {
		if (instance == null) {
			instance = new VersionMapper();
		}
	}
}
