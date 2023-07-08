package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HidanPower extends DurationPower {
    private static final double RADIUS = 10;
    private static final int PARTICLES_PER_TICK = 5;
    private static final HashMap<UUID, Location> circles = new HashMap<>();
    private static final HashMap<UUID, ArrayList<BlockState>> blocks = new HashMap<>();
    public HidanPower() {
        super(
                60000,
                60000,
                Role.HIDAN,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Jashin Ritual",
                        "jashin_ritual",
                        List.of(
                                "Creates a red circle around you.",
                                "While in this circle, you receive",
                                "25% less damage, and the attacker",
                                "receives the damage too",
                                "The circle lasts 1 minute",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
    }

    @Override
    protected void onEnd(Player player) {
        ArrayList<BlockState> states = blocks.get(player.getUniqueId());
        if (states == null) return;
        for (BlockState state : states) {
            if (state.getLocation().getBlock().getType().equals(Material.AIR)) continue;
            state.update(true);
        }
        blocks.remove(player.getUniqueId());
        circles.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        Location loc = getOnGroundLocation(player.getLocation());
        if (loc == null) return false;
        loc.add(0, 1, 0);
        circles.put(player.getUniqueId(), loc);
        blocks.put(player.getUniqueId(), new ArrayList<>());
        spawnCircle(player, loc);
        return true;
    }

    private Location getOnGroundLocation(Location location) {
        if (!location.getBlock().isPassable()) return location;
        Location loc = location.clone();
        while (loc.getY() > -65) {
            loc.add(0, -1, 0);
            if (!loc.getBlock().isPassable()) return loc;
        }
        return null;
    }

    private void spawnCircle(Player player, Location loc) {
        for (int x = -(int) RADIUS; x <= RADIUS; x++) {
            for (int y = -(int) RADIUS; y <= RADIUS; y++) {
                for (int z = -(int) RADIUS; z <= RADIUS; z++) {
                    if (Math.abs(x) < RADIUS && Math.abs(y) < RADIUS && Math.abs(z) < RADIUS) continue;
                    Location location = loc.getBlock().getRelative(x, y, z).getLocation();
                    location.setDirection(new Vector(
                            loc.getX() - location.getX(),
                            loc.getY() - location.getY(),
                            loc.getZ() - location.getZ()
                    ));
                    BlockIterator iterator = new BlockIterator(location, 0, 100);
                    while (iterator.hasNext()) {
                        Block block = iterator.next();
                        if (block.getLocation().distance(loc) > RADIUS) continue;
                        if (block.isPassable()) break;
                        if (block.getType().equals(Material.REDSTONE_BLOCK)) break;
                        if (block.getType().equals(Material.BLUE_ICE)) break;
                        blocks.get(player.getUniqueId()).add(block.getState());
                        block.setType(Material.REDSTONE_BLOCK);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (!isCircleAround(damaged)) return;
        event.setDamage(event.getDamage() * 0.75);
        if (!(event.getDamager() instanceof Damageable damager)) return;
        if (damager instanceof Player player) {
            if (player.getNoDamageTicks() > 0) return;
        }
        damager.damage(event.getDamage(), damaged);
    }

    @Override
    protected void tickAlways(Player player) {
        Location loc = circles.get(player.getUniqueId());
        if (loc == null) return;
        for (int i = 0; i < PARTICLES_PER_TICK; i++) {
            Location particleLoc = BlockUtils.randomPointInSphere(loc, RADIUS);
            World world = particleLoc.getWorld();
            if (world == null) return;
            world.spawnParticle(Particle.REDSTONE, particleLoc, 1, new Particle.DustOptions(Color.RED, 1));
        }
    }

    private boolean isCircleAround(Player player) {
        if (!circles.containsKey(player.getUniqueId())) return false;
        return circles.get(player.getUniqueId()).distance(player.getLocation()) < RADIUS;
    }
}
