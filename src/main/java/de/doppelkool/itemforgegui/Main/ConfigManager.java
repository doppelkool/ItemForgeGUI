package de.doppelkool.itemforgegui.Main;

import com.tchristofferson.configupdater.ConfigUpdater;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * Manager class to handle the plugins config file and its variables
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public class ConfigManager {
	private static final String CONFIG_FILE_NAME = "config.yml";
	private static ConfigManager instance;

	private final FileConfiguration config;
	private final File configFile;
	private final boolean uniqueIdOnEditedItemEnabled;
	private final boolean differCappedEffectsEnabled;

	private final boolean armoreffectsShowAmbient;
	private final boolean armoreffectsShowParticles;
	private final boolean armoreffectsShowIcon;

	private final boolean showMinecraftItemFlags;
	private final boolean showCustomItemFlags;
	private final boolean showCustomPreventionFlags;

	private ConfigManager() {
		this.configFile = new File(Main.getPlugin().getDataFolder(), CONFIG_FILE_NAME);

		try (InputStream in = Main.getPlugin().getResource(CONFIG_FILE_NAME)) {
			if (!configFile.exists()) {
				Files.copy(in, configFile.toPath());
			}

			ConfigUpdater.update(Main.getPlugin(),
				CONFIG_FILE_NAME,
				configFile,
				List.of()
			);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, e.getMessage());
			Bukkit.getLogger().log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
		}

		this.config = YamlConfiguration.loadConfiguration(configFile);

		this.uniqueIdOnEditedItemEnabled = this.config.getBoolean("unique-id-on-edited-item");
		this.differCappedEffectsEnabled = this.config.getBoolean("differ-capped-effects");

		this.armoreffectsShowAmbient = this.config.getBoolean("armoreffects.showAmbient");
		this.armoreffectsShowParticles = this.config.getBoolean("armoreffects.showParticles");
		this.armoreffectsShowIcon = this.config.getBoolean("armoreffects.showIcon");

		this.showMinecraftItemFlags = this.config.getBoolean("show-flags-by-default-in-item-lore.minecraft-item-flags");
		this.showCustomItemFlags = this.config.getBoolean("show-flags-by-default-in-item-lore.custom-item-flags");
		this.showCustomPreventionFlags = this.config.getBoolean("show-flags-by-default-in-item-lore.custom-prevention-flags");

	}

	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}
}
