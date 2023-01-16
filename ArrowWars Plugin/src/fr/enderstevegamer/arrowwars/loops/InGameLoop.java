package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InGameLoop extends BukkitRunnable {
    @Override
    public void run() {
        if (!Main.isGameStarted() || Main.isAnnouncingResults()) return;
        Main.setGameTime(Main.getGameTime() + (1 / 20f));
        final float timeBeforeStart = ArrowWarsUtils.round(10 - Main.getGameTime(), 2);
        if ((timeBeforeStart == 1.00 || timeBeforeStart == 2.00 || timeBeforeStart == 3.00)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
        }
        if ((timeBeforeStart == 0.00)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 5);
            }
            ArrowWarsUtils.startTurn();
        }
        if ((timeBeforeStart >= 0.00 && timeBeforeStart <= 2.10)) {
            ArrowWarsUtils.removeBarrierLine(-11 - (21 - (int) (timeBeforeStart * 10)));
        }
        if ((Main.getRedTeam().size() == 0 || Main.getBlueTeam().size() == 0) && !Main.isStartedForDebug()) {
            ArrowWarsUtils.endGame();
        }
    }
}
