package fr.enderstevegamer.spleef;

import fr.enderstevegamer.spleef.commands.*;
import fr.enderstevegamer.spleef.commands.tabcompleters.SetGamemodeCompleter;
import fr.enderstevegamer.spleef.listeners.*;
import fr.enderstevegamer.spleef.loops.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {
    static boolean gameStarted = false;
    static boolean announcingResults = false;
    static boolean startedForDebug = false;
    static boolean forcedGamemode = false;
    static float gameTime = 0;
    static float startTimer = 30;
    static ArrayList<UUID> playersAlive = new ArrayList<>();
    static ArrayList<UUID> playersReady = new ArrayList<>();
    static ArrayList<UUID> playersSpectating = new ArrayList<>();
    public static Main INSTANCE;
    static String currentGamemode = "normal";

    // Declare loops
    public static ActionBarDisplay actionBarDisplay;
    public static FeedPlayers feedPlayers;
    public static KillPlayersAtBottom killPlayersAtBottom;
    public static CheckEndGame checkEndGame;
    public static AddGameTime addGameTime;
    public static GiveItemsOnStart giveItemsOnStart;
    public static UpdateStartTimer updateStartTimer;
    public static SnowRain snowRain;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Spleef plugin loaded successfully!");

        // Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnSignClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnItemDrop(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnItemDrop(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnSnowballThrow(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnSnowballLand(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerMove(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnQuit(), this);

        // Register commands
        getCommand("forcestart").setExecutor(new ForceStart());
        getCommand("ready").setExecutor(new Ready());
        getCommand("endgame").setExecutor(new EndGame());
        getCommand("rebuild").setExecutor(new Rebuild());
        getCommand("setgamemode").setExecutor(new SetGamemode());
        getCommand("setgamemode").setTabCompleter(new SetGamemodeCompleter());
        getCommand("spectate").setExecutor(new Spectate());

        // Define instance
        INSTANCE = this;

        // Register channel
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Define loops
        actionBarDisplay = new ActionBarDisplay();
        feedPlayers = new FeedPlayers();
        killPlayersAtBottom = new KillPlayersAtBottom();
        checkEndGame = new CheckEndGame();
        addGameTime = new AddGameTime();
        giveItemsOnStart = new GiveItemsOnStart();
        updateStartTimer = new UpdateStartTimer();
        snowRain = new SnowRain();

        // Run loops
        actionBarDisplay.runTaskTimer(this, 0, 0);
        feedPlayers.runTaskTimer(this, 0, 0);
        killPlayersAtBottom.runTaskTimer(this, 0, 0);
        checkEndGame.runTaskTimer(this, 0, 0);
        addGameTime.runTaskTimer(this, 0, 2);
        giveItemsOnStart.runTaskTimer(this, 0, 0);
        updateStartTimer.runTaskTimer(this, 0, 0);
        snowRain.runTaskTimer(this, 0, 0);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Spleef plugin unloaded successfully!");
    }

    public static boolean gameStarted() {
        return gameStarted;
    }

    public static void setGameStarted(boolean gameStarted) {
        Main.gameStarted = gameStarted;
    }

    public static Location getLobbyPos(Player player) {
        Location loc = new Location(player.getWorld(), 16.5, 13, 49.5);
        loc.setDirection(new Vector(0, 0, -1));
        return loc;
    }

    public static ArrayList<UUID> getPlayersAlive() {
        return playersAlive;
    }

    public static ArrayList<UUID> getPlayersReady() {
        return playersReady;
    }

    public static boolean getAnnouncingResults() {
        return announcingResults;
    }

    public static void setAnnouncingResults(boolean announcingResults) {
        Main.announcingResults = announcingResults;
    }

    public static boolean getStartedForDebug() {
        return startedForDebug;
    }

    public static void setStartedForDebug(boolean startedForDebug) {
        Main.startedForDebug = startedForDebug;
    }

    public static float getGameTime() {
        return gameTime;
    }

    public static void setGameTime(float gameTime) {
        Main.gameTime = gameTime;
    }

    public static float getStartTimer() {
        return startTimer;
    }

    public static void setStartTimer(float value) {
        Main.startTimer = value;
    }

    public static String getCurrentGamemode() {
        return currentGamemode;
    }

    public static void setGamemode(String gamemode) {
        Main.currentGamemode = gamemode;
    }

    public static boolean getForcedGamemode() {
        return forcedGamemode;
    }

    public static void setForcedGamemode(boolean bool) {
        forcedGamemode = bool;
    }
    public static ArrayList<UUID> getPlayersSpectating() {
        return playersSpectating;
    }
}
