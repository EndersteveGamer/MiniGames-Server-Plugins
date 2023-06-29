package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.SasoriPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class OnEntityDeath implements Listener {
    @EventHandler
    public static void onEntityDeath(EntityDeathEvent event) {
        Powers.forEachPowerType(SasoriPower.class, (s) -> s.onEntityDeath(event));
    }
}
