package de.doppelkool.itemforgegui.Main;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class ConfigManager {
	private static ConfigManager instance;

	@Getter
	private FileConfiguration config;

	@Getter
	private boolean itemImmutabilityEnabled;

	@Getter
	private boolean uniqueIdOnEditedItemEnabled;


	private ConfigManager() {
		this.config = Main.getPlugin().getConfig();
		Main.getPlugin().saveDefaultConfig();

		this.uniqueIdOnEditedItemEnabled = this.config.getBoolean("unique-id-on-edited-item");
		this.itemImmutabilityEnabled = this.config.getBoolean("item-immutability-enabled");
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}
}
