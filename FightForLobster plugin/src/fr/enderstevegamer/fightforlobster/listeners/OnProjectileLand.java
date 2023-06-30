package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.RoshiPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class OnProjectileLand implements Listener {
    @EventHandler
    public static void onProjectileLand(ProjectileHitEvent event) {
        Powers.forEachPowerType(RoshiPower.class, (r) -> r.onProjectileLand(event));
    }
}
