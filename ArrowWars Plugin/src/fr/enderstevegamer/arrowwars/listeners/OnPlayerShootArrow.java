package fr.enderstevegamer.arrowwars.listeners;

import fr.enderstevegamer.arrowwars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class OnPlayerShootArrow implements Listener {
    @EventHandler
    public static void onPlayerShootArrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        Main.getAlreadyShot().add(player.getUniqueId());
        player.getInventory().clear();
    }
}
