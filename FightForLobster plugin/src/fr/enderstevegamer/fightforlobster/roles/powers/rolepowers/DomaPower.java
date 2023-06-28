package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DomaPower extends DurationPower {
    private final ArrayList<UUID> isOnIce = new ArrayList<>();
    private final HashMap<UUID, ArrayList<BlockState>> blocks = new HashMap<>();
    private static final double RADIUS = 24;
    private static final float SPEED = 1.6f;
    public DomaPower() {
        super (
                60000,
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
                                "Ice, you get +60% Speed, +10% Strength,",
                                "and +10% Resistance",
                                "Other players get -10% Strength",
                                "and -10% Resistance",
                                "The ice lasts 1 minute",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        blocks.put(player.getUniqueId(), new ArrayList<>());
        BlockUtils.forEachSphereBlock(player.getLocation(), RADIUS, (b) -> {
            if (b.isPassable()) b.setType(Material.AIR);
            else {
                if (b.getType().equals(Material.BLUE_ICE)) return;
                blocks.get(player.getUniqueId()).add(b.getState());
                b.setType(Material.BLUE_ICE);
            }
        });
        return true;
    }

    @Override
    protected void tickActivated(Player player) {

    }

    @Override
    protected void onEnd(Player player) {
        if (!blocks.containsKey(player.getUniqueId())) return;
        for (BlockState state : blocks.get(player.getUniqueId())) {
            if (state.getLocation().getBlock().isEmpty()) continue;
            state.update(true);
        }
        blocks.remove(player.getUniqueId());
    }

    @Override
    public void tickAlways(Player player) {
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

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (!(event.getDamager() instanceof Player damager)) return;
        if (damaged.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.BLUE_ICE)) {
            attackOnIce(damaged, event);
        }
        if (damager.getLocation().getBlock().getRelative(0, -1, 0).getType().equals(Material.BLUE_ICE)) {
            attackFromIce(damager, event);
        }
    }

    private void attackOnIce(Player damaged, EntityDamageByEntityEvent event) {
        if (Roles.getPlayerRole(damaged).equals(getRole())) {
            event.setDamage(event.getDamage() * 0.9);
        }
        else event.setDamage(event.getDamage() * 1.1);
    }

    private void attackFromIce(Player damager, EntityDamageByEntityEvent event) {
        if (Roles.getPlayerRole(damager).equals(getRole())) {
            event.setDamage(event.getDamage() * 1.1);
        }
        else event.setDamage(event.getDamage() * 0.9);
    }
}
