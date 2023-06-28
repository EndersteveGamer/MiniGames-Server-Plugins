package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class AkazaPower extends Power {
    private static final double RADIUS = 8;
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
        BlockUtils.forEachSphereBlock(player.getLocation(), RADIUS, (b) -> {
            b.setType(Material.AIR);
        });
        return true;
    }
}
