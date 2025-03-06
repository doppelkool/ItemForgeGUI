package de.doppelkool.itemforgegui.Listeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ArmorEffectManager;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class UnEquipEffectArmorListener implements Listener {

	@EventHandler
	public void onArmorChange(ArmorEquipEvent e) {
		Player player = e.getPlayer();
		// Delay the effect update by one tick to allow the inventory to update.
		Bukkit.getScheduler().runTask(Main.getPlugin(), () -> updateArmorEffects(player));
	}

	private void updateArmorEffects(Player player) {
		// Recalculate best armor effects from currently equipped items
		Map<PotionEffectType, Integer> armorEffects = ArmorEffectManager.getBestArmorEffects(player);

		// Apply or update armor effects: use -1 as our marker for armor-applied effects.
		for (Map.Entry<PotionEffectType, Integer> entry : armorEffects.entrySet()) {
			PotionEffectType type = entry.getKey();
			int newAmp = entry.getValue() - 1; // 0 based effects on minecraft end
			PotionEffect active = player.getPotionEffect(type);

			// Update the effect if:
			// - It is missing,
			// - It does not have our custom marker (-1 aka. PotionEffect.INFINITE_DURATION),
			// - Or its amplifier doesn't match the best available amplifier.
			if (active == null || active.getDuration() != PotionEffect.INFINITE_DURATION || active.getAmplifier() != newAmp) {
				player.removePotionEffect(type);
				//ToDo at ForgeArmorEffect.getPotionEffect:
				player.addPotionEffect(new PotionEffect(type, PotionEffect.INFINITE_DURATION, newAmp, false, false));
			}
		}

		// Remove any armor effect (marker: duration -1) that is no longer provided by any equipped armor.
		for (PotionEffect effect : player.getActivePotionEffects()) {
			if (effect.getDuration() == -1 && !armorEffects.containsKey(effect.getType())) {
				player.removePotionEffect(effect.getType());
			}
		}
	}

}