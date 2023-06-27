package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MuichiroPower extends DurationPower {
    private static final double RADIUS = 16;
    private static final int PARTICLES_PER_TICK = 50;
    HashMap<UUID, Location> spheres = new HashMap<>();
    public MuichiroPower() {
        super(
                60000,
                60000,
                Role.MUICHIRO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Mist",
                        "mist_breath",
                        List.of(
                                "Creates a mist sphere around you,",
                                "inflicting blindness to all players",
                                "inside. Lasts 1 minute",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            for (UUID uuid : spheres.keySet()) {
                if (player1.getUniqueId().equals(uuid)) continue;
                if (player1.getLocation().distance(spheres.get(uuid)) > RADIUS) continue;
                player1.addPotionEffect(new PotionEffect(
                        PotionEffectType.BLINDNESS, 60, 2, false, false
                ));
            }
        }
        for (Location loc : spheres.values()) {
            for (int i = 0; i < PARTICLES_PER_TICK; i++) {
                Location particleLoc = BlockUtils.randomPointInSphere(loc, RADIUS);
                World world = particleLoc.getWorld();
                if (world == null) continue;
                world.spawnParticle(Particle.CLOUD, particleLoc, 5, 0, 0, 0, 0.5);
            }
        }
    }

    @Override
    protected void onEnd(Player player) {
        spheres.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        spheres.put(player.getUniqueId(), player.getLocation());
        return true;
    }
}
