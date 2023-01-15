package fr.enderstevegamer.arrowwars.loops;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InGameActionBar extends BukkitRunnable {
    @Override
    public void run() {
        if (!Main.isGameStarted() || Main.isAnnouncingResults()) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (ArrowWarsUtils.round(10 - Main.getGameTime(), 1) <= 10) {
                ArrowWarsUtils.sendActionbarTitle(player, ChatColor.RED + "Starting in " + ChatColor.GOLD + ArrowWarsUtils.round(10 - Main.getGameTime(), 1));
            } else {
                if (Main.getRedTeam().contains(player.getUniqueId()) || Main.getBlueTeam().contains(player.getUniqueId())) {
                    ArrowWarsUtils.sendActionbarTitle(player, "" + ChatColor.RED + Main.getRedTeam().size() + ChatColor.GOLD + " VS " + ChatColor.BLUE + Main.getBlueTeam().size());
                }
                else {
                    ArrowWarsUtils.sendActionbarTitle(player, ChatColor.GRAY + "SPECTATING: " + ChatColor.RED + Main.getRedTeam().size() + ChatColor.GOLD + " VS " + ChatColor.BLUE + Main.getBlueTeam().size());
                }
            }
        }
    }
}
