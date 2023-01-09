package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;

public class AnnounceFinishedParkour extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getZ() > 152.5
                    && player.getLocation().getY() >= 91
                    && !Main.getFinishedParkour().get(player.getUniqueId())) {
                // Set parkour as finished
                Main.getFinishedParkour().put(player.getUniqueId(), true);

                // Announce parkour completion
                Instant start = Main.getParkourStartTimes().get(player.getUniqueId());
                Instant end = Instant.now();
                Duration duration = Duration.between(start, end);
                String time = ParkourUtils.formatDuration(duration, ChatColor.GOLD, ChatColor.GREEN);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " finished the parkour in " + time + "!");
                ParkourUtils.giveParkourReward(player);

                // Set actionbar title
                ParkourUtils.sendActionbar(player, ChatColor.GOLD + "You finished the parkour in " + time + ChatColor.GOLD + "!");
            }
        }
    }
}
