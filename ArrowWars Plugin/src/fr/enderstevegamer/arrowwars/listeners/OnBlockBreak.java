package fr.enderstevegamer.arrowwars.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }
}
