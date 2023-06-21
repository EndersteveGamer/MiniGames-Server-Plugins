package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GiyuPower extends DurationPower {
    HashMap<UUID, Location> spheres = new HashMap<>();
    protected GiyuPower() {
        super(
                2*60000,
                60000,
                Role.GIYU_TOMIOKA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Water",
                        "water_breath",
                        List.of(
                                "Create a water sphere of 32 blocks",
                                "radius around you for 1 minute",
                                "(2 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {}

    @Override
    protected void onEnd(Player player) {
        BlockUtils.forEachSphereBlock(spheres.get(player.getUniqueId()), 16, (b) -> b.setType(Material.AIR));
        spheres.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        spheres.put(player.getUniqueId(), player.getLocation());
        BlockUtils.forEachSphereBlock(player.getLocation(), 16, (b) -> b.setType(Material.WATER));
        return true;
    }
}
