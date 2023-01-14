package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KillPlayersAtBottom extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getY() <= -29) {
                ArrowWarsUtils.killPlayer(player, true,null);
            }
        }
    }
}
