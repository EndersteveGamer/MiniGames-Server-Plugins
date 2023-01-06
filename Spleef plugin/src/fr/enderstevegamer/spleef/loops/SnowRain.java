package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class SnowRain extends BukkitRunnable {
    @Override
    public void run() {
        if (Main.getGameTime() > 10
                && Main.gameStarted()
                && !Main.getAnnouncingResults()) {
            int SNOWPERTICK = 0;
            if (Main.getCurrentGamemode().equals(SpleefMode.SNOW_STORM)) {
                SNOWPERTICK = 4;
            }
            else if (Main.getCurrentGamemode().equals(SpleefMode.SNOW_RAIN)) {
                SNOWPERTICK = 2;
            }
            for (int i = 0; i < SNOWPERTICK; i++) {
                Location location = new Location(Bukkit.getWorld("world"), 0, 0, 0);

                // Set random X and Z
                location.setX((int) (Math.random() * 33) + 0.5);
                location.setZ(6 + (int) (Math.random() * 33) + 0.5);

                // Set Y
                location.setY(40);

                // Spawn snowball
                Bukkit.getWorld("world").spawnEntity(location, EntityType.SNOWBALL);
            }
        }
    }
}
