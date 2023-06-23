package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DakiPower extends Power {
    private static final int LENGTH = 50;
    private final HashMap<UUID, Boolean> isOnObi = new HashMap<>();
    private final List<Material> OBI_MATERIALS = List.of(
            Material.MAGENTA_WOOL,
            Material.YELLOW_WOOL,
            Material.BLACK_WOOL
    );
    private final float SPEED_BOOST = 1.4f;
    public DakiPower() {
        super(
                15000,
                Role.DAKI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Obi",
                        "obi",
                        List.of(
                                "Creates a line of wool in the direction",
                                "you are looking at",
                                "(30 sec cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        Location loc = player.getEyeLocation();
        loc.add(loc.getDirection().multiply(2));
        BlockIterator iterator = new BlockIterator(loc, 0, LENGTH);
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.isPassable()) block.setType(
                    switch ((int)(block.getLocation().distance(loc) % 6)) {
                        case 0, 1, 3, 4 -> Material.MAGENTA_WOOL;
                        case 2 -> Material.YELLOW_WOOL;
                        default -> Material.BLACK_WOOL;
                    }
            );
            else return true;
        }
        return true;
    }

    @Override
    public void tick(Player player) {
        if (!Roles.getPlayerRole(player).equals(getRole())) return;
        if (!isOnObi.containsKey(player.getUniqueId())) isOnObi.put(player.getUniqueId(), false);
        Block block = player.getLocation().add(0, -1, 0).getBlock();
        Material material = block.getType();
        if (isOnObi.get(player.getUniqueId())) {
            if (OBI_MATERIALS.contains(material)) return;
            player.setWalkSpeed(Roles.getPlayerRole(player).getRoleWalkSpeed());
            isOnObi.put(player.getUniqueId(), false);
        }
        else {
            if (!OBI_MATERIALS.contains(material)) return;
            player.setWalkSpeed(0.2f * SPEED_BOOST);
            isOnObi.put(player.getUniqueId(), true);
        }
    }
}
