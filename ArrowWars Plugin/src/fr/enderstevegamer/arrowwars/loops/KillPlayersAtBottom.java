package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KillPlayersAtBottom extends BukkitRunnable {
    @Override
    public void run() {
        if (!Main.isGameStarted()) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getBlueTeam().contains(player.getUniqueId()) && !Main.getRedTeam().contains(player.getUniqueId())) continue;
            if (player.getLocation().getY() <= -29) {
                ArrowWarsUtils.killPlayer(player, true,null);
            }
        }
    }
}
