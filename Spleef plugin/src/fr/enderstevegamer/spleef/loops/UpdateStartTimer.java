package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateStartTimer extends BukkitRunnable {
    @Override
    public void run() {
        if (((Main.getPlayersReady().size() + Main.getPlayersSpectating().size()) == Bukkit.getOnlinePlayers().size() && Main.getPlayersReady().size() > 1)
                || SpleefUtils.round(Main.getStartTimer(), 1) == 0.0) {
            if (!Main.gameStarted() && !Main.getAnnouncingResults()) {
                SpleefUtils.startGame();
                return;
            }
        }
        if (Main.getPlayersReady().size() < 2) {
            Main.setStartTimer(30);
        }
        else if (!Main.gameStarted() && !Main.getAnnouncingResults()) {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                Main.setStartTimer(Main.getStartTimer() - (1f / 20f));
            }
        }
    }
}
