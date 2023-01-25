package fr.enderstevegamer.global.servers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.enderstevegamer.global.Main;
import fr.enderstevegamer.global.utils.DecodingUtils;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;

import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

public class Lobby {
    // Declare HashMap
    public HashMap<UUID, Duration> parkourBestTimes;

    public Lobby() {
        parkourBestTimes = new HashMap<>();
    }


    public void onPluginMessageReceived(PluginMessageEvent event) {
        final ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        final String subChannel = in.readUTF();

        switch (subChannel) {
            case "ParkourBestTimes" -> {
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
            case "UpdateParkourBestTimes" ->
                    sendParkourBestTime("UpdateParkourBestTimes", event.getReceiver(), parkourBestTimes);
            case "PlayerLogin" -> {
                final String uuidStr = in.readUTF();
                final UUID uuid = UUID.fromString(uuidStr);
                final String username = in.readUTF();

                Main.getPlayerUsernames().put(uuid, username);
                BungeeCord.getInstance().getLogger().info("Player " + username + " logged in!");
            }
        }
    }

    public static void sendParkourBestTime(String subChannel, Connection SendPlayer, HashMap<UUID, Duration> parkourBestTimes) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        out.writeUTF(DecodingUtils.DurationHashMap.toString.hashMap(parkourBestTimes));
        out.writeUTF(DecodingUtils.UUIDStringHashMap.toString.hashMap(Main.getPlayerUsernames()));

        final ProxiedPlayer player = Main.getInstance().getProxy().getPlayer(SendPlayer.toString());
        player.getServer().sendData("endersteve:lobby", out.toByteArray());
    }

    public HashMap<UUID, Duration> getParkourBestTimes() {
        return parkourBestTimes;
    }

    public void setParkourBestTimes(HashMap<UUID, Duration> parkourBestTimes) {
        this.parkourBestTimes = parkourBestTimes;
    }
}
