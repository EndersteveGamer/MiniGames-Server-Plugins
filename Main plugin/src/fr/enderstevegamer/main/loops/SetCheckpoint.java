package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

                // Set is in parkour to true
                if (!Main.getIsInParkour().get(player.getUniqueId())) {
                    Main.getIsInParkour().put(player.getUniqueId(), true);

                    // Send message
                    player.sendMessage(ChatColor.GOLD + "You joined the parkour!");
                }
            }
        }
    }
}
