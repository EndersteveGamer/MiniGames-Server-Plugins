package fr.enderstevegamer.arrowwars.listeners;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeave implements Listener {
    @EventHandler
    public static void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ArrowWarsUtils.killPlayer(player, false, null);
        if (Main.getPlayersReady().containsKey(player.getUniqueId())) {
            Main.getPlayersReady().remove(player.getUniqueId());
        }
        if (Main.getPlayersSpectating().containsKey(player.getUniqueId())) {
            Main.getPlayersSpectating().remove(player.getUniqueId());
        }
        if (Main.getRedTeam().contains(player.getUniqueId())) {
            Main.getRedTeam().remove(player.getUniqueId());
        }
        if (Main.getBlueTeam().contains(player.getUniqueId())) {
            Main.getBlueTeam().remove(player.getUniqueId());
        }
    }
}
