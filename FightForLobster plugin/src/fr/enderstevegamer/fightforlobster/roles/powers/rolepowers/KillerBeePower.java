package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class KillerBeePower extends Power {
    private static final double RADIUS = 15;
    private static final double BLIND_TIME = 20;
    private static final int PARTICLES_NUM = 1000;
    public KillerBeePower() {
        super(
                60000,
                Role.KILLER_BEE,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Gyuki",
                        "gyuki",
                        List.of(
                                "Blinds all players for 20 seconds",
                                "in a 15 blocks radius",
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
                    PotionEffectType.BLINDNESS, (int) (BLIND_TIME*20), 2, false, false
            ));
            player1.sendMessage(ChatColor.RED + "You were blinded by " + player.getName());
            activated = true;
        }
        if (activated) {
            for (int i = 0; i < PARTICLES_NUM; i++) {
                Location loc = BlockUtils.randomPointInSphere(player.getLocation(), RADIUS);
                if (loc.getWorld() == null) continue;
                loc.getWorld().spawnParticle(Particle.SQUID_INK, loc, 5);
            }
        }
        return activated;
    }
}
