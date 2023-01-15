package fr.enderstevegamer.main.listeners;

import fr.enderstevegamer.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
    @EventHandler
    public static void onQuit(PlayerQuitEvent event) {
        Main.getPlayerSpawns().remove(event.getPlayer().getUniqueId());
        Main.getIsInParkour().put(event.getPlayer().getUniqueId(), false);
        Main.getFinishedParkour().put(event.getPlayer().getUniqueId(), false);
        event.getPlayer().getInventory().clear();
    }
}
