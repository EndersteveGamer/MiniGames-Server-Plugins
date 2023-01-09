package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class DisplayParkourTime extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.getIsInParkour().get(player.getUniqueId()) && !Main.getFinishedParkour().get(player.getUniqueId())) {
                Instant start;
                if (Main.getParkourStartTimes().containsKey(player.getUniqueId())) {
                    start = Main.getParkourStartTimes().get(player.getUniqueId());
                }
                else {
                    start = Instant.now();
                }
                Instant now = Instant.now();
                Duration duration = Duration.between(start, now);
                String durationString = ParkourUtils.formatDuration(duration);
                ParkourUtils.sendActionbar(player, ChatColor.GOLD + "Time: " + durationString);
            }
        }
    }
}
