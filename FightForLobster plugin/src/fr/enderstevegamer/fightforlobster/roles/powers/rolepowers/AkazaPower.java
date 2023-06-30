package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.List;

public class AkazaPower extends Power {
    private static final double RADIUS = 8;
    private static final int DAMAGE = 8;
    private static final int PARTICLE_COUNT = 50;
    public AkazaPower() {
        super(
                60000,
                Role.AKAZA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Destructive Execution",
                        "destructive_execution",
                        List.of(
                                "Create a " + (int) RADIUS + " blocks radius",
                                "crater around you",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        BlockUtils.forEachSphereBlock(player.getLocation(), RADIUS, (b) -> b.setType(Material.AIR));
        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 1);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            if (player1.getLocation().distance(player.getLocation()) > RADIUS) continue;
            PowerUtils.damageThroughArmor(player1, DAMAGE, player);
        }
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Location loc = BlockUtils.randomPointInSphere(player.getLocation(), RADIUS);
            player.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, loc, 0);
        }
        return true;
    }
}
