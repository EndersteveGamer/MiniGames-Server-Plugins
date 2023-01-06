package fr.enderstevegamer.main.listeners;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.ParkourUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class OnInteract implements Listener {
    public static final int LEAPPOWER = 3;
    @EventHandler
    public static void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = event.getPlayer();

            // Parkour items
            if (player.getInventory().getItemInMainHand().getItemMeta() != null) {
                String name = player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
                switch (name) {
                    case "goBackToCheckpoint" -> player.teleport(Main.getPlayerSpawns().get(player.getUniqueId()));
                    case "quitParkour" -> ParkourUtils.quitParkour(player, true);
                    case "restartParkour" -> {
                        Location parkourStart = new Location(event.getPlayer().getWorld(), 0.5, 61, 22.5);
                        parkourStart.setDirection(new Vector(0, 0, 1));
                        player.teleport(parkourStart);
                        player.sendMessage(ChatColor.GOLD + "Parkour restarted!");
                    }
                    case "tntStick" -> {
                        Arrow arrow = player.launchProjectile(Arrow.class);
                        TNTPrimed tnt = player.getWorld().spawn(arrow.getLocation(), TNTPrimed.class);
                        tnt.setVelocity(arrow.getVelocity());
                        arrow.remove();
                    }
                    case "arrowStick" -> {player.launchProjectile(Arrow.class);}
                }
            }
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = event.getPlayer();

            // Cancel breaking blocks
            if (player.getGameMode() == GameMode.ADVENTURE) {
                event.setCancelled(true);
            }
        }
        else if (event.getAction() == Action.RIGHT_CLICK_BLOCK
                && event.getClickedBlock() != null
                && !(event.getClickedBlock().getState() instanceof Sign)) {
            event.setCancelled(true);
        }
    }
}
