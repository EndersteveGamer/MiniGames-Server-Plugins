package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceFinishedParkour extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().getZ() > 152.5
                    && player.getLocation().getY() >= 91
                    && !Main.getFinishedParkour().get(player.getUniqueId())) {
                Main.getFinishedParkour().put(player.getUniqueId(), true);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getDisplayName() + " finished the parkour!");
                ParkourUtils.giveParkourReward(player);
            }
        }
    }
}
