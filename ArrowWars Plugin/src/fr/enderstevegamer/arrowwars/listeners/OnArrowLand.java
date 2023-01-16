package fr.enderstevegamer.arrowwars.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class OnArrowLand implements Listener {
    @EventHandler
    public static void onArrowLand(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
        event.getEntity().remove();
    }
}
