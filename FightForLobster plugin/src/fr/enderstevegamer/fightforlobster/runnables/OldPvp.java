package fr.enderstevegamer.fightforlobster.runnables;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OldPvp extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
            if (attribute == null) continue;
            attribute.setBaseValue(10000);
        }
    }
}
