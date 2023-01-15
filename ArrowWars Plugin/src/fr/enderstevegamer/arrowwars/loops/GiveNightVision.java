package fr.enderstevegamer.arrowwars.loops;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveNightVision extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 0, false, false));
        }
    }
}
