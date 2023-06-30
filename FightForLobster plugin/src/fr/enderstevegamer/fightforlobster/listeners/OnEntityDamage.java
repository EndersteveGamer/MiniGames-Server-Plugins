package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.EnmuPower;
import fr.enderstevegamer.fightforlobster.utils.combattracker.CombatTrackerUtils;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnEntityDamage implements Listener {
    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
        Powers.forEachPowerType(EnmuPower.class, (p) -> p.onDamage(event));
        CombatTrackerUtils.onPlayerDamage(event);
    }
}
