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
        Main.setGameTime(Main.getGameTime() + (1 / 20f));
        final float timeBeforeStart = ArrowWarsUtils.round(10 - Main.getGameTime(), 1);
        //if (timeBeforeStart == 2 && Main.isGameStarted()) {
        //  ArrowWarsUtils.removeBarrier();
        //}
        if ((timeBeforeStart == 1.0 || timeBeforeStart == 2.0 || timeBeforeStart == 3.0) && Main.isGameStarted()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
            }
        }
        if ((timeBeforeStart == 0.0) && Main.isGameStarted()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 5);
            }
            ArrowWarsUtils.startTurn();
        }
        if ((timeBeforeStart >= 0 && timeBeforeStart<= 2.1) && Main.isGameStarted()) {
            ArrowWarsUtils.removeBarrierLine(-11 - (21 - (int) (timeBeforeStart * 10)));
        }
    }
}
