package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public class SetCheckpoint extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)) {
                Location loc = player.getLocation().getBlock().getLocation();
                loc.setX(loc.getX() + 0.5);
                loc.setZ(loc.getZ() + 0.5);
                loc.setDirection(player.getLocation().getDirection());
                Main.getPlayerSpawns().put(player.getUniqueId(), loc);

                // Check if this is the start checkpoint
                Location parkourStart = new Location(player.getWorld(), 0, 61, 22);
                if (player.getLocation().getBlock().getLocation().equals(parkourStart)) {
                    // Set start time to now
                    Main.getParkourStartTimes().put(player.getUniqueId(), Instant.now());
                }
            }
        }
    }
}
