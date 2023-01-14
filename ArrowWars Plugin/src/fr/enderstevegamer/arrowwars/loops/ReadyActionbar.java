package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ReadyActionbar extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.isGameStarted()) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getPlayersSpectating().get(player.getUniqueId())) {
                ArrowWarsUtils.displayReady(player, Main.getPlayersReady().get(player.getUniqueId()));
            }
            else {
                ArrowWarsUtils.displaySpectating(player);
            }
        }
    }
}
