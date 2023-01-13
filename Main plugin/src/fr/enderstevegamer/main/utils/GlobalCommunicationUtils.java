package fr.enderstevegamer.main.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.enderstevegamer.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GlobalCommunicationUtils implements PluginMessageListener {
    public static void sendParkourBestTime(Player player, UUID uuid, Duration duration) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("ParkourBestTimes");
        out.writeUTF(uuid.toString());
        out.writeUTF(duration.toString());

        player.sendPluginMessage(Main.getInstance(), "endersteve:lobby", out.toByteArray());
    }

    public static void sendUpdateParkourBestTime() {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("UpdateParkourBestTimes");

        Player player = Bukkit.getOnlinePlayers().iterator().next();
        player.sendPluginMessage(Main.getInstance(), "endersteve:lobby", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("endersteve:lobby")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equals("ParkourBestTimes") || subChannel.equals("UpdateParkourBestTimes")) {
            // Get the updated HashMap
            Main.setParkourBestTimes(fromString.hashMap(in.readUTF()));

            // Send the parkour best times if subchannel is UpdateParkourBestTimes
            if (subChannel.equals("UpdateParkourBestTimes")) {
                for (UUID uuid : Main.getWaitingForParkourBestTimes()) {
                    Player playerSend = Bukkit.getPlayer(uuid);
                    if (playerSend != null) {
                        ParkourUtils.displayParkourBestTimes(playerSend);
                    }
                    Main.getWaitingForParkourBestTimes().remove(uuid);
                }
            }
        }
    }

    public static class toString {
        public static String hashMapKey(HashMap<UUID, Duration> hashMap, UUID key) {
            return key.toString() + ":" + hashMap.get(key).toString();
        }

        public static String hashMap(HashMap<UUID, Duration> hashMap) {
            ArrayList<String> keys = new ArrayList<>();
            StringBuilder result = new StringBuilder();
            for (UUID key : hashMap.keySet()) {
                keys.add(toString.hashMapKey(hashMap, key));
            }

            for (int i = 0; i < keys.size(); i++) {
                if (i == keys.size() - 1) {
                    result.append(keys.get(i));
                } else {
                    result.append(keys.get(i)).append(";");
                }
            }

            return result.toString();
        }
    }

    public static class fromString {
        public static HashMap<UUID, Duration> hashMap(String string) {
            if (string.equals("")) return new HashMap<>();
            HashMap<UUID, Duration> result = new HashMap<>();
            String[] keys = string.split(";");
            for (String key : keys) {
                String[] keySplit = key.split(":");
                result.put(UUID.fromString(keySplit[0]), Duration.parse(keySplit[1]));
            }
            return result;
        }
    }
}
