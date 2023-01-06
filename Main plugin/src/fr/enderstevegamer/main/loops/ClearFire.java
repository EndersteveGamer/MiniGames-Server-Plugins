package fr.enderstevegamer.main.loops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearFire extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getFireTicks() > 0) {
                player.setFireTicks(0);
            }
        }
    }
}
