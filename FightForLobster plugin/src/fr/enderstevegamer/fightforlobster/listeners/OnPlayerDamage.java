package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnPlayerDamage implements Listener {
    @EventHandler
    public static void onPlayerDamage(EntityDamageByEntityEvent event) {
        Roles.onPlayerDamage(event);
    }
}
