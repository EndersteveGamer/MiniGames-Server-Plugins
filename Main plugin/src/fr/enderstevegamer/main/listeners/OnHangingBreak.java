package fr.enderstevegamer.main.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class OnHangingBreak implements Listener {
    @EventHandler
    public static void onHangingBreak(HangingBreakEvent event) {
        event.setCancelled(true);
    }
}
