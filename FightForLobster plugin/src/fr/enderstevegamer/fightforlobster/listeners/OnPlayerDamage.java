package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.runnables.PreventDamageLoops;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnPlayerDamage implements Listener {
    @EventHandler
    public static void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (PreventDamageLoops.wereHit.contains(event.getEntity().getUniqueId())) return;
        PreventDamageLoops.wereHit.add(event.getEntity().getUniqueId());
        Roles.onPlayerDamage(event);
        Powers.forEach((p) -> p.onEntityDamageByEntity(event));
    }
}
