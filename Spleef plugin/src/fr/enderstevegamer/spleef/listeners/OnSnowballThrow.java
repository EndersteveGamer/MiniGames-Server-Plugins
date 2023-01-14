package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class OnSnowballThrow implements Listener {
    @EventHandler
    public static void onSnowballThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player
                && event.getEntity() instanceof Snowball) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    // Give snowballs
                    ItemStack snowballItem = new ItemStack(Material.SNOWBALL, 1);
                    ItemMeta meta = snowballItem.getItemMeta();
                    assert meta != null;
                    meta.setDisplayName(ChatColor.GOLD + "Snowball");
                    snowballItem.setItemMeta(meta);
                    if (!Main.getCurrentGamemode().equals(SpleefMode.SNOWBALL_ONLY) && !Main.getCurrentGamemode().equals(SpleefMode.SUDDEN_DEATH)) {
                        player.getInventory().setItem(1, snowballItem);
                    }
                    else {
                        player.getInventory().setItem(0, snowballItem);
                    }
                }
            }.runTaskLater(Main.INSTANCE, 1);
        }
    }
}
