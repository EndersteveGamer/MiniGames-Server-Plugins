package fr.enderstevegamer.fightforlobster.utils;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathUtils {
    public static void onPlayerDeath(PlayerDeathEvent event) {
        editDeathMessage(event);
    }

    private static void editDeathMessage(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        Player dead = event.getEntity();
        Role deadRole = Roles.getPlayerRole(dead);
        String roleString = (deadRole == null) ? "" : " (" + deadRole + ")";
        if (killer == null) {
            event.setDeathMessage(dead.getName() + roleString +  "died");
        }
        else {
            Role killerRole = Roles.getPlayerRole(killer);
            String killerRoleString = (killerRole == null) ? "" : " (" + killerRole + ")";
            event.setDeathMessage(dead.getName() + roleString
                    + " was killed by " + killer.getName() + killerRoleString);
        }
    }
}
