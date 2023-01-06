package fr.enderstevegamer.main.utils;

import fr.enderstevegamer.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParkourUtils {
    public static ArrayList<Integer> PARKOURSLOTS;
    public static boolean isInit = false;

    public static void init() {
        PARKOURSLOTS = new ArrayList<>();
        PARKOURSLOTS.add(0);
        PARKOURSLOTS.add(7);
        PARKOURSLOTS.add(8);
        isInit = true;
    }
    public static void clearParkourItems(Player player) {
        initIfNotInit();
        for (int i = 0; i <= 44; i++) {
            if (!PARKOURSLOTS.contains(i)) {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null && item.getItemMeta() != null) {
                    String name = item.getItemMeta().getLocalizedName();
                    if (name.equals("goBackToCheckpoint") || name.equals("quitParkour") || name.equals("restartParkour")) {
                        player.getInventory().remove(item);
                    }
                }
            }
        }
    }

    public static void clearTrueParkourItems(Player player) {
        initIfNotInit();
        for (int i : PARKOURSLOTS) {
            player.getInventory().clear(i);
        }
    }

    public static void quitParkour(Player player, boolean teleport) {
        initIfNotInit();
        // Define spawn pos
        Location spawnPos = new Location(player.getWorld(), 0.5, 65, -12.5);
        spawnPos.setDirection(new Vector(0, 0, 1));

        if (teleport) {
            // Teleport player to spawn
            player.teleport(spawnPos);

        }

        // Reset HashMap values
        Main.getPlayerSpawns().put(player.getUniqueId(), spawnPos);
        Main.getIsInParkour().put(player.getUniqueId(), false);

        // Clear parkour items from inventory
        ParkourUtils.clearParkourItems(player);

        // Send message
        player.sendMessage(ChatColor.GOLD + "You left the parkour!");

        // Clear parkour items
        clearTrueParkourItems(player);

        // Set parkour as not finished
        Main.getFinishedParkour().put(player.getUniqueId(), false);
    }

    public static void giveParkourReward(Player player) {
        ItemStack rewardItem = new ItemStack(Material.STICK);
        ItemMeta meta = rewardItem.getItemMeta();
        assert meta != null;

        if (Math.random() < 0.5) {
            // TNT stick
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "TNT Stick");
            meta.setLore(List.of("Congratulations! You finished the parkour!", "", "Shoot TNT with this stick!"));
            meta.setLocalizedName("tntStick");
        }
        else {
            // Arrow stick
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Arrow Stick");
            meta.setLore(List.of("Congratulations! You finished the parkour!", "", "Shoot arrows with this stick!"));
            meta.setLocalizedName("arrowStick");
        }

        rewardItem.setItemMeta(meta);
        player.getInventory().addItem(rewardItem);
    }

    public static void initIfNotInit() {
        if (!isInit) {
            init();
        }
    }
}
