package fr.enderstevegamer.arrowwars;

import fr.enderstevegamer.arrowwars.commands.*;
import fr.enderstevegamer.arrowwars.listeners.*;
import fr.enderstevegamer.arrowwars.loops.*;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Main INSTANCE;
    public static boolean gameStarted = false;
    public static boolean announcingResults = false;
    public static float timeBeforeStart = 30;
    public static float gameTime = 0;
    public static String teamTurn = ArrowWarsUtils.Teams.RED;
    public static boolean startedForDebug = false;

    public static int round = 0;

    // Declare HashMaps
    public static HashMap<UUID, Boolean> playersReady;
    public static HashMap<UUID, Boolean> playersSpectating;

    // Declare ArrayLists
    public static ArrayList<UUID> redTeam;
    public static ArrayList<UUID> blueTeam;
    public static ArrayList<UUID> alreadyShot;

    // Declare loops
    public static ReadyActionbar readyActionbar;
    public static CheckAllReady checkAllReady;
    public static KillPlayersAtBottom killPlayersAtBottom;
    public static InGameActionBar inGameActionBar;
    public static InGameLoop inGameLoop;
    public static GiveNightVision giveNightVision;
    public static DeleteOutBoundsArrows deleteOutBoundsArrows;
    public static EndTurn endTurn;

    @Override
    public void onEnable() {
        INSTANCE = this;

        // Define HashMaps
        playersReady = new HashMap<>();
        playersSpectating = new HashMap<>();

        // Define ArrayLists
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();
        alreadyShot = new ArrayList<>();

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDropItem(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerShootArrow(), this);
        Bukkit.getPluginManager().registerEvents(new OnArrowLand(), this);
        Bukkit.getPluginManager().registerEvents(new OnPlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new OnInventoryClick(), this);

        // Register commands
        getCommand("ready").setExecutor(new Ready());
        getCommand("spectate").setExecutor(new Spectate());
        getCommand("forcestart").setExecutor(new ForceStart());
        getCommand("endgame").setExecutor(new EndGame());

        // Define loops
        readyActionbar = new ReadyActionbar();
        checkAllReady = new CheckAllReady();
        killPlayersAtBottom = new KillPlayersAtBottom();
        inGameActionBar = new InGameActionBar();
        inGameLoop = new InGameLoop();
        giveNightVision = new GiveNightVision();
        deleteOutBoundsArrows = new DeleteOutBoundsArrows();
        endTurn = new EndTurn();

        // Run loops
        readyActionbar.runTaskTimer(this, 0, 0);
        checkAllReady.runTaskTimer(this, 0, 0);
        killPlayersAtBottom.runTaskTimer(this, 0, 0);
        inGameActionBar.runTaskTimer(this, 0, 0);
        inGameLoop.runTaskTimer(this, 0, 0);
        giveNightVision.runTaskTimer(this, 0, 0);
        deleteOutBoundsArrows.runTaskTimer(this, 0, 0);
        endTurn.runTaskTimer(this, 0, 0);

        // Confirm loaded
        Bukkit.getLogger().info("ArrowWars plugin loaded successfully!");
    }

    @Override
    public void onDisable() {
        // Confirm unloaded
        Bukkit.getLogger().info("ArrowWars plugin unloaded successfully!");
    }

    public static Location getLobbyLocation() {
        Location location = new Location(Bukkit.getWorld("world"), -1.5, -17, 25.5);
        location.setDirection(new Vector(0, 0, 0));
        location.setYaw(180);
        location.setPitch(0);

        return location;
    }

    public static JavaPlugin getInstance() {
        return INSTANCE;
    }

    public static boolean isGameStarted() {
        return gameStarted;
    }

    public static void setGameStarted(boolean gameStarted) {
        Main.gameStarted = gameStarted;
    }

    public static boolean isAnnouncingResults() {
        return announcingResults;
    }

    public static void setAnnouncingResults(boolean announcingResults) {
        Main.announcingResults = announcingResults;
    }

    public static HashMap<UUID, Boolean> getPlayersReady() {
        return playersReady;
    }

    public static HashMap<UUID, Boolean> getPlayersSpectating() {
        return playersSpectating;
    }

    public static float getTimeBeforeStart() {
        return timeBeforeStart;
    }

    public static void setTimeBeforeStart(float timeBeforeStart) {
        Main.timeBeforeStart = timeBeforeStart;
    }

    public static ArrayList<UUID> getRedTeam() {
        return redTeam;
    }

    public static ArrayList<UUID> getBlueTeam() {
        return blueTeam;
    }

    public static float getGameTime() {
        return gameTime;
    }

    public static void setGameTime(float gameTime) {
        Main.gameTime = gameTime;
    }

    public static String getTeamTurn() {
        return teamTurn;
    }

    public static void setTeamTurn(String teamTurn) {
        Main.teamTurn = teamTurn;
    }

    public static ArrayList<UUID> getAlreadyShot() {
        return alreadyShot;
    }

    public static boolean isStartedForDebug() {
        return startedForDebug;
    }

    public static void setStartedForDebug(boolean startedForDebug) {
        Main.startedForDebug = startedForDebug;
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int round) {
        Main.round = round;
    }
}
