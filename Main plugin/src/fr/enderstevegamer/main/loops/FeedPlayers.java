package fr.enderstevegamer.main.loops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FeedPlayers extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setFoodLevel(20);
            player.setSaturation(20);
        }
    }
}
