package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KillPlayersAtBottom extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getY() <= -23 && Main.getPlayersAlive().contains(player.getUniqueId()) && !Main.getAnnouncingResults()) {
                SpleefUtils.killPlayer(player);
                break;
            }
        }
    }
}
