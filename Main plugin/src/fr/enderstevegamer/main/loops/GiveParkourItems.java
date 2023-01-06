package fr.enderstevegamer.main.loops;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class GiveParkourItems extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Main.getIsInParkour().get(player.getUniqueId())) {
                // Clear parkour items from inventory
                ParkourUtils.clearParkourItems(player);

                // Give "Go back to checkpoint" heavy pressure plate
                ItemStack goBackToCheckpoint = new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
                ItemMeta meta = goBackToCheckpoint.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.GOLD + "Go back to checkpoint");
                meta.setLocalizedName("goBackToCheckpoint");
                meta.setLore(List.of("Will send you back to your last checkpoint"));
                goBackToCheckpoint.setItemMeta(meta);
                player.getInventory().setItem(0, goBackToCheckpoint);

                // Give "Quit parkour" barrier
                ItemStack quitParkour = new ItemStack(Material.BARRIER);
                meta = quitParkour.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.RED + "Quit parkour");
                meta.setLocalizedName("quitParkour");
                meta.setLore(List.of("Will send you back to the spawn"));
                quitParkour.setItemMeta(meta);
                player.getInventory().setItem(8, quitParkour);

                // Give "Restart parkour" light pressure plate
                ItemStack restartParkour = new ItemStack(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                meta = restartParkour.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.GOLD + "Restart parkour");
                meta.setLocalizedName("restartParkour");
                meta.setLore(List.of("Will send you back to the start", "of the parkour"));
                restartParkour.setItemMeta(meta);
                player.getInventory().setItem(7, restartParkour);
            }
        }
    }
}
