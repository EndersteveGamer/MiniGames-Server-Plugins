package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Welcome messages
        player.sendMessage(ChatColor.GREEN + "Welcome to the " + ChatColor.GOLD + "Spleef" + ChatColor.GREEN + " minigame!");
        player.sendTitle(ChatColor.GOLD + "Spleef", ChatColor.GREEN + "Make all your opponents fall!", 10, 70, 20);

        // Set gamemode, effects and teleport
        if (Main.gameStarted()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(ChatColor.RED + "The game has already started!");
        }
        else {
            player.setGameMode(GameMode.ADVENTURE);
        }

        player.teleport(Main.getLobbyPos(player));
    }
}
