package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckEndGame extends BukkitRunnable {
    @Override
    public void run() {
        if (!Main.getStartedForDebug() && Main.gameStarted() && Main.getPlayersAlive().size() <= 1) {
            SpleefUtils.announceWinner();
        }
    }
}
