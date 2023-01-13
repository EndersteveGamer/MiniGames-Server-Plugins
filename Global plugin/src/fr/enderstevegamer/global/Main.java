package fr.enderstevegamer.global;

import fr.enderstevegamer.global.Utils.SavingUtils;
import fr.enderstevegamer.global.servers.Lobby;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.ChatColor;

public class Main extends Plugin {
    public static Main INSTANCE;
    public static Lobby lobby;

    @Override
    public void onEnable() {
        // Set instance
        INSTANCE = this;

        // Register custom channel
        INSTANCE.getProxy().registerChannel("endersteve:lobby");
        INSTANCE.getProxy().getPluginManager().registerListener(INSTANCE, new PluginMessageListener());

        // Create objects
        lobby = new Lobby();

        // Load data
        SavingUtils.BestTimesHashMap.load();

        // Confirm loaded successfully
        BungeeCord.getInstance().getLogger().info(ChatColor.GREEN + "Global plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Save data
        SavingUtils.BestTimesHashMap.save();

        // Unregister channel
        INSTANCE.getProxy().unregisterChannel("endersteve:lobby");

        // Confirm unloaded successfully
        BungeeCord.getInstance().getLogger().info(ChatColor.GREEN + "Global plugin unloaded successfully!");
    }

    // Getters
    public static Main getInstance() {
        return INSTANCE;
    }

    public static Lobby getLobby() {
        return lobby;
    }
}
