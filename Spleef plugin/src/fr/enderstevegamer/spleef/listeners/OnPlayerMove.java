package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (Main.getGameTime() < 10 && Main.getPlayersAlive().contains(player.getUniqueId())) {
            Location to = event.getFrom();
            to.setPitch(event.getTo().getPitch());
            to.setYaw(event.getTo().getYaw());
            event.setTo(to);
        }
    }
}
