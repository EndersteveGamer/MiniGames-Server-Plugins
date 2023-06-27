package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KyojuroPower extends DurationPower {
    private static final double RADIUS = 10;
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
        for (Location loc : spheres.values()) {
            BlockUtils.forEachSphereBlock(loc, RADIUS,
                    (b) -> {
                if (b.isPassable()) b.setType(Material.FIRE);
            });
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
