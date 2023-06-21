package fr.enderstevegamer.fightforlobster.runnables;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TickPowers extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Powers.forEach((p) -> p.tick(player));
        }
    }
}
