package de.doppelkool.itemforgegui.Main.CustomItemManager;

import com.google.common.collect.ImmutableMap;
import com.jeff_media.morepersistentdatatypes.datatypes.serializable.ConfigurationSerializableDataType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
@Getter
public class ForgeArmorEffect implements ConfigurationSerializable {

	private final PotionEffectType type;
	private final int amplifier;

	public ForgeArmorEffect(PotionEffectType type, int amplifier) {
		this.type = type;
		this.amplifier = amplifier;
	}

	@SuppressWarnings("unused")
	//This constructor is needed for deserialization
	public ForgeArmorEffect(Map<String, Object> map) {
		this(
			Bukkit.getUnsafe().get(Registry.EFFECT, NamespacedKey.fromString((String) map.get("effect"))),
			(Integer) map.get("amplifier")
		);
	}

	@Override
	@NotNull
	public Map<String, Object> serialize() {
		return ImmutableMap.<String, Object>builder()
			.put("effect", this.type.getKey().toString())
			.put("amplifier", this.amplifier)
			.build();
	}

	public PotionEffect getPotionEffect() {
		return new PotionEffect(
			this.type,
			PotionEffect.INFINITE_DURATION,
			this.amplifier,
			false, //ToDo configurable in config
			false, //ToDo configurable in config
			true //ToDo configurable in config
		);
	}

}
