package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class OnItemDrop implements Listener {
    @EventHandler
    public static void onItemDrop(PlayerDropItemEvent event) {
        if (Main.getPlayersAlive().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
