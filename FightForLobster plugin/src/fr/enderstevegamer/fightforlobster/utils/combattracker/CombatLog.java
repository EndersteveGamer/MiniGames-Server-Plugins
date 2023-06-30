package fr.enderstevegamer.fightforlobster.utils.combattracker;

import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatDamage;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatDamageCause;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatEvent;
import fr.enderstevegamer.fightforlobster.utils.combattracker.events.CombatEventType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CombatLog {
    private final ArrayList<UUID> playersParticipating;
    private final ArrayList<Object> events;
    private final long startTime;
    private final UUID combatOwner;

    public CombatLog(UUID combatOwner) {
        this.playersParticipating = new ArrayList<>();
        this.events = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        this.combatOwner = combatOwner;
        events.add(new CombatEvent(CombatEventType.COMBAT_START));
    }

    public void addDamage(CombatDamage damage) {
        events.add(damage);
        if (damage.getDamageCause().equals(CombatDamageCause.ENTITY)
                && damage.getEntityCause() != null
                && damage.getEntityCause() instanceof Player player
                && !playersParticipating.contains(player.getUniqueId())) {
            playersParticipating.add(player.getUniqueId());
            events.add(new CombatEvent(CombatEventType.PLAYER_JOIN_COMBAT, player.getUniqueId()));
        }
    }

    public void addEvent(CombatEvent event) {
        events.add(event);
    }

    public String combatEventToString(CombatEvent event) {
        String time = ChatColor.RESET +  "(" + CombatTrackerUtils.getCombatTime(startTime, event.getTime()) + "s) ";
        switch (event.getEventType()) {
            case COMBAT_START -> {return time + ChatColor.GREEN + "The combat started!";}
            case COMBAT_END -> {return time + ChatColor.RED + "The combat ended!";}
        }
        if (event.getEventType().equals(CombatEventType.PLAYER_JOIN_COMBAT)) {
            if (event.getEventPlayer() == null) return "";
            OfflinePlayer player = Bukkit.getOfflinePlayer(event.getEventPlayer());
            return time + player.getName() + " joined the fight!";
        }
        return "";
    }

    public String damageToString(CombatDamage damage) {
        String damageDesc = ChatColor.RESET + "(" + CombatTrackerUtils.getCombatTime(startTime, damage.getTime())
                + "s) ";
        damageDesc += switch (damage.getDamageCause()) {
            case FIRE -> "Fire damage";
            case DROWN -> "Drowning damage";
            case POISON -> "Poison effect";
            case WITHER -> "Wither effect";
            case LAVA -> "Lava damage";
            case UNKNOWN -> "Unknown damage";
            default -> "";
        };
        if (damage.getDamageCause().equals(CombatDamageCause.ENTITY)) {
            if (damage.getEntityCause() == null) return ChatColor.RED + "ERROR: Entity is null";
            damageDesc += "Attacked by " + damage.getEntityCause().getName() + ChatColor.RESET;
        }
        return damageDesc + ": " + damage.getDamageCount() + "HP";
    }

    public String logObjectToString(Object obj) {
        if (obj instanceof CombatEvent event) return combatEventToString(event);
        if (obj instanceof CombatDamage damage) return damageToString(damage);
        return obj.toString();
    }

    public List<String> getLogString() {
        ArrayList<String> logs = new ArrayList<>();
        logs.add("Combat for " + Bukkit.getOfflinePlayer(this.combatOwner).getName());
        if (playersParticipating.size() > 0) logs.add("Players participating:");
        for (UUID uuid : playersParticipating) {
            logs.add(Bukkit.getOfflinePlayer(uuid).getName());
        }
        logs.add("");
        for (Object obj : events) {
            logs.add(logObjectToString(obj));
        }
        return logs;
    }
}
