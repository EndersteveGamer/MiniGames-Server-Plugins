package fr.enderstevegamer.fightforlobster.runnables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveEffects extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(
                    PotionEffectType.NIGHT_VISION, 60*20, 0, false, false, false
            ));
            player.setFoodLevel(20);
        }
    }
}
