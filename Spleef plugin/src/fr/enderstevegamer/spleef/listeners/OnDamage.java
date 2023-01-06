package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnDamage implements Listener {
    @EventHandler
    public static void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (Main.gameStarted()
                    && !Main.getAnnouncingResults()
                    && Main.getCurrentGamemode().equals(SpleefMode.SUDDEN_DEATH)
                    && event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                SpleefUtils.killPlayer(player);
                Snowball snowball = (Snowball) ((EntityDamageByEntityEvent) event).getDamager();
                if (snowball.getShooter() instanceof Player shooter) {
                    if (shooter.getUniqueId() == player.getUniqueId()) {
                        Bukkit.broadcastMessage(ChatColor.GOLD + shooter.getName() + ChatColor.RED + " killed himself!");
                    }
                    else {
                        Bukkit.broadcastMessage(ChatColor.GOLD + shooter.getName() + ChatColor.RED + " killed " + ChatColor.GOLD + player.getName() + ChatColor.RED + "!");
                    }
                }
            }
            event.setCancelled(true);
        }
    }
}
