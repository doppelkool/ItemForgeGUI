package de.doppelkool.itemforgegui.Main.VersionDependency;

import lombok.Getter;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public enum SupportedVersion {

	v1_21_1("1.21.1"),
	v1_21_2("1.21.2"),
	v1_21_3("1.21.3"),
	v1_21_4("1.21.4"),
	v1_21_5("1.21.5"),
	v1_21_6("1.21.6"),
	v1_21_7("1.21.7"),
	v1_21_8("1.21.8"),

	;

	@Getter
	private String version;

	SupportedVersion(String version) {
		this.version = version;
	}

	public static SupportedVersion getSupportedVersionFromString(String version) throws IllegalStateException {
		for (SupportedVersion supported : SupportedVersion.values()) {
			if (supported.getVersion().equals(version)) {
				return supported;
			}
		}
		throw new IllegalStateException("Version not supported");
	}

}
