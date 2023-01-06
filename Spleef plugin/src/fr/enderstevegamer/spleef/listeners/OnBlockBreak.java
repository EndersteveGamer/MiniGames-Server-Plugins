package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.SNOW_BLOCK) || Main.getGameTime() < 10) {
            event.setCancelled(true);
        }
    }
}
