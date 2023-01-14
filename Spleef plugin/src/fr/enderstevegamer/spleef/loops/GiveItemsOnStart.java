package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveItemsOnStart extends BukkitRunnable {
    @Override
    public void run() {
        if (SpleefUtils.round(Main.getGameTime(), 1) == 10.0 && !Main.getCurrentGamemode().equals(SpleefMode.SNOW_STORM)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Main.getPlayersAlive().contains(player.getUniqueId())) {
                    if (!Main.getCurrentGamemode().equals(SpleefMode.SUDDEN_DEATH)) {
                        SpleefUtils.giveItems(player);
                    }
                    else {
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                SpleefUtils.giveItems(player);
                            }
                        }.runTaskLater(Main.getPlugin(Main.class), 60);
                    }
                }
            }
        }
    }
}
