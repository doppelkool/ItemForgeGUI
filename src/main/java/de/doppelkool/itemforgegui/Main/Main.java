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
import de.doppelkool.itemforgegui.Main.CustomItemManager.ForgeArmorEffect;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * The plugins main class
 * Provides cross-plugin objects, such as the plugins message prefix and its own instance
 *
 * @author doppelkool | github.com/doppelkool
 */
public final class Main extends JavaPlugin {

    public static final String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.LIGHT_PURPLE + "ItemForgeGUI" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

    @Getter
    private static Main plugin;
    @Getter
    private NamespacedKey customLoreEditBookKey;
    @Getter
    private NamespacedKey customEnchantmentStackIDKey;
    @Getter
    private NamespacedKey customNotAvailableStackIDKey;
    @Getter
    private NamespacedKey customTagItemImmutabilityKey;
    @Getter
    private NamespacedKey customTagItemNotAllowedForgeActions;
    @Getter
    private NamespacedKey customTagUID;

    @Getter
    private NamespacedKey customArmorEffectsKeyStackIDKey;
    @Getter
    private NamespacedKey customArmorEffectsKey;
    private PersistentDataType<byte[], ForgeArmorEffect> customPersistantDataTypeArmorEffect;
    @Getter
    private CollectionDataType<ArrayList<ForgeArmorEffect>, ForgeArmorEffect> customArmorEffectListDataType;

    public void onEnable()
    {
        plugin = this;

        ConfigManager cMr = ConfigManager.getInstance();
        if(cMr.isUniqueIdOnEditedItemEnabled()) {
            customTagUID = new NamespacedKey(this, "id");
        }

        if(cMr.isItemImmutabilityEnabled()) {
            customTagItemImmutabilityKey = new NamespacedKey(this, "isImmutable");
            customTagItemNotAllowedForgeActions = new NamespacedKey(this, "notAllowedForgeActions");
        }

        customLoreEditBookKey = new NamespacedKey(this, "isEditLoreBook");
        customNotAvailableStackIDKey = new NamespacedKey(this, "isNotAvailable");
        customEnchantmentStackIDKey = new NamespacedKey(this, "enchantmentInvID");
        customArmorEffectsKeyStackIDKey = new NamespacedKey(Main.getPlugin(), "armorEffectsInvID");

        customArmorEffectsKey = new NamespacedKey(Main.getPlugin(), "armorEffects");
        customPersistantDataTypeArmorEffect = new ConfigurationSerializableDataType<>(ForgeArmorEffect.class);
        customArmorEffectListDataType = DataType.asArrayList(customPersistantDataTypeArmorEffect);

        getCommand("edit").setExecutor(new EditCommand());

        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(new MenuListener(), this);
        pluginmanager.registerEvents(new OnQuitListener(), this);
        pluginmanager.registerEvents(new LoreBookListeners(), this);
        pluginmanager.registerEvents(new EditDurabilitySignListener(), this);
        pluginmanager.registerEvents(new EditAmountSignListener(), this);
        pluginmanager.registerEvents(new EditSingleEnchantmentStrengthSignListener(), this);
        pluginmanager.registerEvents(new EditSingleArmorEffectStrengthSignListener(), this);
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
        pluginmanager.registerEvents(new PreventBurnListener(), this);
        pluginmanager.registerEvents(new SmithingTableListener(), this);
        pluginmanager.registerEvents(new PreventEquipListener(), this);
        pluginmanager.registerEvents(new UnEquipEffectArmorListener(), this);
        pluginmanager.registerEvents(new DrinkMilkListener(), this);

        ArmorEquipEvent.registerListener(this);
        //Enable Block location updates
        CustomBlockData.registerListener(this);

        ConfigurationSerialization.registerClass(ForgeArmorEffect.class);
    }
}
