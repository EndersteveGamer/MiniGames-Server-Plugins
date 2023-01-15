package fr.enderstevegamer.main.listeners;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.GlobalCommunicationUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OnJoin implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location spawnPos = new Location(player.getWorld(), 0.5, 65, -12.5);
        spawnPos.setDirection(new Vector(0, 0, 1));

        // Welcome message
        player.sendMessage(ChatColor.GREEN + "Welcome to the server!");

        // Set gamemode to adventure
        player.setGameMode(GameMode.ADVENTURE);

        // Teleport to spawn
        player.teleport(spawnPos);

        // Set player spawn
        Main.getPlayerSpawns().put(player.getUniqueId(), spawnPos);

        // Set player as not in parkour
        Main.getIsInParkour().put(player.getUniqueId(), false);

        // Set parkour as not finished
        Main.getFinishedParkour().put(player.getUniqueId(), false);

        // Send player login
        new BukkitRunnable() {
            @Override
            public void run() {
                GlobalCommunicationUtils.sendPlayerLogin(player);
            }
        }.runTaskLater(Main.getInstance(), 1);
    }
}
