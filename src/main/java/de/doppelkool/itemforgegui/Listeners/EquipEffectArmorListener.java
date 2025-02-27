package de.doppelkool.itemforgegui.Listeners;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.jeff_media.armorequipevent.ArmorType;
import com.jeff_media.morepersistentdatatypes.DataType;
import com.jeff_media.morepersistentdatatypes.datatypes.collections.CollectionDataType;
import com.jeff_media.morepersistentdatatypes.datatypes.collections.MapDataType;
import com.jeff_media.morepersistentdatatypes.datatypes.serializable.ConfigurationSerializableDataType;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import de.doppelkool.itemforgegui.Main.Logger;
import de.doppelkool.itemforgegui.Main.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.naming.Name;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class Description
 *
 * @author doppelkool | github.com/doppelkool
 */
public class EquipEffectArmorListener implements Listener {

	@EventHandler
	public void onEquipEffectArmor(ArmorEquipEvent e) {
		Player pl = e.getPlayer();
		ArmorType type = e.getType();
		ItemStack newArmorPiece = e.getNewArmorPiece();

		Logger.log(newArmorPiece);

		ItemMeta itemMeta = newArmorPiece.getItemMeta();
		PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

		NamespacedKey namespacedKey = new NamespacedKey(Main.getPlugin(), "armorEffects");

		Set<ForgeArmorEffect> effects = Set.of(new ForgeArmorEffect(PotionEffectType.INVISIBILITY, 1));
		PersistentDataType<byte[], ForgeArmorEffect> forgeArmorEffectConfigurationSerializableDataType = new ConfigurationSerializableDataType<>(ForgeArmorEffect.class);

		CollectionDataType<Set<ForgeArmorEffect>, ForgeArmorEffect> persistentDataType = DataType.asSet(forgeArmorEffectConfigurationSerializableDataType);

		pdc.set(namespacedKey,
			persistentDataType,
			effects);

		newArmorPiece.setItemMeta(itemMeta);

		Logger.log(newArmorPiece);

		System.out.println("seperator");

		PersistentDataContainer persistentDataContainer = e.getNewArmorPiece().getItemMeta().getPersistentDataContainer();
		Set<ForgeArmorEffect> stringPotionEffects = persistentDataContainer.get(namespacedKey, persistentDataType);

		if (stringPotionEffects == null) {
			return;
		}

		Logger.log(stringPotionEffects);

		for(ForgeArmorEffect forgeArmorEffect : stringPotionEffects) {
			PotionEffect potionEffect = pl.getPotionEffect(forgeArmorEffect.getType());
			if(potionEffect != null) {
				pl.removePotionEffect(forgeArmorEffect.getType());
			}

			pl.addPotionEffect(forgeArmorEffect.getPotionEffect());
		}
	}
}
