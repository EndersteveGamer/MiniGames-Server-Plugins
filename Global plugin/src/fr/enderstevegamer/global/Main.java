package fr.enderstevegamer.global;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.ChatColor;

public class Main extends Plugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        // Set instance
        INSTANCE = this;

        // Register custom channel
        INSTANCE.getProxy().registerChannel("endersteve:lobby");
        INSTANCE.getProxy().getPluginManager().registerListener(INSTANCE, new PluginMessageListener());

        // Confirm loaded successfully
        BungeeCord.getInstance().getLogger().info(ChatColor.GREEN + "Global plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        BungeeCord.getInstance().getLogger().info(ChatColor.GREEN + "Global plugin unloaded successfully!");
    }

    // Getters
    public static Main getInstance() {
        return INSTANCE;
    }
}
