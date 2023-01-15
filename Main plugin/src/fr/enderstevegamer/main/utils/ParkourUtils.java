package fr.enderstevegamer.main.utils;

import fr.enderstevegamer.main.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

        // Delete parkour start time
        Main.getParkourStartTimes().remove(player.getUniqueId());

        // Reset actionbar title
        sendActionbar(player, "");
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

    public static void sendActionbar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static String formatDuration(Duration duration) {
        String seconds = formatTimeWithZeros(String.valueOf(duration.toSecondsPart()), 2);
        String millis = formatTimeWithZeros(String.valueOf(duration.toMillisPart()), 3);
        return String.format(duration.toMinutes() + ":" + seconds + "." + millis);
    }

    public static String formatDuration(Duration duration, ChatColor textColor, ChatColor timeColor) {
        String seconds = formatTimeWithZeros(String.valueOf(duration.toSecondsPart()), 2);
        String millis = formatTimeWithZeros(String.valueOf(duration.toMillisPart()), 3);
        return String.format(timeColor + "" + duration.toMinutes() + textColor + ":" + timeColor + seconds + textColor + "." + timeColor + millis);
    }

    public static String formatTimeWithZeros(String string, int digits) {
        StringBuilder sb = new StringBuilder(string);
        while (sb.length() < digits) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static void displayParkourBestTimes(Player player) {
        player.sendMessage("Best times for the parkour:");
        HashMap<Integer, ParkourTime> times = sortParkourBestTimes(Main.getParkourBestTimes());
        String color;
        for (int i = 0; i < 10 && i < times.size(); i++) {
            switch (i) {
                case 0 -> color = ChatColor.GOLD + "";
                case 1 -> color = ChatColor.GRAY + "";
                case 2 ->color = ChatColor.DARK_GRAY + "";
                default -> color = ChatColor.WHITE + "";
            }
            player.sendMessage(color + (i + 1) + ". " +
                    Main.getParkourBestTimesNames().get(times.get(i).getUuid()) + ": " + ParkourUtils.formatDuration(times.get(i).getDuration()));
        }
    }

    public static class ParkourTime {
        public final Duration duration;
        public final UUID uuid;

        public ParkourTime(Duration duration, UUID uuid) {
            this.duration = duration;
            this.uuid = uuid;
        }

        public Duration getDuration() {
            return duration;
        }

        public UUID getUuid() {
            return uuid;
        }
    }

    public static HashMap<Integer, ParkourTime> sortParkourBestTimes(HashMap<UUID, Duration> originalHashMap) {
        HashMap<UUID, Duration> hashMap = new HashMap<>(originalHashMap);
        HashMap<Integer, ParkourTime> sortedHashMap = new HashMap<>();
        int i = 0;
        while (hashMap.size() > 0) {
            Duration lowestDuration = Duration.ofMillis(Long.MAX_VALUE);
            UUID lowestUUID = null;
            for (UUID uuid : hashMap.keySet()) {
                Duration duration = hashMap.get(uuid);
                if (duration.compareTo(lowestDuration) < 0) {
                    lowestDuration = duration;
                    lowestUUID = uuid;
                }
            }
            hashMap.remove(lowestUUID);
            sortedHashMap.put(i, new ParkourTime(lowestDuration, lowestUUID));
            i++;
        }
        return sortedHashMap;
    }
}
