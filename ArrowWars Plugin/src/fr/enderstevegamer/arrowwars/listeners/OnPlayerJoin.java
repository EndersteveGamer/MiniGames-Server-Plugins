package fr.enderstevegamer.arrowwars.listeners;

import fr.enderstevegamer.arrowwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(Main.getLobbyLocation());

        if (Main.isGameStarted()) {
            player.sendMessage(ChatColor.RED + "Game already started!");
            player.setGameMode(GameMode.SPECTATOR);
            Main.getPlayersSpectating().put(player.getUniqueId(), true);
            Main.getPlayersReady().put(player.getUniqueId(), false);
        }
        else {
            player.setGameMode(GameMode.ADVENTURE);
            Main.getPlayersReady().put(player.getUniqueId(), false);
            Main.getPlayersSpectating().put(player.getUniqueId(), false);
        }
    }
}
