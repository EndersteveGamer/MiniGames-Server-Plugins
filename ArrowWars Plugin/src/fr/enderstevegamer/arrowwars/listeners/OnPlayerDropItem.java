package fr.enderstevegamer.arrowwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnPlayerDropItem implements Listener {
    @EventHandler
    public static void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
