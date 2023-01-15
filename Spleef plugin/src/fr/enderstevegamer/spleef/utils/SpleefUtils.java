package fr.enderstevegamer.spleef.utils;

import fr.enderstevegamer.spleef.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SpleefUtils {
    public static void giveItems(Player player) {
        Inventory inv = player.getInventory();
        inv.clear();

        // Get current gamemode
        String gamemode = Main.getCurrentGamemode();

        if (!gamemode.equals(SpleefMode.SNOWBALL_ONLY) && !gamemode.equals(SpleefMode.SUDDEN_DEATH)) {
            // Give shovel
            ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
            ItemMeta meta = shovel.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.GOLD + "Shovel");
            meta.setLore(List.of("A very fast shovel"));
            meta.setUnbreakable(true);
            shovel.setItemMeta(meta);
            inv.setItem(0, shovel);
        }

        if (!gamemode.equals(SpleefMode.SHOVEL_ONLY)) {
            // Give snowballs
            ItemStack snowballs = new ItemStack(Material.SNOWBALL);
            ItemMeta meta2 = snowballs.getItemMeta();
            assert meta2 != null;
            meta2.setDisplayName(ChatColor.GOLD + "Snowball");
            meta2.setLore(List.of("An infinite amount of snowballs"));
            snowballs.setItemMeta(meta2);
            if (gamemode.equals(SpleefMode.SNOWBALL_ONLY) || gamemode.equals(SpleefMode.SUDDEN_DEATH)) {
                inv.setItem(0, snowballs);
            } else {
                inv.setItem(1, snowballs);
            }
        }

        player.getInventory().setContents(inv.getContents());
    }

    public static void removeItems(Player player) {
        player.getInventory().clear();
    }

    public static void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void startGame() {
        // Set ready players alive
        Main.getPlayersAlive().clear();
        for (UUID uuid : Main.getPlayersReady()) {
            Main.getPlayersAlive().add(uuid);
        }

        // Remove ready players
        Main.getPlayersReady().clear();

        // Select gamemode
        if (!Main.getForcedGamemode()) {
            selectGamemode();
        }
        else {
            Main.setForcedGamemode(false);
        }

        // Announce game mode
        announceGamemode();

        // If hidden gamemode, select the hidden gamemode
        while (Main.getCurrentGamemode().equals(SpleefMode.HIDDEN)) {
            selectGamemode();
        }

        // Reset arena
        SpleefUtils.buildArena();

        // Teleport players
        for (UUID uuid : Main.getPlayersAlive()) {
            Player player = Main.INSTANCE.getServer().getPlayer(uuid);
            assert player != null;
            teleportInGame(player);
        }

        // Set gamemodes
        for (Player player : Main.INSTANCE.getServer().getOnlinePlayers()) {
            if (Main.getPlayersAlive().contains(player.getUniqueId())) {
                player.setGameMode(GameMode.SURVIVAL);
            }
            else {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }

        // Remove spectating players from list
        Main.getPlayersSpectating().clear();

        // Reset game time
        Main.setGameTime(0);

        if (Main.getCurrentGamemode().equals(SpleefMode.SUDDEN_DEATH)) {
            for (UUID uuid : Main.getPlayersAlive()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.RED + "You will get your items 3 seconds after the start of the game!");
            }
        }

        // Set game started
        Main.setGameStarted(true);
    }

    public static void teleportInGame(Player player) {
        // Define location
        Location loc = new Location(player.getWorld(), 0, 5, 0);

        // Set random X and Z
        loc.setX((int) (Math.random() * 33) + 0.5);
        loc.setZ(6 + (int) (Math.random() * 33) + 0.5);

        loc.setDirection(player.getLocation().getDirection());

        player.teleport(loc);
    }

    public static void killPlayer(Player player) {
        Main.getPlayersAlive().remove(player.getUniqueId());
        player.setGameMode(GameMode.SPECTATOR);
        removeItems(player);
        player.sendMessage(ChatColor.RED + "You are dead!");
    }

    public static void announceWinner() {
        // Set announcing results to true
        Main.setAnnouncingResults(true);

        // Set game as not started
        Main.setGameStarted(false);

        // Set started for debug to false
        Main.setStartedForDebug(false);

        // Set game time to 30
        Main.setGameTime(30);

        // Set alive player's gamemode to spectator
        for (UUID uuid : Main.getPlayersAlive()) {
            Player player = Bukkit.getServer().getPlayer(uuid);
            assert player != null;
            player.setGameMode(GameMode.SPECTATOR);
            removeItems(player);
        }

        // Title winner
        String winner;
        if (Main.getPlayersAlive().size() == 0) {
            winner = null;
        }
        else {
            winner = Objects.requireNonNull(Bukkit.getServer().getPlayer(Main.getPlayersAlive().get(0))).getName();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (winner == null) {
                player.sendTitle(ChatColor.GOLD + "Game Over!", ChatColor.RED + "Nobody won!", 10, 70, 20);
            }
            else {
                player.sendTitle(ChatColor.GOLD + "Game Over!", ChatColor.GOLD + winner + ChatColor.GREEN + " won!", 10, 70, 20);
            }
        }

        // Clear items
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
        }

        // Schedule endGame
        getEndGameRunnable().runTaskLater(Main.INSTANCE, 5 * 20);
    }

    public static void endGame() {
        // Set announcing results to false
        Main.setAnnouncingResults(false);

        // Set alive players to empty
        Main.getPlayersAlive().clear();

        // Set ready players to empty
        Main.getPlayersReady().clear();

        // Teleport players to lobby and set gamemode to adventure
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(Main.getLobbyPos(player));
            player.setGameMode(GameMode.ADVENTURE);
        }

        // Clear items
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
        }
    }

    public static BukkitRunnable getEndGameRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                endGame();
            }
        };
    }

    public static void buildArena() {
        // Clear arena
        clearArena();

        // Define world
        World world = Bukkit.getWorld("world");

        Location loc = new Location(world, 0, 0, 0);

        // Get gamemode
        String gamemode = Main.getCurrentGamemode();
        int yLimit;
        if (gamemode.equals(SpleefMode.MORE_LAYERS)
                || gamemode.equals(SpleefMode.BIG_SNOWBALLS_MORE_LAYERS)
                || gamemode.equals(SpleefMode.SUDDEN_DEATH)
                || gamemode.equals(SpleefMode.ARMOR_PIERCING_BIG)
                || gamemode.equals(SpleefMode.SNOW_RAIN)
                || gamemode.equals(SpleefMode.SNOW_STORM)) {
            yLimit = -20;
        }
        else if (gamemode.equals(SpleefMode.LESS_LAYERS)) {
            yLimit = 4;
        }
        else {
            yLimit = -8;
        }

        for (int y = 4; y >= yLimit; y--) {
            if ((y + 2) % 6 == 0) {
                for (int x = 0; x <= 32; x++) {
                    for (int z = 6; z <= 38; z++) {
                        loc.setX(x);
                        loc.setY(y);
                        loc.setZ(z);
                        assert world != null;
                        world.getBlockAt(loc).setType(Material.SNOW_BLOCK);
                    }
                }
            }
        }
    }

    public static void clearArena() {
        // Define world
        World world = Bukkit.getWorld("world");

        // Define location
        Location loc = new Location(world, 0, 0, 0);

        // Clear blocks
        for (int y = 4; y >= -20; y--) {
            for (int x = 0; x <= 32; x++) {
                for (int z = 6; z <= 38; z++) {
                    loc.setX(x);
                    loc.setY(y);
                    loc.setZ(z);
                    assert world != null;
                    world.getBlockAt(loc).setType(Material.AIR);
                }
            }
        }
    }

    public static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (float) tmp / factor;
    }

    public static void selectGamemode() {
        String[] modes = SpleefMode.MODES;
        Main.setGamemode(modes[(int) (Math.random() * (modes.length))]);
    }

    public static void announceGamemode() {
        String gamemode = Main.getCurrentGamemode();
        String[] description = SpleefMode.getDescriptions(gamemode);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(ChatColor.GOLD + description[0], ChatColor.GOLD + description[1], 10, 70, 20);
        }
    }
}
