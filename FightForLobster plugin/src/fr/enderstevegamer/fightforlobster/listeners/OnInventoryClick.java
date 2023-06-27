package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.RoleSelector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {
    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        RoleSelector.onInventoryClick(event);
    }
}
