package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
    @EventHandler
    public static void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        Main.getPlayersAlive().remove(player.getUniqueId());
        Main.getPlayersReady().remove(player.getUniqueId());
        Main.getPlayersSpectating().remove(player.getUniqueId());
    }
}
