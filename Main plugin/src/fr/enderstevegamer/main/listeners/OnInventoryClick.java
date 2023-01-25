package fr.enderstevegamer.main.listeners;

import fr.enderstevegamer.main.utils.LobbyUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class OnInventoryClick implements Listener {
    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        LobbyUtils.sendPlayerToServer(player, event.getCurrentItem().getItemMeta().getLocalizedName());
    }
}
