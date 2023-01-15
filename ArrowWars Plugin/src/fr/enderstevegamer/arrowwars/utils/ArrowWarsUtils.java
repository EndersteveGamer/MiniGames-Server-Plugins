package fr.enderstevegamer.arrowwars.utils;

import fr.enderstevegamer.arrowwars.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class ArrowWarsUtils {
    public static void sendActionbarTitle(Player player, String string) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(string));
    }

    public static boolean countDownActive() {
        return getNumberReady() >= 2;
    }

    public static int getNumberReady() {
        int result = 0;
        for (boolean ready : Main.getPlayersReady().values()) {
            if (ready) result++;
        }
        for (boolean spectating : Main.getPlayersSpectating().values()) {
            if (spectating) result++;
        }
        return result;
    }

    public static void displayReady(Player player, boolean ready) {
        if (ready) {
            if (countDownActive()) {
                sendActionbarTitle(player, ChatColor.GREEN + "You are ready! " + ChatColor.GOLD + "| " + ChatColor.GREEN + "Starting in " + ChatColor.GOLD + (int) Main.getTimeBeforeStart());
            }
            else {
                sendActionbarTitle(player, ChatColor.GREEN + "You are ready!");
            }
        }
        else {
            if (countDownActive()) {
                sendActionbarTitle(player, ChatColor.RED + "You are not ready! " + ChatColor.GOLD + "| " + ChatColor.RED + "Starting in " + ChatColor.GOLD + (int) Main.getTimeBeforeStart());
            }
            else {
                sendActionbarTitle(player, ChatColor.RED + "You are not ready!");
            }
        }
    }

    public static void displaySpectating(Player player) {
        if (countDownActive()) {
            sendActionbarTitle(player, ChatColor.GRAY + "You are spectating! " + ChatColor.GOLD + "| " + ChatColor.GRAY + "Starting in " + ChatColor.GOLD + (int) Main.getTimeBeforeStart());
        }
        else {
            sendActionbarTitle(player, ChatColor.GRAY + "You are spectating!");
        }
    }

    public static boolean allPlayersReady() {
        return getNumberReady() == Bukkit.getOnlinePlayers().size() && getNumberReady() >= 2;
    }

    public static void startGame() {
        Main.setGameStarted(true);
        Main.setAnnouncingResults(false);
        Main.setGameTime(0);

        // Set the starting team to random
        Main.setTeamTurn((Math.random() < 0.5) ? Teams.RED : Teams.BLUE);

        // Announce who starts
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.getTeamTurn().equals(Teams.RED)) {
                player.sendMessage(ChatColor.RED + "The red team starts!");
            }
            else {
                player.sendMessage(ChatColor.BLUE + "The blue team starts!");
            }
        }

        // Set spectators to gamemode spectator
        for (UUID uuid : Main.getPlayersSpectating().keySet()) {
            if (Main.getPlayersSpectating().get(uuid)) {
                Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
            }
        }

        ArrayList<UUID> players = new ArrayList<>();
        for (UUID uuid : Main.getPlayersReady().keySet()) {
            if (Main.getPlayersReady().get(uuid)) {
                players.add(uuid);
            }
        }

        makeTeams(players);
        teleportPlayers();
    }

    public static void makeTeams(ArrayList<UUID> players) {
        int playersNum = players.size();
        int teamSize = playersNum / 2;
        for (int i = 0; i < teamSize; i++) {
            UUID uuid = getRandomPlayer(players);
            Main.getBlueTeam().add(uuid);
            players.remove(uuid);
        }
        playersNum = playersNum - teamSize;
        for (int i = 0; i < playersNum; i++) {
            UUID uuid = getRandomPlayer(players);
            Main.getRedTeam().add(uuid);
            players.remove(uuid);
        }
    }

    public static UUID getRandomPlayer(ArrayList<UUID> players) {
        int random = (int) (Math.random() * players.size());
        return players.get(random);
    }

    public static void teleportPlayers() {
        Location blueSpawn = new Location(Bukkit.getWorld("world"), 10.5, -24, 9.5);
        blueSpawn.setYaw(90);
        blueSpawn.setPitch(0);
        Location redSpawn = new Location(Bukkit.getWorld("world"), -13.5, -24, 9.5);
        redSpawn.setYaw(-90);
        redSpawn.setPitch(0);
        // Teleport blue team
        for (UUID uuid : Main.getBlueTeam()) {
            Bukkit.getPlayer(uuid).teleport(blueSpawn);
        }
        // Teleport red team
        for (UUID uuid : Main.getRedTeam()) {
            Bukkit.getPlayer(uuid).teleport(redSpawn);
        }
    }

    public static void endGame() {
        Main.setGameStarted(false);
        Main.setAnnouncingResults(false);
        Main.setTimeBeforeStart(30);
        Main.getRedTeam().clear();
        Main.getBlueTeam().clear();
        Main.getPlayersSpectating().clear();
        Main.getPlayersReady().clear();

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.ADVENTURE);
            player.teleport(Main.getLobbyLocation());
        }
    }

    public static void killPlayer(Player player, boolean announceDeath, Player killer) {
        boolean naturalDeath = killer == null;
        Main.getBlueTeam().remove(player.getUniqueId());
        Main.getRedTeam().remove(player.getUniqueId());
        Main.getPlayersSpectating().put(player.getUniqueId(), true);
        player.setGameMode(GameMode.SPECTATOR);
        if (!announceDeath) return;
        player.sendMessage((naturalDeath) ? ChatColor.RED + "You fell off the map!" : ChatColor.RED + "You were killed by " + ChatColor.GOLD + killer.getName());
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            player1.sendMessage((naturalDeath) ? ChatColor.GOLD + player.getName() + ChatColor.RED + " fell off the map!" : ChatColor.GOLD + player.getName() + ChatColor.RED + " was killed by " + ChatColor.GOLD + killer.getName());
        }
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }

    public static void removeBarrierLine(int y) {
        Location location = new Location(Bukkit.getWorld("world"), -2, y, 0);
        for (int z = -3; z <= 21; z++) {
            location.setZ(z);
            Block block = location.getBlock();
            block.setType(Material.AIR);
        }
    }

    public static void removeBarrier() {
        for (int i = 0; i <= 20; i++) {
            int finalI = i;
            new BukkitRunnable() {
                @Override
                public void run() {
                    removeBarrierLine(-12 - finalI);
                };
            }.runTaskLater(Main.getInstance(), i);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                removeBarrierLine(-32);
            }
        }.runTaskLater(Main.getInstance(), 20);
    }

    public static void switchTurn() {
        if (Main.getTeamTurn().equals(Teams.RED)) {
            for (UUID uuid : Main.getRedTeam()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.RED + "Your turn is over!");
                player.getInventory().clear();
            }
            Main.setTeamTurn(Teams.BLUE);
            startTurn();
        }
        else {
            for (UUID uuid : Main.getBlueTeam()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.RED + "Your turn is over!");
                player.getInventory().clear();
            }
            Main.setTeamTurn(Teams.RED);
            startTurn();
        }
    }

    public static void startTurn() {
        if (Main.getTeamTurn().equals(Teams.RED)) {
            for (UUID uuid : Main.getRedTeam()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.GREEN + "It's your turn!");
                player.getInventory().setItem(0, new ItemStack(Material.BOW));
                player.getInventory().setItem(1, new ItemStack(Material.ARROW, 1));
            }
        }
        else {
            for (UUID uuid : Main.getBlueTeam()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.GREEN + "It's your turn!");
                player.getInventory().setItem(0, new ItemStack(Material.BOW));
                player.getInventory().setItem(1, new ItemStack(Material.ARROW, 1));
            }
        }
    }

    public static class Teams {
        public static final String RED = ChatColor.RED + "Red";
        public static final String BLUE = ChatColor.BLUE + "Blue";
    }
}
