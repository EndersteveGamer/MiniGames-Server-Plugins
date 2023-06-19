package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnInteract implements Listener {
    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        Powers.forEach((p) -> p.tryActivating(event));
    }
}
