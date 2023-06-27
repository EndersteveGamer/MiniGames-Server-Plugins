package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DomaPower extends Power {
    private final ArrayList<UUID> isOnIce = new ArrayList<>();
    private static final double RADIUS = 24;
    private static final float SPEED = 1.6f;
    public DomaPower() {
        super (
                60000,
                Role.DOMA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Blood Frost",
                        "blood_frost",
                        List.of(
                                "Turns all blocks in a " + (int) RADIUS,
                                "blocks radius around you into",
                                "Blue Ice. When standing on Blue",
                                "Ice, you get +60% Speed",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        BlockUtils.forEachSphereBlock(player.getLocation(), RADIUS, (b) -> {
            if (b.isPassable()) {b.setType(Material.AIR); return;}
            b.setType(Material.BLUE_ICE);
        });
        return true;
    }

    @Override
    public void tick(Player player) {
        if (!Roles.getPlayerRole(player).equals(getRole())) return;
        Block block = player.getLocation().getBlock().getRelative(0, -1, 0);
        if (!block.getType().equals(Material.BLUE_ICE)) {
            boolean wasRemoved = isOnIce.remove(player.getUniqueId());
            if (wasRemoved) player.setWalkSpeed(player.getWalkSpeed() * (1/SPEED));
        }
        else if (!isOnIce.contains(player.getUniqueId())) {
            isOnIce.add(player.getUniqueId());
            player.setWalkSpeed(player.getWalkSpeed() * SPEED);
        }
    }
}
