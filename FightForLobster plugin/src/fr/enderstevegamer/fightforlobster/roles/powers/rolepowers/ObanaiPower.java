package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ObanaiPower extends Power {
    private static final double RADIUS = 20;
    private static final int PARTICLE_COUNT = 50;
    public ObanaiPower() {
        super(
                30000,
                Role.OBANAI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Snake",
                        "snake_breath",
                        List.of(
                                "Inflicts Poison III for 10 seconds to players",
                                "up to " + (int) RADIUS + " blocks away",
                                "(30 sec cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        boolean activated = false;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getLocation().distance(player.getLocation()) > RADIUS) continue;
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            player1.addPotionEffect(new PotionEffect(
                    PotionEffectType.POISON, 10*20, 2, false, true
            ));
            player1.sendMessage(ChatColor.RED + "You were poisoned by " + player.getName());
            activated = true;
        }
        if (activated) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                Location loc = BlockUtils.randomPointInSphere(player.getLocation(), RADIUS);
                player.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 1,
                        new Particle.DustOptions(Color.GREEN, 1));
            }
        }
        return activated;
    }
}
