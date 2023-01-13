package fr.enderstevegamer.global.servers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.enderstevegamer.global.Main;
import fr.enderstevegamer.global.Utils.DecodingUtils;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public class Lobby {
    // Declare HashMap
    public static HashMap<UUID, Duration> parkourBestTimes;

    public Lobby() {
        parkourBestTimes = new HashMap<>();
    }


    public void onPluginMessageReceived(PluginMessageEvent event) {
        final ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        final String subChannel = in.readUTF();

        if (subChannel.equals("ParkourBestTimes")) {
            final String uuidStr = in.readUTF();
            final UUID uuid = UUID.fromString(uuidStr);
            final String durationStr = in.readUTF();
            final Duration duration = Duration.parse(durationStr);

            if (parkourBestTimes.containsKey(uuid)) {
                if (parkourBestTimes.get(uuid).compareTo(duration) > 0) {
                    parkourBestTimes.put(uuid, duration);
                }
            } else {
                parkourBestTimes.put(uuid, duration);
            }

            sendParkourBestTime("ParkourBestTimes", event.getReceiver(), parkourBestTimes);
        }
        else if (subChannel.equals("UpdateParkourBestTimes")) {
            sendParkourBestTime("UpdateParkourBestTimes", event.getReceiver(), parkourBestTimes);
        }
    }

    public static void sendParkourBestTime(String subChannel, Connection SendPlayer, HashMap<UUID, Duration> parkourBestTimes) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        out.writeUTF(DecodingUtils.DurationHashMap.toString.hashMap(parkourBestTimes));

        final ProxiedPlayer player = Main.getInstance().getProxy().getPlayer(SendPlayer.toString());
        player.getServer().sendData("endersteve:lobby", out.toByteArray());
    }

    public static HashMap<UUID, Duration> getParkourBestTimes() {
        return parkourBestTimes;
    }
}
