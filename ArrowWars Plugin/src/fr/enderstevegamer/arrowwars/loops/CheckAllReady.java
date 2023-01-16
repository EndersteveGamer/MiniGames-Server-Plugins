package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckAllReady extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.isGameStarted() || Main.isAnnouncingResults()) return;
        if (ArrowWarsUtils.countDownActive()) {
            Main.setTimeBeforeStart(Main.getTimeBeforeStart() - (1/20f));
        }
        else {
            Main.setTimeBeforeStart(30);
        }
        if (Main.getTimeBeforeStart() <= 0 || ArrowWarsUtils.allPlayersReady()) {
            ArrowWarsUtils.startGame();
        }
    }
}
