package fr.enderstevegamer.main.loops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class GiveGamemodeCompass extends BukkitRunnable {
    @Override
    public void run() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "Select Minigame");
        meta.setLore(List.of(ChatColor.GRAY + "Click to select a minigame!"));
        meta.setLocalizedName("gamemodeSelector");
        compass.setItemMeta(meta);
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getInventory().contains(compass)) continue;
            player.getInventory().setItem(4, compass);
        }
    }
}
