package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class QuitParkourIfInLobby extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.getIsInParkour().get(player.getUniqueId()) && player.getLocation().getZ() <= 16) {
                ParkourUtils.quitParkour(player, false);
            }
        }
    }
}
