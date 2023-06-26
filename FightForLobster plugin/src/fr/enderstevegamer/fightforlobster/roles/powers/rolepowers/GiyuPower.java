package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GiyuPower extends DurationPower {
    private static final double RADIUS = 16;
    HashMap<UUID, Location> spheres = new HashMap<>();
    public GiyuPower() {
        super(
                60000,
                60000,
                Role.GIYU_TOMIOKA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Water",
                        "water_breath",
                        List.of(
                                "Create a water sphere of " + RADIUS + " blocks",
                                "radius around you for 1 minute",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {}

    @Override
    protected void onEnd(Player player) {
        BlockUtils.forEachSphereBlock(spheres.get(player.getUniqueId()), RADIUS + 1,
                (b) -> {
                    if (b.getType().equals(Material.WATER)) b.setType(Material.AIR);
                });
        spheres.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        spheres.put(player.getUniqueId(), player.getLocation());
        BlockUtils.forEachSphereBlock(player.getLocation(), RADIUS,
                (b) -> {
            if (b.isPassable()) b.setType(Material.WATER);
        });
        return true;
    }

    public void onWaterFlow(BlockFromToEvent event) {
        Block from = event.getBlock();
        for (Location loc : spheres.values()) {
            if (BlockUtils.isInSphere(loc, RADIUS, from)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
