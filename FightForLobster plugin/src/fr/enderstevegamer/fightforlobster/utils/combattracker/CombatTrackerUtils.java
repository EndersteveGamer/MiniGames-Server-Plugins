package fr.enderstevegamer.fightforlobster.utils.combattracker;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.UUID;

public class CombatTrackerUtils {
    private static final HashMap<UUID, CombatTracker> trackers = new HashMap<>();

    public static void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!trackers.containsKey(player.getUniqueId())) trackers.put(player.getUniqueId(),
                new CombatTracker(player.getUniqueId()));
        trackers.get(player.getUniqueId()).onPlayerDamage(event);
    }

    public static void onPlayerDeath(PlayerDeathEvent event) {
        if (!trackers.containsKey(event.getEntity().getUniqueId())) return;
        trackers.get(event.getEntity().getUniqueId()).onPlayerDeath(event);
    }

    public static int getCombatTime(long combatStartTime, long eventTime) {
        long combatPosition = eventTime - combatStartTime;
        return (int) (combatPosition / 1000);
    }

    public static void openTracker(Player player) {
        if (!trackers.containsKey(player.getUniqueId())) trackers.put(player.getUniqueId(),
                new CombatTracker(player.getUniqueId()));
        CombatTracker tracker = trackers.get(player.getUniqueId());
        tracker.openTrackerLog(player);
    }
}
