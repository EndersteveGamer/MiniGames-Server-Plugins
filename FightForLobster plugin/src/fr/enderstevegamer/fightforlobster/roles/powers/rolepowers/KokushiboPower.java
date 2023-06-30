package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KokushiboPower extends Power {
    private static final int PROJECTILES_NUM = 16;
    private static final double PROJECTILE_SPEED = 2;
    private static final double PROJECTILE_RADIUS = 5;
    private static final int PROJECTILE_DAMAGE = 2;
    private static final int SHOOT_FREQUENCY = 20;
    private final HashMap<UUID, Integer> tickCount = new HashMap<>();
    private final HashMap<UUID, ArrayList<MoonProjectile>> projectiles = new HashMap<>();
    private final HashMap<UUID, Integer> shooting = new HashMap<>();
    public KokushiboPower() {
        super(
                60000,
                Role.KOKUSHIBO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of the Moon",
                        "moon_breath",
                        List.of(
                                "Launches " + PROJECTILES_NUM + " moon shard projectiles",
                                "to where you are looking,",
                                "making " + PROJECTILE_DAMAGE/2 + " heart of true damage on hit",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        shooting.put(player.getUniqueId(), PROJECTILES_NUM);
        return true;
    }

    @Override
    public void tick(Player player) {
        if (!Roles.getPlayerRole(player).equals(getRole())) return;
        if (!tickCount.containsKey(player.getUniqueId())) tickCount.put(player.getUniqueId(), 0);
        tickCount.put(player.getUniqueId(), tickCount.get(player.getUniqueId()) + 1);
        shootProjectiles(player);
        updateProjectiles(player);
    }

    private void shootProjectiles(Player player) {
        if (tickCount.get(player.getUniqueId()) % SHOOT_FREQUENCY != 0) return;
        if (shooting.containsKey(player.getUniqueId())) {
            if (!projectiles.containsKey(player.getUniqueId())) projectiles.put(player.getUniqueId(), new ArrayList<>());
            projectiles.get(player.getUniqueId()).add(new MoonProjectile(player.getEyeLocation(), player));
            shooting.put(player.getUniqueId(), shooting.get(player.getUniqueId()) - 1);
            if (shooting.get(player.getUniqueId()) == 0) shooting.remove(player.getUniqueId());
        }
    }

    private void updateProjectiles(Player player) {
        ArrayList<MoonProjectile> projs = projectiles.get(player.getUniqueId());
        if (projs == null) return;
        if (projs.size() == 0) {projectiles.remove(player.getUniqueId()); return;}
        projs.forEach(MoonProjectile::tickProjectile);
        projs.removeIf(MoonProjectile::isRemoved);
    }

    @Override
    public void onPlayerDeath(Player player) {
        shooting.remove(player.getUniqueId());
        super.onPlayerDeath(player);
    }

    private static class MoonProjectile {
        private final Location loc;
        private final UUID shooter;
        private boolean remove;
        public MoonProjectile(Location loc, Player shooter) {
            this.loc = loc;
            this.shooter = shooter.getUniqueId();
            remove = false;
        }

        public void tickProjectile() {
            this.loc.add(this.loc.getDirection().clone().multiply(PROJECTILE_SPEED));
            createParticle();
            damagePlayers();
            if (!this.loc.getBlock().isPassable()) this.remove = true;
        }

        private void damagePlayers() {
            Player damager = Bukkit.getPlayer(this.shooter);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getUniqueId().equals(this.shooter)) continue;
                if (player.getLocation().distance(this.loc) > PROJECTILE_RADIUS) continue;
                PowerUtils.damageThroughArmor(player, PROJECTILE_DAMAGE, damager);
                this.remove = true;
            }
        }

        private void createParticle() {
            World world = this.loc.getWorld();
            if (world == null) return;
            Location location = this.loc.clone();
            world.spawnParticle(Particle.SWEEP_ATTACK, location, 1);
        }

        public boolean isRemoved() {
            return this.remove;
        }
    }
}
