package fr.enderstevegamer.arrowwars;

import fr.enderstevegamer.arrowwars.commands.ForceStart;
import fr.enderstevegamer.arrowwars.commands.Ready;
import fr.enderstevegamer.arrowwars.commands.Spectate;
import fr.enderstevegamer.arrowwars.listeners.OnBlockBreak;
import fr.enderstevegamer.arrowwars.listeners.OnPlayerJoin;
import fr.enderstevegamer.arrowwars.loops.CheckAllReady;
import fr.enderstevegamer.arrowwars.loops.KillPlayersAtBottom;
import fr.enderstevegamer.arrowwars.loops.ReadyActionbar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static boolean gameStarted = false;
    public static boolean announcingResults = false;

    // Declare HashMaps
    public static HashMap<UUID, Boolean> playersReady;
    public static HashMap<UUID, Boolean> playersSpectating;

    // Declare ArrayLists
    public static ArrayList<UUID> redTeam;
    public static ArrayList<UUID> blueTeam;
    public static float timeBeforeStart = 30;

    // Declare loops
    public static ReadyActionbar readyActionbar;
    public static CheckAllReady checkAllReady;
    public static KillPlayersAtBottom killPlayersAtBottom;
    @Override
    public void onEnable() {
        // Define HashMaps
        playersReady = new HashMap<>();
        playersSpectating = new HashMap<>();

        // Define ArrayLists
        redTeam = new ArrayList<>();
        blueTeam = new ArrayList<>();

        // Register listeners
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new OnBlockBreak(), this);

        // Register commands
        getCommand("ready").setExecutor(new Ready());
        getCommand("spectate").setExecutor(new Spectate());
        getCommand("forcestart").setExecutor(new ForceStart());

        // Define loops
        readyActionbar = new ReadyActionbar();
        checkAllReady = new CheckAllReady();
        killPlayersAtBottom = new KillPlayersAtBottom();

        // Run loops
        readyActionbar.runTaskTimer(this, 0, 0);
        checkAllReady.runTaskTimer(this, 0, 0);
        killPlayersAtBottom.runTaskTimer(this, 0, 0);

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
}
