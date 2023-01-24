package fr.enderstevegamer.main.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnItemDrop implements Listener {
    @EventHandler
    public static void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
