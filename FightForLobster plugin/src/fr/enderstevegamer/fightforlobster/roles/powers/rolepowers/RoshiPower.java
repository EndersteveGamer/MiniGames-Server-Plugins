package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RoshiPower extends Power {
    private static final int PROJECTILES_NUM = 4;
    private static final int LAUNCH_COOLDOWN = 20;
    private static final double EXPLOSION_RADIUS = 5;
    private static final int DAMAGE = 2;
    private static final HashMap<UUID, ArrayList<UUID>> fireballs = new HashMap<>();
    private static final HashMap<UUID, Integer> projectilesLeft = new HashMap<>();
    private static final HashMap<UUID, Integer> launchCooldown = new HashMap<>();
    public RoshiPower() {
        super(
                60000,
                Role.ROSHI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Son Goku",
                        "son_goku",
                        List.of(
                                "Throws " + PROJECTILES_NUM + " fireballs,",
                                "making " + DAMAGE/2 + " of true damage",
                                "and placing lava",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        if (!projectilesLeft.containsKey(player.getUniqueId())) projectilesLeft.put(player.getUniqueId(), 0);
        projectilesLeft.put(player.getUniqueId(), projectilesLeft.get(player.getUniqueId()) + PROJECTILES_NUM);
        if (!launchCooldown.containsKey(player.getUniqueId())) launchCooldown.put(player.getUniqueId(), 0);
        if (!fireballs.containsKey(player.getUniqueId())) fireballs.put(player.getUniqueId(), new ArrayList<>());
        return true;
    }

    private UUID spawnFireball(Player player) {
        return player.launchProjectile(Fireball.class).getUniqueId();
    }

    @Override
    public void tick(Player player) {
        if (launchCooldown.containsKey(player.getUniqueId())) launchCooldown.put(
                player.getUniqueId(),
                launchCooldown.get(player.getUniqueId()) - 1);
        if (!projectilesLeft.containsKey(player.getUniqueId()) || projectilesLeft.get(player.getUniqueId()) <= 0) return;
        if (!launchCooldown.containsKey(player.getUniqueId()) || launchCooldown.get(player.getUniqueId()) > 0) return;
        if (!fireballs.containsKey(player.getUniqueId())) return;
        fireballs.get(player.getUniqueId()).add(spawnFireball(player));
        projectilesLeft.put(player.getUniqueId(), projectilesLeft.get(player.getUniqueId()) - 1);
        launchCooldown.put(player.getUniqueId(), LAUNCH_COOLDOWN);
    }

    public void onProjectileLand(ProjectileHitEvent event) {
        for (UUID uuid : fireballs.keySet()) {
            if (fireballs.get(uuid).contains(event.getEntity().getUniqueId())) {
                Projectile fireball = event.getEntity();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().distance(fireball.getLocation()) > EXPLOSION_RADIUS) continue;
                    PowerUtils.damageThroughArmor(player, DAMAGE, Bukkit.getPlayer(uuid));
                }
                fireball.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, fireball.getLocation(), 1);
                fireball.getLocation().getBlock().setType(Material.LAVA);
                BlockUtils.dontUpdateLiquid(fireball.getLocation().getBlock());
                event.setCancelled(true);
                fireball.remove();
                fireballs.get(uuid).remove(fireball.getUniqueId());
            }
        }
    }

    @Override
    public void onPlayerDeath(Player player) {
        if (!fireballs.containsKey(player.getUniqueId())) return;
        for (UUID uuid : fireballs.get(player.getUniqueId())) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            entity.remove();
        }
        fireballs.remove(player.getUniqueId());
    }
}
