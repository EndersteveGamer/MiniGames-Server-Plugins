package fr.enderstevegamer.arrowwars.listeners;

import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnPlayerDamage implements Listener {
    @EventHandler
    public static void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {event.setCancelled(true); return;}
        Arrow arrow = (Arrow) ((EntityDamageByEntityEvent) event).getDamager();
        if (!(arrow.getShooter() instanceof Player shooter)) return;
        shooter.playSound(shooter.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
        ArrowWarsUtils.killPlayer(player, true, shooter);
        arrow.remove();
        event.setCancelled(true);
    }
}
