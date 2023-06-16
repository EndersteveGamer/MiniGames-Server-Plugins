package fr.enderstevegamer.fightforlobster.runnables;

import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.scheduler.BukkitRunnable;

public class TickRoles extends BukkitRunnable {
    @Override
    public void run() {
        Roles.tickRoles();
    }
}
