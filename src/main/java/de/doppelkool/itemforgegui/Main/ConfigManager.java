package de.doppelkool.itemforgegui.Main;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Manager class to handle the plugins config file and its variables
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public class ConfigManager {
	private static ConfigManager instance;

	private FileConfiguration config;
	private boolean uniqueIdOnEditedItemEnabled;
	private boolean differCappedEffectsEnabled;

	private boolean armoreffectsShowAmbient;
	private boolean armoreffectsShowParticles;
	private boolean armoreffectsShowIcon;

	private ConfigManager() {
		this.config = Main.getPlugin().getConfig();
		Main.getPlugin().saveDefaultConfig();

		this.uniqueIdOnEditedItemEnabled = this.config.getBoolean("unique-id-on-edited-item");
		this.differCappedEffectsEnabled = this.config.getBoolean("differ-capped-effects");

		this.armoreffectsShowAmbient = this.config.getBoolean("armoreffects.showAmbient");
		this.armoreffectsShowParticles = this.config.getBoolean("armoreffects.showParticles");
		this.armoreffectsShowIcon = this.config.getBoolean("armoreffects.showIcon");
	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}
}
