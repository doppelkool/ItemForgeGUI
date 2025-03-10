package de.doppelkool.itemforgegui.Main.CustomItemManager;

import de.doppelkool.itemforgegui.Main.ConfigManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
@Setter
public class ForgeArmorEffect implements ConfigurationSerializable {

	private final PotionEffectType type;
	private int amplifier;

	public ForgeArmorEffect(PotionEffectType type, int amplifier) {
		this.type = type;
		this.amplifier = amplifier;
	}

	@Override
	@NotNull
	public Map<String, Object> serialize() {
		return new HashMap<>(Map.of(
			"effect", this.type.getKey().toString(),
			"amplifier", this.amplifier
		));
	}

	@SuppressWarnings("unused")
	//This constructor is needed for paper deserialization
	public static ForgeArmorEffect deserialize(Map<String, Object> map) {
		return new ForgeArmorEffect(
			PotionEffectType.getByKey(NamespacedKey.fromString((String) map.get("effect"))),
			(Integer) map.get("amplifier")
		);
	}

	public PotionEffect getPotionEffect() {
		ConfigManager instance = ConfigManager.getInstance();

		return new PotionEffect(
			this.type,
			PotionEffect.INFINITE_DURATION,
			this.amplifier,
			instance.isArmoreffectsShowAmbient(),
			instance.isArmoreffectsShowParticles(),
			instance.isArmoreffectsShowIcon()
		);
	}
}