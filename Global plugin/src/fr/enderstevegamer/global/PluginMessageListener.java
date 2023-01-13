package fr.enderstevegamer.global;

import fr.enderstevegamer.global.servers.Lobby;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;

public class PluginMessageListener implements Listener {
    // Declare classes
    public static Lobby lobby;

    // Constructor
    public PluginMessageListener() {
        lobby = new Lobby();
    }

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        switch (event.getTag()) {
            case "endersteve:lobby" -> lobby.onPluginMessageReceived(event);
        }
    }
}
