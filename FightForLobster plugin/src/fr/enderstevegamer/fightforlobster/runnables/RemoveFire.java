package fr.enderstevegamer.fightforlobster.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveFire extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) continue;
            player.setFireTicks(0);
        }
    }
}
