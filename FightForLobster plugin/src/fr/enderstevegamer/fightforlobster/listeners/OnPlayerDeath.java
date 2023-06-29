package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.RoleSelector;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.utils.DeathUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {
    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        Powers.onPlayerDeath(event.getEntity());
        DeathUtils.onPlayerDeath(event);
        RoleSelector.onPlayerDeath(event);
    }
}
