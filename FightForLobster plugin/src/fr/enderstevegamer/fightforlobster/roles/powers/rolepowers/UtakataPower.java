package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UtakataPower extends DurationPower {
    private static final double SPHERE_RADIUS = 5;
    private static final int PARTICLES_PER_TICK = 5;
    private static final double SPHERE_DURATION = 20;
    private static final double DAMAGE_PER_TICK = 1;
    private final HashMap<UUID, ArrayList<PoisonSphere>> poisonTrails = new HashMap<>();
    private final HashMap<UUID, Integer> tickCounts = new HashMap<>();
    public UtakataPower() {
        super(
                60000,
                10000,
                Role.UTAKATA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Saiken",
                        "saiken",
                        List.of(
                                "Leaves a trail of acid behind",
                                "you for 10 seconds, lasting " + SPHERE_DURATION + " seconds",
                                "and inflicting Slowness I and damaging",
                                "players inside of it",
                                "(1 minute cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
        tickCounts.put(player.getUniqueId(), tickCounts.get(player.getUniqueId()) + 1);
        if (tickCounts.get(player.getUniqueId()) % 10 != 0) return;
        poisonTrails.get(player.getUniqueId()).add(new PoisonSphere(player.getLocation()));
    }

    @Override
    protected void onEnd(Player player) {
        tickCounts.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        poisonTrails.put(player.getUniqueId(), new ArrayList<>());
        tickCounts.put(player.getUniqueId(), 0);
        return true;
    }

    @Override
    protected void tickAlways(Player player) {
        ArrayList<UUID> damaged = new ArrayList<>();
        if (!poisonTrails.containsKey(player.getUniqueId())) return;
        poisonTrails.get(player.getUniqueId()).removeIf(PoisonSphere::isExpired);
        for (PoisonSphere sphere : poisonTrails.get(player.getUniqueId())) {
            for (int i = 0; i < PARTICLES_PER_TICK; i++) {
                Location loc = BlockUtils.randomPointInSphere(sphere.getLoc(), SPHERE_RADIUS);
                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, loc, 2, 0, 0, 0, 0.01);
            }
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (damaged.contains(player1.getUniqueId())) continue;
                if (player1.getUniqueId().equals(player.getUniqueId())) continue;
                if (player1.getLocation().distance(sphere.getLoc()) > SPHERE_RADIUS) continue;
                PowerUtils.damageThroughArmor(player1, DAMAGE_PER_TICK);
                damaged.add(player1.getUniqueId());
                player1.addPotionEffect(new PotionEffect(
                        PotionEffectType.SLOW, 20, 0, false, false
                ));
            }
        }
    }

    private record PoisonSphere(Location loc, double creationTime) {
        public PoisonSphere(Location loc) {
            this(loc, System.currentTimeMillis());
        }
        public Location getLoc() {return loc;}
        public boolean isExpired() {
            return System.currentTimeMillis() > creationTime + SPHERE_DURATION * 1000;
        }
    }
}
