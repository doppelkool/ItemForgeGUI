package de.doppelkool.itemforgegui.Main;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.jeff_media.customblockdata.CustomBlockData;
import com.jeff_media.morepersistentdatatypes.DataType;
import com.jeff_media.morepersistentdatatypes.datatypes.collections.CollectionDataType;
import com.jeff_media.morepersistentdatatypes.datatypes.serializable.ConfigurationSerializableDataType;
import de.doppelkool.itemforgegui.Commands.EditCommand;
import de.doppelkool.itemforgegui.Listeners.*;
import de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.*;
import de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair.*;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.CustomItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.ItemFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.Flags.PreventionFlagManager;
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import de.doppelkool.itemforgegui.Main.Messages.MessageManager;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The plugins main class
 * Provides cross-plugin objects, such as its own instance
 *
 * @author doppelkool | github.com/doppelkool
 */
public final class Main extends JavaPlugin {

	@Getter
	private static Main plugin;
	@Getter
	private NamespacedKey customLoreEditBookKey;
	@Getter
	private NamespacedKey customEnchantmentStackIDKey;
	@Getter
	private NamespacedKey customNotAvailableStackIDKey;
	@Getter
	private NamespacedKey customTagItemNotAllowedForgeActions;
	@Getter
	private NamespacedKey customTagItemCraftPrevention;
	@Getter
	private NamespacedKey customTagCustomItemFlags;
	@Getter
	private NamespacedKey customTagCustomHideFlag;
	@Getter
	private NamespacedKey customTagUID;

	@Getter
	private NamespacedKey customArmorEffectsKeyStackIDKey;
	@Getter
	private NamespacedKey customArmorEffectsKey;

	@Getter
	private NamespacedKey customAttributeModifierKeyStackIDKey;

	@Getter
	private NamespacedKey customAttributeModifierKeyCategoryIDKey;

	@Getter
	private ArrayList<NamespacedKey> customAttributeModifierPluginKeys;

	@Getter
	private NamespacedKey customEquipmentSlotIDKey;

	private PersistentDataType<byte[], ForgeArmorEffect> customPersistantDataTypeArmorEffect;
	@Getter
	private CollectionDataType<ArrayList<ForgeArmorEffect>, ForgeArmorEffect> customArmorEffectListDataType;

	public void onEnable() {
		plugin = this;
		new Metrics(this, 24997);

		ConfigManager cMr = ConfigManager.getInstance();
		if (cMr.isUniqueIdOnEditedItemEnabled()) {
			customTagUID = new NamespacedKey(this, "id");
		}

		customTagItemNotAllowedForgeActions = new NamespacedKey(this, "notAllowedForgeActions");
		customTagItemCraftPrevention = new NamespacedKey(this, "craftingPreventionType");
		customTagCustomItemFlags = new NamespacedKey(this, "customItemFlags");
		customTagCustomHideFlag = new NamespacedKey(this, "customHideFlag");
		customLoreEditBookKey = new NamespacedKey(this, "isEditLoreBook");
		customNotAvailableStackIDKey = new NamespacedKey(this, "isNotAvailable");
		customEnchantmentStackIDKey = new NamespacedKey(this, "enchantmentInvID");
		customArmorEffectsKeyStackIDKey = new NamespacedKey(this, "armorEffectsInvID");

		customArmorEffectsKey = new NamespacedKey(this, "armorEffects");
		customAttributeModifierKeyStackIDKey = new NamespacedKey(this, "attributeInvID");
		customAttributeModifierKeyCategoryIDKey = new NamespacedKey(this, "attributeCategoryID");
		customAttributeModifierPluginKeys = new ArrayList<>();

		customEquipmentSlotIDKey = new NamespacedKey(this, "equipmentSlot");

		customPersistantDataTypeArmorEffect = new ConfigurationSerializableDataType<>(ForgeArmorEffect.class);
		customArmorEffectListDataType = DataType.asArrayList(customPersistantDataTypeArmorEffect);

		MessageManager.getInstance();
		ItemFlagManager.getInstance();
		PreventionFlagManager.getInstance();
		CustomItemFlagManager.getInstance();

		getCommand("edit").setExecutor(new EditCommand());

		PluginManager pluginmanager = Bukkit.getPluginManager();
		pluginmanager.registerEvents(new MenuListener(), this);
		pluginmanager.registerEvents(new OnQuitListener(), this);
		pluginmanager.registerEvents(new OnRespawnListener(), this);
		pluginmanager.registerEvents(new LoreBookListeners(), this);
		pluginmanager.registerEvents(new EditDurabilitySignListener(), this);
		pluginmanager.registerEvents(new EditAmountSignListener(), this);
		pluginmanager.registerEvents(new EditSingleEnchantmentStrengthSignListener(), this);
		pluginmanager.registerEvents(new EditSingleArmorEffectStrengthSignListener(), this);
		pluginmanager.registerEvents(new EditAttributeModifierAmountSignListener(), this);
		pluginmanager.registerEvents(new EditItemIDSignListener(), this);
		pluginmanager.registerEvents(new PreventAlteringListeners(), this);
		pluginmanager.registerEvents(new PreventApplyListener(), this);
		pluginmanager.registerEvents(new GrindstoneListener(), this);
		pluginmanager.registerEvents(new AnvilListener(), this);
		pluginmanager.registerEvents(new CraftListener(), this);
		pluginmanager.registerEvents(new EnchantingTableListener(), this);
		pluginmanager.registerEvents(new PreventThrowListener(), this);
		pluginmanager.registerEvents(new PreventEatListener(), this);
		pluginmanager.registerEvents(new PreventPlaceListener(), this);
		pluginmanager.registerEvents(new PreventDestroyListener(), this);
		pluginmanager.registerEvents(new SmithingTableListener(), this);
		pluginmanager.registerEvents(new PreventEquipListener(), this);
		pluginmanager.registerEvents(new PreventSmeltingListeners(), this);
		pluginmanager.registerEvents(new UnEquipEffectArmorListener(), this);
		pluginmanager.registerEvents(new DrinkMilkListener(), this);

		ArmorEquipEvent.registerListener(this);
		CustomBlockData.registerListener(this);

		ConfigurationSerialization.registerClass(ForgeArmorEffect.class);
	}

	public NamespacedKey getRandomKey() {
		NamespacedKey randomKey = new NamespacedKey(plugin, UUID.randomUUID().toString().replace("-", ""));
		customAttributeModifierPluginKeys.add(randomKey);
		return randomKey;
	}
}
