package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class AddGameTime extends BukkitRunnable {
    @Override
    public void run() {
        Main.setGameTime(Main.getGameTime() + 0.1f);
    }
}
