package fr.enderstevegamer.fightforlobster.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.HashMap;

public class PowerUtils {
    public static Player getTargetedPlayer(Player player) {
        HashMap<Player, Double> targetedPlayers = new HashMap<>();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;
            if (isLookingAt(player, player1)) {
                targetedPlayers.put(player1, player.getLocation().distance(player1.getLocation()));
            }
        }
        if (targetedPlayers.size() == 0) return null;
        Player selectedPlayer = (Player) targetedPlayers.keySet().toArray()[0];
        double minDistance = targetedPlayers.get(selectedPlayer);
        for (Player player1 : targetedPlayers.keySet()) {
            if (targetedPlayers.get(player1) < minDistance) {
                selectedPlayer = player1; minDistance = targetedPlayers.get(player1);
            }
        }
        return selectedPlayer;
    }

    public static boolean isLookingAt(Player player, Player player1) {
        Location eye = player.getEyeLocation();
        Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.99D;
    }

    public static boolean canTeleportHere(Location loc) {
        return loc.getBlock().isPassable() && loc.getBlock().getRelative(0, 1, 0).isPassable();
    }

    public static void damageThroughArmor(Player player, double damage, @Nullable Entity cause) {
        if (player.getNoDamageTicks() > 0) return;
        player.damage(1, cause);
        if (player.getHealth() - damage < 0) player.setHealth(0);
        else player.setHealth(player.getHealth() - damage);
    }

    public static void damageThroughArmor(Player player, double damage) {
        damageThroughArmor(player, damage, null);
    }

    public static Location getTargetedLocation(Player player, int maxDistance) {
        BlockIterator iterator = new BlockIterator(player.getLocation(), 0, maxDistance);
        Location loc = null;
        while (iterator.hasNext()) loc = iterator.next().getLocation();
        return loc;
    }

    public static Vector vectorFromLocations(Location start, Location end) {
        return new Vector(
                end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ()
        );
    }
}
