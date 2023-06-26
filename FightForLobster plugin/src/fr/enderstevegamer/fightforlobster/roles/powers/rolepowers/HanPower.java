package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.Main;
import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HanPower extends Power {
    private static final double STRENGTH = 5;
    private static final double DAMAGE_RADIUS = 4;
    private static final double DASH_DAMAGE = 8;
    private static final double VELOCITY_THRESHOLD = 0.1;
    private static final int GROUND_THRESHOLD = 7;
    private final ArrayList<UUID> dashing = new ArrayList<>();
    private final ArrayList<UUID> dontUpdate = new ArrayList<>();
    private final HashMap<UUID, Integer> groundTick = new HashMap<>();
    private final HashMap<UUID, ArrayList<UUID>> touchedByDash = new HashMap<>();
    public HanPower() {
        super(
                60000,
                Role.HAN,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Kokuo",
                        "kokuo",
                        List.of(
                                "Makes you dash forward, inflicting",
                                DASH_DAMAGE/2 + " hearts of true damage to touched",
                                "players",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        player.teleport(player.getLocation().add(new Vector(0, 1.5, 0)));
        Vector velocity = player.getLocation().getDirection();
        velocity.multiply(STRENGTH);
        dontUpdate.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setVelocity(velocity);
                dontUpdate.remove(player.getUniqueId());
            }
        }.runTaskLater(Main.getInstance(), 2);
        Bukkit.getLogger().info(player.getName() + ": " + player.getVelocity());
        dashing.add(player.getUniqueId());
        groundTick.put(player.getUniqueId(), 0);
        touchedByDash.put(player.getUniqueId(), new ArrayList<>());
        return true;
    }

    @Override
    public void tick(Player player) {
        if (!dashing.contains(player.getUniqueId())) return;
        if (dontUpdate.contains(player.getUniqueId())) return;
        if (((Entity) player).isOnGround()) {
            groundTick.put(player.getUniqueId(), groundTick.get(player.getUniqueId()) + 1);
        }
        if (player.getVelocity().length() < VELOCITY_THRESHOLD
                || groundTick.get(player.getUniqueId()) > GROUND_THRESHOLD) {
            dashing.remove(player.getUniqueId());
            touchedByDash.remove(player.getUniqueId());
            return;
        }
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            if (player1.getLocation().distance(player.getLocation()) > DAMAGE_RADIUS) continue;
            if (touchedByDash.containsKey(player.getUniqueId())
                    && touchedByDash.get(player.getUniqueId()).contains(player1.getUniqueId())) continue;
            PowerUtils.damageThroughArmor(player1, DASH_DAMAGE);
            touchedByDash.get(player.getUniqueId()).add(player1.getUniqueId());
        }
        player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 5);
    }

    @Override
    public void onPlayerDeath(Player player) {
        dashing.remove(player.getUniqueId());
        touchedByDash.remove(player.getUniqueId());
        super.onPlayerDeath(player);
    }
}
