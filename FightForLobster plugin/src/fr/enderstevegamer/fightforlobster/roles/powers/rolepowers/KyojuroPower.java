package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KyojuroPower extends DurationPower {
    private static final double RADIUS = 10;
    private static final int PARTICLE_PER_TICK = 10;
    private final HashMap<UUID, Location> spheres = new HashMap<>();
    public KyojuroPower() {
        super(
                60000,
                60000,
                Role.KYOJURO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Flame",
                        "flame_breath",
                        List.of(
                                "Creates a fire sphere around you, ",
                                "that lasts 1 minute",
                                "(2 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
        if (!spheres.containsKey(player.getUniqueId())) return;
        Location loc = spheres.get(player.getUniqueId());
        BlockUtils.forEachSphereBlock(loc, RADIUS,
                (b) -> {
            if (b.isPassable()) b.setType(Material.FIRE);
        });
        for (int i = 0; i < PARTICLE_PER_TICK; i++) {
            Location loc1 = BlockUtils.randomPointInSphere(loc, RADIUS);
            World world = loc1.getWorld();
            if (world == null) continue;
            world.spawnParticle(Particle.FLAME, loc1, 1, 0, 0, 0, 0);
        }
    }

    @Override
    protected void onEnd(Player player) {
        BlockUtils.forEachSphereBlock(spheres.get(player.getUniqueId()), RADIUS,
                (b) -> {
            if (b.getType().equals(Material.FIRE)) b.setType(Material.AIR);
        });
        spheres.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        spheres.put(player.getUniqueId(), player.getLocation());
        return true;
    }
}
