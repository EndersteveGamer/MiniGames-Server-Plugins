package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.EnmuPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class OnInteract implements Listener {
    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        List<EnmuPower> enmuPowers = Powers.getPowerType(EnmuPower.class);
        for (EnmuPower enmuPower : enmuPowers) {
            if (enmuPower.onPlayerInteract(event)) return;
        }
        Powers.forEach((p) -> p.tryActivating(event));
    }
}
