package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
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
    private final HashMap<UUID, Location> spheres = new HashMap<>();
    private static final double RADIUS = 24;
    private static final float SPEED = 1.6f;
    private static final int PARTICLES_PER_TICK = 30;
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
        spheres.put(player.getUniqueId(), player.getLocation());
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
        spheres.remove(player.getUniqueId());
    }

    @Override
    public void tickAlways(Player player) {
        if (!Roles.getPlayerRole(player).equals(getRole())) return;
        if (!isOnOwnIce(player)) {
            boolean wasRemoved = isOnIce.remove(player.getUniqueId());
            if (wasRemoved) player.setWalkSpeed(player.getWalkSpeed() * (1/SPEED));
        }
        else if (!isOnIce.contains(player.getUniqueId())) {
            isOnIce.add(player.getUniqueId());
            player.setWalkSpeed(player.getWalkSpeed() * SPEED);
        }
        for (Location loc : spheres.values()) {
            for (int i = 0; i < PARTICLES_PER_TICK; i++) {
                Location particleLoc = BlockUtils.randomPointInSphere(loc, RADIUS);
                player.getWorld().spawnParticle(Particle.SNOWFLAKE, particleLoc, 0, 0, -0.5, 0);
            }
        }
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (!(event.getDamager() instanceof Player damager)) return;
        if (isOnOwnIce(damager)) event.setDamage(event.getDamage() * 1.1);
        if (isOnOwnIce(damaged)) event.setDamage(event.getDamage() * 0.9);
        if (isOnEnemyIce(damager)) event.setDamage(event.getDamage() * 0.9);
        if (isOnEnemyIce(damaged)) event.setDamage(event.getDamage() * 1.1);
    }

    private boolean isOnOwnIce(Player player) {
        if (!spheres.containsKey(player.getUniqueId())) return false;
        return spheres.get(player.getUniqueId()).distance(player.getLocation()) <= RADIUS;
    }

    private boolean isOnEnemyIce(Player player) {
        for (UUID uuid : spheres.keySet()) {
            if (uuid.equals(player.getUniqueId())) continue;
            if (spheres.get(uuid).distance(player.getLocation()) < RADIUS) return true;
        }
        return false;
    }
}
