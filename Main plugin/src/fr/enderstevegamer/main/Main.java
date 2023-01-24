package fr.enderstevegamer.main;

import fr.enderstevegamer.main.commands.ArrowWars;
import fr.enderstevegamer.main.commands.BestParkourTimes;
import fr.enderstevegamer.main.commands.Spleef;
import fr.enderstevegamer.main.listeners.*;
import fr.enderstevegamer.main.loops.*;
import fr.enderstevegamer.main.utils.GlobalCommunicationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
    public static DisplayParkourTime displayParkourTime;
    public static GiveGamemodeCompass giveGamemodeCompass;

    // Declare HashMaps
    public static HashMap<UUID, Location> playerSpawns;
    public static HashMap<UUID, Boolean> isInParkour;
    public static HashMap<UUID, Boolean> finishedParkour;
    public static HashMap<UUID, Instant> parkourStartTimes;
    public static HashMap<UUID, Duration> parkourBestTimes;
    public static HashMap<UUID, String> parkourBestTimesNames;

    public static ArrayList<UUID> waitingForParkourBestTimes;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Main plugin loaded successfully!");

        // Define instance
        INSTANCE = this;

        // Register channel
        INSTANCE.getServer().getMessenger().registerIncomingPluginChannel(INSTANCE, "endersteve:lobby", new GlobalCommunicationUtils());
        INSTANCE.getServer().getMessenger().registerOutgoingPluginChannel(INSTANCE, "endersteve:lobby");
        INSTANCE.getServer().getMessenger().registerOutgoingPluginChannel(INSTANCE, "BungeeCord");

        // Register commands
        getCommand("spleef").setExecutor(new Spleef());
        getCommand("bestparkourtimes").setExecutor(new BestParkourTimes());
        getCommand("arrowwars").setExecutor(new ArrowWars());

        // Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new OnEntityDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnHangingBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerInteractEntity(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnQuit(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnTNTExplode(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnArrowLand(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnItemDrop(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInventoryInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnRecipeDiscover(), this);

        // Define loops
        clearFire = new ClearFire();
        voidTP = new VoidTP();
        setCheckpoint = new SetCheckpoint();
        giveParkourItems = new GiveParkourItems();
        quitParkourIfInLobby = new QuitParkourIfInLobby();
        feedPlayers = new FeedPlayers();
        joinParkour = new JoinParkour();
        announceFinishedParkour = new AnnounceFinishedParkour();
        displayParkourTime = new DisplayParkourTime();
        giveGamemodeCompass = new GiveGamemodeCompass();

        // Run loops
        clearFire.runTaskTimer(this, 0, 0);
        voidTP.runTaskTimer(this, 0, 0);
        setCheckpoint.runTaskTimer(this, 0, 0);
        giveParkourItems.runTaskTimer(this, 0, 0);
        quitParkourIfInLobby.runTaskTimer(this, 0, 0);
        feedPlayers.runTaskTimer(this, 0, 0);
        joinParkour.runTaskTimer(this, 0, 0);
        announceFinishedParkour.runTaskTimer(this, 0, 0);
        displayParkourTime.runTaskTimer(this, 0, 0);
        giveGamemodeCompass.runTaskTimer(this, 0, 0);

        // Define HashMaps
        playerSpawns = new HashMap<>();
        isInParkour = new HashMap<>();
        finishedParkour = new HashMap<>();
        parkourStartTimes = new HashMap<>();
        parkourBestTimes = new HashMap<>();
        waitingForParkourBestTimes = new ArrayList<>();
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
    public static HashMap<UUID, Instant> getParkourStartTimes() {
        return parkourStartTimes;
    }
    public static HashMap<UUID, Duration> getParkourBestTimes() {
        return parkourBestTimes;
    }
    public static void setParkourBestTimes(HashMap<UUID, Duration> parkourBestTimes) {
        Main.parkourBestTimes = parkourBestTimes;
    }
    public static ArrayList<UUID> getWaitingForParkourBestTimes() {
        return waitingForParkourBestTimes;
    }

    public static HashMap<UUID, String> getParkourBestTimesNames() {
        return parkourBestTimesNames;
    }

    public static void setParkourBestTimesNames(HashMap<UUID, String> parkourBestTimesNames) {
        Main.parkourBestTimesNames = parkourBestTimesNames;
    }
}
