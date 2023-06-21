package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class SanemiPower extends Power {
    private static final double STRENGTH = 5;
    protected SanemiPower() {
        super(
                120000,
                Role.SANEMI_SHINAZUGAWA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Wind",
                        "wind_breath",
                        List.of(
                                "Knockbacks nearby players (<10 blocks)",
                                "to at least 40 blocks",
                                "(2 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        boolean activated = false;
        Location loc1 = player.getLocation();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(player1.getLocation()) > 10) continue;
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            activated = true;
            Location loc2 = player1.getLocation();
            Vector vect = new Vector(loc2.getX() - loc1.getX(),
                    loc2.getY() - loc1.getY(),
                    loc2.getZ() - loc1.getZ());
            vect.normalize();
            vect.multiply(STRENGTH);
            player1.setVelocity(vect);
        }
        if (!activated) return false;
        World world = player.getWorld();
        world.playSound(loc1, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
        world.spawnParticle(Particle.EXPLOSION_HUGE, loc1, 1);
        return true;
    }
}
