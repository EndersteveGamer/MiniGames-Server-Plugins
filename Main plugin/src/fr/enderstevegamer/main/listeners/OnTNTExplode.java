package fr.enderstevegamer.main.listeners;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class OnTNTExplode implements Listener {
    @EventHandler
    public static void onTNTExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed tnt) {
            event.setCancelled(true);
            tnt.getWorld().createExplosion(tnt.getLocation(), 0);
        }
    }
}
