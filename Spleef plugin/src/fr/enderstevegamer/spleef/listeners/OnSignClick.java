package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.utils.Rules;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnSignClick implements Listener {
    @EventHandler
    public static void onSignClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getState() instanceof Sign sign && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                String language;
                Location english = new Location(event.getPlayer().getWorld(), 17, 14, 52);
                Location french = new Location(event.getPlayer().getWorld(), 15, 14, 52);
                if (sameCoords(sign.getLocation(), english)) {
                    language = "en";
                } else if (sameCoords(sign.getLocation(), french)) {
                    language = "fr";
                } else {
                    language = "en";
                }
                Player player = event.getPlayer();
                for (String line : Rules.getRules(language)) {
                    player.sendMessage(line);
                }
            }
        }
    }

    public static boolean sameCoords(Location loc1, Location loc2) {
        return loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ();
    }
}
