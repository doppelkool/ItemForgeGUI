package de.doppelkool.itemforgegui.Main;

import de.doppelkool.itemforgegui.Commands.EditCommand;
import de.doppelkool.itemforgegui.Listeners.EditLoreBookListener;
import de.doppelkool.itemforgegui.Listeners.MenuListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private static Main plugin;
    @Getter
    private NamespacedKey customLoreEditBookKey;
    @Getter
    private NamespacedKey customEnchantmentStackIDKey;
    @Getter
    private NamespacedKey customNotAvailableStackIDKey;

    public void onEnable()
    {
        plugin = this;
        customLoreEditBookKey = new NamespacedKey(this, "item_key");
        customEnchantmentStackIDKey = new NamespacedKey(Main.getPlugin(), "id");
        customNotAvailableStackIDKey = new NamespacedKey(Main.getPlugin(), "not_available");
        getCommand("edit").setExecutor(new EditCommand());

        PluginManager pluginmanager = Bukkit.getPluginManager();
        pluginmanager.registerEvents(new MenuListener(), this);
        pluginmanager.registerEvents(new EditLoreBookListener(), this);
    }
}
