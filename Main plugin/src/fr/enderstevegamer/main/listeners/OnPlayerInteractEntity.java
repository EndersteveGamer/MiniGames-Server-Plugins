package fr.enderstevegamer.main.listeners;

import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnPlayerInteractEntity implements Listener {
    @EventHandler
    public static void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            event.setCancelled(true);
        }
    }
}
