package de.doppelkool.itemforgegui.Main;

import de.doppelkool.itemforgegui.Commands.EditCommand;
import de.doppelkool.itemforgegui.Listeners.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ObjectInputFilter;

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

    public void onEnable()
    {
        plugin = this;

        ConfigManager instance = ConfigManager.getInstance();
        if(instance.isItemImmutabilityEnabled()){
            customTagItemImmutabilityKey = new NamespacedKey(this, "customTagItemImmutability");
        }

        customLoreEditBookKey = new NamespacedKey(this, "edit_lore");
        customNotAvailableStackIDKey = new NamespacedKey(this, "not_available");
        customEnchantmentStackIDKey = new NamespacedKey(this, "id");

        getCommand("edit").setExecutor(new EditCommand());

        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(new MenuListener(), this);
        pluginmanager.registerEvents(new EditLoreBookListener(), this);
        pluginmanager.registerEvents(new EditDurabilitySignListener(), this);
        pluginmanager.registerEvents(new EditAmountSignListener(), this);
        pluginmanager.registerEvents(new EditSingleEnchantmentStrengthSignListener(), this);
    }
}
