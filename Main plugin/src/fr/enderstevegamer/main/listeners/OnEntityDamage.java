package fr.enderstevegamer.main.listeners;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnEntityDamage implements Listener {
    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }
}
