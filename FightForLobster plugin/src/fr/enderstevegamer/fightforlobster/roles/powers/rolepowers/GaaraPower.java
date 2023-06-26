package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;

public class GaaraPower extends Power {
    public GaaraPower() {
        super(
                60000,
                Role.GAARA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Shukaku",
                        "shukaku",
                        List.of(
                                "Creates a 7x7x7 cube of sand",
                                "around the targeted block",
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
                Location loc = block.getLocation();
                BlockUtils.forEachBlockInCenteredCube(loc, 7, (b) -> {
                    if (!b.isPassable()) return;
                    b.setType(Material.SAND);
                });
                return true;
            }
        }
        return false;
    }
}
