package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.EnmuPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove implements Listener {
    @EventHandler
    public static void onPlayerMove(PlayerMoveEvent event) {
        Powers.forEachPowerType(EnmuPower.class, (p) -> p.onPlayerMove(event));
    }
}
