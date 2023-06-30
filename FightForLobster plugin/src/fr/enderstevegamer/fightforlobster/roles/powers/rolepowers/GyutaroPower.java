package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class GyutaroPower extends Power {
    private static final double RADIUS = 10;
    private static final double PARTICLE_COUNT = 50;
    public GyutaroPower() {
        super(
                60000,
                Role.GYUTARO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Stale Blood",
                        "stale_blood",
                        List.of(
                                "Inflicts Wither II for 10 seconds to players",
                                "that are less than " + (int) RADIUS + " blocks away",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        boolean activated = false;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            if (player1.getLocation().distance(player.getLocation()) > RADIUS) continue;
            player1.addPotionEffect(new PotionEffect(
                    PotionEffectType.WITHER, 10*20, 1, true, true
            ));
            player1.sendMessage(ChatColor.RED + "You were withered by " + player.getName());
            activated = true;
        }
        if (activated) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                Location loc = BlockUtils.randomPointInSphere(player.getLocation(), RADIUS);
                player.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 1,
                        new Particle.DustOptions(Color.RED, 1));
            }
        }
        return activated;
    }
}
