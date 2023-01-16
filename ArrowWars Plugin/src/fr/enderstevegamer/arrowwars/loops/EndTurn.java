package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class EndTurn extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.isGameStarted() && Main.getGameTime() > 10) {
            if (ArrowWarsUtils.everybodyShot() && !ArrowWarsUtils.arrowsRemaining()) {
                if (Main.getBlueTeam().size() > 0 && Main.getRedTeam().size() > 0) {
                    ArrowWarsUtils.switchTurn();
                }
            }
        }
    }
}
