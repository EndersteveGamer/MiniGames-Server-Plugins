package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class JoinParkour extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Main.getIsInParkour().get(player.getUniqueId())
                    && player.getLocation().getZ() >= 22
                    && player.getLocation().getX() <= 7.5
                    && player.getLocation().getX() >= -6.5) {
                // Set is in parkour to true
                if (!Main.getIsInParkour().get(player.getUniqueId())) {
                    Main.getIsInParkour().put(player.getUniqueId(), true);

                    // Send message
                    player.sendMessage(ChatColor.GOLD + "You joined the parkour!");

                    // Set checkpoint to start of the parkour
                    Location parkourStart = new Location(player.getWorld(), 0.5, 61, 22.5);
                    parkourStart.setDirection(new Vector(0, 0, 1));
                    Main.getPlayerSpawns().put(player.getUniqueId(), parkourStart);
                }
            }
        }
    }
}
