package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VoidTP extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getY() < 40) {
                player.teleport(Main.getPlayerSpawns().get(player.getUniqueId()));
            }
        }
    }
}
