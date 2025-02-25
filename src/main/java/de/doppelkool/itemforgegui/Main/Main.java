package de.doppelkool.itemforgegui.Main;

import com.jeff_media.armorequipevent.ArmorEquipEvent;
import com.jeff_media.customblockdata.CustomBlockData;
import de.doppelkool.itemforgegui.Commands.EditCommand;
import de.doppelkool.itemforgegui.Listeners.*;
import de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.*;
import de.doppelkool.itemforgegui.Listeners.PreventionFlagListeners.PreventCraftRepairDisEnchantRepair.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
    private NamespacedKey customTagItemNotAllowedInInvType;
    @Getter
    private NamespacedKey customTagItemNotAllowedForgeActions;
    @Getter
    private NamespacedKey customTagUID;

    public void onEnable()
    {
        plugin = this;

        ConfigManager cMr = ConfigManager.getInstance();
        if(cMr.isUniqueIdOnEditedItemEnabled()) {
            customTagUID = new NamespacedKey(this, "id");
        }

        if(cMr.isItemImmutabilityEnabled()) {
            customTagItemImmutabilityKey = new NamespacedKey(this, "isImmutable");
            customTagItemNotAllowedInInvType = new NamespacedKey(this, "notAllowedInvTypes");
            customTagItemNotAllowedForgeActions = new NamespacedKey(this, "notAllowedForgeActions");
        }

        customLoreEditBookKey = new NamespacedKey(this, "isEditLoreBook");
        customNotAvailableStackIDKey = new NamespacedKey(this, "isNotAvailable");
        customEnchantmentStackIDKey = new NamespacedKey(this, "enchantmentInvID");

        getCommand("edit").setExecutor(new EditCommand());

        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(new MenuListener(), this);
        pluginmanager.registerEvents(new OnQuitListener(), this);
        pluginmanager.registerEvents(new LoreBookListeners(), this);
        pluginmanager.registerEvents(new EditDurabilitySignListener(), this);
        pluginmanager.registerEvents(new EditAmountSignListener(), this);
        pluginmanager.registerEvents(new EditSingleEnchantmentStrengthSignListener(), this);
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

        ArmorEquipEvent.registerListener(this);
        //Enable Block location updates
        CustomBlockData.registerListener(this);
    }
}
