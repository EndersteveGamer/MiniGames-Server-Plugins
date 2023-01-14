package fr.enderstevegamer.spleef.loops;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarDisplay extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.gameStarted() && !Main.getAnnouncingResults()) {
                if (Main.getPlayersAlive().contains(player.getUniqueId())) {
                    if (Main.getGameTime() >= 10) {
                        SpleefUtils.sendActionBar(player, ChatColor.GOLD + "" + Main.getPlayersAlive().size() + ChatColor.GREEN + " players left");
                    }
                    else {
                        SpleefUtils.sendActionBar(player, ChatColor.RED + "Starting in " + ChatColor.GOLD + SpleefUtils.round(10 - Main.getGameTime(), 1));
                    }
                }
                else {
                    SpleefUtils.sendActionBar(player, ChatColor.RED + "SPECTATING: " + ChatColor.GOLD + Main.getPlayersAlive().size() + ChatColor.RED + " players left");
                }
            }
            else if (!Main.gameStarted() && !Main.getAnnouncingResults()) {
                if (Main.getPlayersReady().contains(player.getUniqueId())) {
                    if (Main.getPlayersReady().size() < 2) {
                        SpleefUtils.sendActionBar(player, ChatColor.GREEN + "You are ready!");
                    }
                    else {
                        SpleefUtils.sendActionBar(player, ChatColor.GREEN + "You are ready! " + ChatColor.GOLD + "|" + ChatColor.GREEN + " Starting in " + ChatColor.GOLD + (int) SpleefUtils.round(Main.getStartTimer(), 0));
                    }
                }
                else if (!Main.getPlayersSpectating().contains(player.getUniqueId())) {
                    if (Main.getPlayersReady().size() < 2) {
                        SpleefUtils.sendActionBar(player, ChatColor.RED + "You are not ready!");
                    }
                    else {
                        SpleefUtils.sendActionBar(player, ChatColor.RED + "You are not ready! " + ChatColor.GOLD + "|" + ChatColor.RED + " Starting in " + ChatColor.GOLD + (int) SpleefUtils.round(Main.getStartTimer(), 0));
                    }
                }
                else {
                    if (Main.getPlayersReady().size() < 2) {
                        SpleefUtils.sendActionBar(player, ChatColor.GRAY + "You are spectating!");
                    }
                    else {
                        SpleefUtils.sendActionBar(player, ChatColor.GRAY + "You are spectating! " + ChatColor.GOLD + "|" + ChatColor.GRAY + " Starting in " + ChatColor.GOLD + (int) SpleefUtils.round(Main.getStartTimer(), 0));
                    }
                }
            }
            else {
                SpleefUtils.sendActionBar(player, "");
            }
        }
    }
}
