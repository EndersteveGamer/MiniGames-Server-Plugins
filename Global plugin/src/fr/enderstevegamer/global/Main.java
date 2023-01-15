package fr.enderstevegamer.global;

import fr.enderstevegamer.global.utils.Database;
import fr.enderstevegamer.global.servers.Lobby;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.UUID;

public class Main extends Plugin {
    public static Main INSTANCE;
    public static Lobby lobby;
    public static Database database;

    public static HashMap<UUID, String> playerUsernames;

    @Override
    public void onEnable() {
        // Set instance
        INSTANCE = this;

        // Register custom channel
        INSTANCE.getProxy().registerChannel("endersteve:lobby");
        INSTANCE.getProxy().getPluginManager().registerListener(INSTANCE, new PluginMessageListener());

        // Create objects
        lobby = new Lobby();
        try {
            database = new Database();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Create HashMaps
        playerUsernames = new HashMap<>();

        // Load data
        database.loadData();

        // Confirm loaded successfully
        BungeeCord.getInstance().getLogger().info(ChatColor.GREEN + "Global plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Save data
        Database.saveParkourBestTimes();

        // Unregister channel
        INSTANCE.getProxy().unregisterChannel("endersteve:lobby");

        // Save data
        database.saveData();

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

    public static HashMap<UUID, String> getPlayerUsernames() {
        return playerUsernames;
    }
}
