package fr.enderstevegamer.global;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {
    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) {
        switch (event.getTag()) {
            case "endersteve:lobby" -> Main.getLobby().onPluginMessageReceived(event);
        }
    }
}
