package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.DeidaraPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class OnEntityTarget implements Listener {
    @EventHandler
    public static void onEntityTarget(EntityTargetEvent event) {
        Powers.forEachPowerType(DeidaraPower.class, (p) -> p.onGhastTarget(event));
    }
}
