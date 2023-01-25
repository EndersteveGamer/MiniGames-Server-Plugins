package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteOutBoundsArrows extends BukkitRunnable {
    @Override
    public void run() {
        for (Arrow arrow : Main.getInstance().getServer().getWorld("world").getEntitiesByClass(Arrow.class)) {
            double x = arrow.getLocation().getX();
            double z = arrow.getLocation().getZ();
            if (z < -3 || z > 22 || x < -23 || x > 20) {
                Player player = (Player) arrow.getShooter();
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                arrow.remove();
            }
        }
    }
}
