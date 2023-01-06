package fr.enderstevegamer.main;

import fr.enderstevegamer.main.commands.Spleef;
import fr.enderstevegamer.main.listeners.*;
import fr.enderstevegamer.main.loops.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Main INSTANCE;

    // Declare loops
    public static ClearFire clearFire;
    public static VoidTP voidTP;
    public static SetCheckpoint setCheckpoint;
    public static GiveParkourItems giveParkourItems;
    public static QuitParkourIfInLobby quitParkourIfInLobby;
    public static FeedPlayers feedPlayers;
    public static JoinParkour joinParkour;
    public static AnnounceFinishedParkour announceFinishedParkour;

    // Declare HashMaps
    public static HashMap<UUID, Location> playerSpawns;
    public static HashMap<UUID, Boolean> isInParkour;
    public static HashMap<UUID, Boolean> finishedParkour;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Main plugin loaded successfully!");

        // Define instance
        INSTANCE = this;

        // Register channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Register commands
        getCommand("spleef").setExecutor(new Spleef());

        // Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new OnEntityDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnHangingBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerInteractEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnQuit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnTNTExplode(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnArrowLand(), this);

        // Define loops
        clearFire = new ClearFire();
        voidTP = new VoidTP();
        setCheckpoint = new SetCheckpoint();
        giveParkourItems = new GiveParkourItems();
        quitParkourIfInLobby = new QuitParkourIfInLobby();
        feedPlayers = new FeedPlayers();
        joinParkour = new JoinParkour();
        announceFinishedParkour = new AnnounceFinishedParkour();

        // Run loops
        clearFire.runTaskTimer(this, 0, 0);
        voidTP.runTaskTimer(this, 0, 0);
        setCheckpoint.runTaskTimer(this, 0, 0);
        giveParkourItems.runTaskTimer(this, 0, 0);
        quitParkourIfInLobby.runTaskTimer(this, 0, 0);
        feedPlayers.runTaskTimer(this, 0, 0);
        joinParkour.runTaskTimer(this, 0, 0);
        announceFinishedParkour.runTaskTimer(this, 0, 0);

        // Define HashMaps
        playerSpawns = new HashMap<>();
        isInParkour = new HashMap<>();
        finishedParkour = new HashMap<>();
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Main plugin unloaded successfully!");
    }

    // Getters
    public static Main getInstance() {
        return INSTANCE;
    }
    public static HashMap<UUID, Location> getPlayerSpawns() {
        return playerSpawns;
    }
    public static HashMap<UUID, Boolean> getIsInParkour() {
        return isInParkour;
    }
    public static HashMap<UUID, Boolean> getFinishedParkour() {
        return finishedParkour;
    }
}
