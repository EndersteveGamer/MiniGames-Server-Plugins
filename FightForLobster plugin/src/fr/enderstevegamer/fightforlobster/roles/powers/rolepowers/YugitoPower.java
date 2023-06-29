package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;

public class YugitoPower extends Power {
    private static final double RADIUS = 24;
    private static final int PARTICLE_COUNT = 300;
    public YugitoPower() {
        super(
                60000,
                Role.YUGITO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Matatabi",
                        "matatabi",
                        List.of(
                                "Creates fire in a",
                                (int) RADIUS + " blocks sphere around the",
                                "targeted block",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        BlockIterator iterator = new BlockIterator(player, 100);
        boolean isPassable = false;
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.isPassable()) isPassable = true;
            else if (isPassable) {
                BlockUtils.forEachSphereBlock(block.getLocation(), RADIUS, (b) -> {
                    if (!b.isPassable()) return;
                    b.setType(Material.FIRE);
                });
                for (int i = 0; i < PARTICLE_COUNT; i++) {
                    Location loc = BlockUtils.randomPointInSphere(block.getLocation(), RADIUS);
                    player.getWorld().spawnParticle(Particle.FLAME, loc, 0);
                }
                return true;
            }
        }
        return false;
    }
}
