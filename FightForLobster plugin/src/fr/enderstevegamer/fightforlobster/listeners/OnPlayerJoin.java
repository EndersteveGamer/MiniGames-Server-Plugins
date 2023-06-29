package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Roles.setPlayerRole(event.getPlayer(), Role.NORMAL_PLAYER);
    }
}
