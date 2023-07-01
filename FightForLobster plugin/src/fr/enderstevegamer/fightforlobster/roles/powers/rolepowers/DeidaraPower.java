package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.loot.LootTables;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeidaraPower extends Power {
    private static final int C1_NUM = 75;
    private static final double C1_RADIUS = 16;
    private static final int C1_DAMAGE = 2;
    private static final int C1_VERTICAL_SEARCH = 10;
    private static final double C1_ACTIVATION_RANGE = 0.5;
    private static final int C2_DAMAGE = 4;
    private static final int C2_HEALTH = 40;
    private static final int C2_SPAWN_HEIGHT = 3;
    private static final double C2_SPEED = 1;
    private static final int C2_DESPAWN_PARTICLES = 50;
    private static final double C2_DEATH_RADIUS = 8;
    private static final double C2_DEATH_DAMAGE = 6;
    private static final double C2_PROJECTILE_DETECTION_RADIUS = 2;
    private static final double C2_PROJECTILE_EXPLOSION_RADIUS = 7;
    private static final double C2_PROJECTILE_SPEED = 1.5;
    private static final long C2_PROJECTILE_COOLDOWN = 4000;
    private static final double C3_RADIUS = 8;
    private static final int C3_PROJECTILE_DAMAGE = 6;
    private static final double C4_TP_RADIUS = 50;
    private static final long C4_EXPLOSION_TIMER = 2000;
    private static final double C4_EXPLOSION_RADIUS = 16;
    private static final int C4_DAMAGE = 12;

    private final HashMap<UUID, ArrayList<Location>> mines = new HashMap<>();
    private final HashMap<UUID, GhastMount> ghastMounts = new HashMap<>();
    private final HashMap<UUID, Long> ghastCooldowns = new HashMap<>();
    private final HashMap<UUID, ArrayList<GhastProjectile>> ghastProjectiles = new HashMap<>();

    public DeidaraPower() {
        super(
                60000,
                Role.DEIDARA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Bakuton",
                        "bakuton",
                        List.of(
                                "Summons randomly one of these",
                                "capacities:",
                                "C1: Creates " + C1_NUM + " little mines in",
                                "a " + (int) C1_RADIUS + " blocks radius, inflicting",
                                C1_DAMAGE/2 + " heart of damage on explosion",
                                "C2: Summons a ghast mount, shooting",
                                "projectiles inflicting " + C2_DAMAGE/2 + " hearts",
                                "of damage",
                                "C3: Creates a " + (int) C3_RADIUS + " blocks radius",
                                "crater, inflicting " + C3_PROJECTILE_DAMAGE /2 + " hearts",
                                "of damage to players",
                                "C4: Teleports you randomly in a",
                                (int) C4_TP_RADIUS + " blocks radius, and replaces",
                                "you with a clay dummy, exploding after " + (int) (C4_EXPLOSION_TIMER/1000),
                                "seconds with a " + (int) C4_EXPLOSION_RADIUS + " blocks radius",
                                "inflicting " + C4_DAMAGE/2 + " hearts of damage",
                                "to players"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        int selectedPower = (int) (Math.random() * 4);
        selectedPower = 1;
        switch (selectedPower) {
            case 0 -> activateC1(player);
            case 1 -> activateC2(player);
            case 2 -> activateC3(player);
            case 3 -> activateC4(player);
        }
        return true;
    }

    private void activateC1(@NotNull Player player) {
        mines.put(player.getUniqueId(), getRandomMines(player));
    }

    private void tickC1(@NotNull Player player) {
        if (!mines.containsKey(player.getUniqueId())) return;
        ArrayList<Location> toRemove = new ArrayList<>();
        for (Location loc : mines.get(player.getUniqueId())) {
            World world = loc.getWorld();
            if (world == null) continue;
            world.spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 0,
                    new Particle.DustOptions(Color.WHITE, 2));
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.getUniqueId().equals(player.getUniqueId())) continue;
                if (player1.getLocation().distance(loc) > C1_ACTIVATION_RANGE) continue;
                PowerUtils.damageThroughArmor(player1, C1_DAMAGE, player);
                toRemove.add(loc);
                player1.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 1);
                player1.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            }
        }
        mines.get(player.getUniqueId()).removeAll(toRemove);
    }

    private @NotNull ArrayList<Location> getRandomMines(Player player) {
        ArrayList<Location> mines = new ArrayList<>();
        for (int i = 0; i < C1_NUM; i++) {
            Location loc = player.getLocation().clone();
            loc.setX(loc.getX() + (int) (((Math.random() - 0.5) * 2) * C1_RADIUS) + 0.5);
            loc.setZ(loc.getZ() + (int) (((Math.random() - 0.5) * 2) * C1_RADIUS) + 0.5);
            ArrayList<Location> locs = getVerticalPositions(loc);
            if (locs.size() == 0) continue;
            mines.add(locs.get((int) (Math.random() * locs.size())));
        }
        return mines;
    }

    private @NotNull ArrayList<Location> getVerticalPositions(@NotNull Location loc) {
        ArrayList<Location> result = new ArrayList<>();
        for (double y = (int) loc.getY() - C1_VERTICAL_SEARCH;
             y < (int) loc.getY() + C1_VERTICAL_SEARCH;
             y++) {
            Location loc1 = loc.clone();
            loc1.setY(y);
            if (loc1.getBlock().isPassable() && !loc1.getBlock().getRelative(0, -1, 0).isPassable()) {
                result.add(loc1);
            }
        }
        return result;
    }

    private void onDeathC1(@NotNull Player player) {
        mines.remove(player.getUniqueId());
    }

    private void activateC2(@NotNull Player player) {
        ghastMounts.put(player.getUniqueId(), new GhastMount(player));
    }

    private void tickC2(@NotNull Player player) {
        if (!ghastMounts.containsKey(player.getUniqueId())) return;
        GhastMount mount = ghastMounts.get(player.getUniqueId());
        if (mount.isUnmounted(player)) {
            removeMountProjectiles(player);
            ghastMounts.remove(player.getUniqueId());
            mount.destroyMount();
            return;
        }
        Ghast ghast = (Ghast) mount.getGhast();
        if (ghast == null) return;
        ghast.setVelocity(player.getLocation().getDirection().multiply(C2_SPEED));
        ghast.setCharging(false);
        ghast.setTarget(null);
        ghast.getLocation().setDirection(player.getLocation().getDirection());
        if (!ghastProjectiles.containsKey(ghast.getUniqueId())) ghastProjectiles.put(ghast.getUniqueId(),
                new ArrayList<>());
        ghastProjectiles.get(ghast.getUniqueId()).removeIf(GhastProjectile::wasRemoved);
        ghastProjectiles.get(ghast.getUniqueId()).forEach(GhastProjectile::tickProjectile);
        tryShootingProjectile(ghast, player);
    }

    private void tryShootingProjectile(@NotNull Entity ghast, Player owner) {
        if (!ghastCooldowns.containsKey(ghast.getUniqueId())) {
            shootProjectile(ghast, owner);
            ghastCooldowns.put(ghast.getUniqueId(), System.currentTimeMillis() + C2_PROJECTILE_COOLDOWN);
            return;
        }
        if (System.currentTimeMillis() < ghastCooldowns.get(ghast.getUniqueId())) return;
        shootProjectile(ghast, owner);
        ghastCooldowns.put(ghast.getUniqueId(), System.currentTimeMillis() + C2_PROJECTILE_COOLDOWN);
    }

    private void shootProjectile(@NotNull Entity ghast, Player owner) {
        if (!ghastProjectiles.containsKey(ghast.getUniqueId())) ghastProjectiles.put(ghast.getUniqueId(),
                new ArrayList<>());
        Location loc = ghast.getLocation();
        loc.setDirection(owner.getLocation().getDirection());
        ghastProjectiles.get(ghast.getUniqueId()).add(new GhastProjectile(loc, owner.getUniqueId()));
    }

    private void removeMountProjectiles(@NotNull Player player) {
        if (!ghastMounts.containsKey(player.getUniqueId())) return;
        GhastMount mount = ghastMounts.get(player.getUniqueId());
        if (!ghastProjectiles.containsKey(mount.getGhastUUID())) return;
        ArrayList<GhastProjectile> projectiles = ghastProjectiles.get(mount.getGhastUUID());
        projectiles.forEach(GhastProjectile::remove);
        ghastProjectiles.remove(mount.getGhastUUID());
    }

    public void onGhastTarget(EntityTargetEvent event) {
        for (GhastMount mount : ghastMounts.values()) {
            if (mount.getGhast() != null && mount.getGhast().getUniqueId().equals(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    private void onDeathC2(@NotNull Player player) {
        if (!ghastMounts.containsKey(player.getUniqueId())) return;
        ghastMounts.get(player.getUniqueId()).destroyMount();
        ghastMounts.remove(player.getUniqueId());
    }

    private void activateC3(Player player) {

    }

    private void activateC4(Player player) {

    }

    @Override
    public void tick(Player player) {
        tickC1(player);
        tickC2(player);
    }

    @Override
    public void onPlayerDeath(Player player) {
        onDeathC1(player);
        onDeathC2(player);
        super.onPlayerDeath(player);
    }

    private static class GhastMount {
        private final UUID ghastUUID;
        private final UUID horseUUID;
        private final UUID ownerUUID;
        public GhastMount(@NotNull Player player) {
            player.teleport(player.getLocation().clone().add(0, C2_SPAWN_HEIGHT, 0));
            Entity ghast = buildGhast(player);
            ghastUUID = ghast.getUniqueId();
            Entity horse = buildHorse(player, ghast);
            horseUUID = horse.getUniqueId();
            ownerUUID = player.getUniqueId();
        }

        private @NotNull Entity buildGhast(@NotNull Player player) {
            World world = player.getWorld();
            LivingEntity ghast = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.GHAST);
            ghast.setSilent(true);
            AttributeInstance health = ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (health == null) return ghast;
            health.setBaseValue(C2_HEALTH);
            ghast.setHealth(C2_HEALTH);
            Ghast trueGhast = (Ghast) ghast;
            trueGhast.setLootTable(LootTables.EMPTY.getLootTable());
            return ghast;
        }

        private @NotNull Entity buildHorse(@NotNull Player player, @NotNull Entity ghast) {
            World world = player.getWorld();
            LivingEntity horse = (LivingEntity) world.spawnEntity(player.getLocation(), EntityType.HORSE);
            horse.setSilent(true);
            horse.setInvulnerable(true);
            horse.setAI(false);
            horse.setInvisible(true);
            Horse trueHorse = (Horse) horse;
            trueHorse.setLootTable(LootTables.EMPTY.getLootTable());
            ghast.addPassenger(horse);
            horse.addPassenger(player);
            return horse;
        }

        public @Nullable Entity getGhast() {
            if (ghastUUID == null) return null;
            return Bukkit.getEntity(ghastUUID);
        }

        public UUID getGhastUUID() {return this.ghastUUID;}

        public @Nullable Entity getHorse() {
            if (horseUUID == null) return null;
            return Bukkit.getEntity(horseUUID);
        }

        public void destroyMount() {
            if (ghastUUID != null) {
                Entity ghast = Bukkit.getEntity(ghastUUID);
                if (ghast != null) {
                    for (int i = 0; i < C2_DESPAWN_PARTICLES; i++) {
                        World world = ghast.getWorld();
                        world.spawnParticle(Particle.CLOUD, ghast.getLocation(), 10, 3, 3, 3);
                    }
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getUniqueId().equals(ownerUUID)) continue;
                        if (player.getLocation().distance(ghast.getLocation()) > C2_DEATH_RADIUS) continue;
                        Player owner = Bukkit.getPlayer(ownerUUID);
                        PowerUtils.damageThroughArmor(player, C2_DEATH_DAMAGE, owner);
                    }
                    ghast.remove();
                }
            }
            if (horseUUID != null) {
                Entity horse = Bukkit.getEntity(horseUUID);
                if (horse != null) horse.remove();
            }
        }

        private boolean isUnmounted(Player player) {
            Entity ghast = getGhast();
            if (ghast == null) return true;
            Entity horse = getHorse();
            if (horse == null) return true;
            boolean isHorseOk = false;
            for (Entity entity : ghast.getPassengers()) {
                if (entity.getUniqueId().equals(horse.getUniqueId())) isHorseOk = true;
            }
            if (!isHorseOk) return true;
            boolean isPlayerOk = false;
            for (Entity entity : horse.getPassengers()) {
                if (entity.getUniqueId().equals(player.getUniqueId())) isPlayerOk = true;
            }
            return !isPlayerOk;
        }
    }

    private static class GhastProjectile {
        private final UUID blockUUID;
        private final Location loc;
        private final UUID ownerUUID;

        private boolean wasRemoved;

        public GhastProjectile(Location loc, @NotNull UUID ownerUUID) {
            this.loc = loc;
            this.blockUUID = buildFallingBlock(loc);
            this.ownerUUID = ownerUUID;
            this.wasRemoved = false;
        }

        private @Nullable UUID buildFallingBlock(@NotNull Location loc) {
            World world = loc.getWorld();
            if (world == null) return null;
            FallingBlock block = world.spawnFallingBlock(loc, Material.WHITE_CONCRETE.createBlockData());
            block.setDropItem(false);
            block.setGravity(false);
            block.setVelocity(new Vector(0, 0, 0));
            block.setInvulnerable(true);
            return block.getUniqueId();
        }

        public void remove() {
            if (blockUUID == null) return;
            Entity entity = Bukkit.getEntity(blockUUID);
            if (entity == null) return;
            entity.remove();
            this.wasRemoved = true;
        }

        public void tickProjectile() {
            moveProjectile();
            detectExplosion();
        }

        private void moveProjectile() {
            this.loc.add(loc.getDirection().clone().multiply(C2_PROJECTILE_SPEED));
            if (blockUUID == null) return;
            Entity block = Bukkit.getEntity(blockUUID);
            if (block == null) return;
            block.teleport(loc);
        }

        private void detectExplosion() {
            if (!loc.getBlock().isPassable()) {this.explode(); return;}
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getUniqueId().equals(ownerUUID)) continue;
                if (player.getLocation().distance(loc) > C2_PROJECTILE_DETECTION_RADIUS) continue;
                this.explode();
                return;
            }
        }

        private void explode() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getUniqueId().equals(ownerUUID)) continue;
                if (player.getLocation().distance(this.loc) > C2_PROJECTILE_EXPLOSION_RADIUS) continue;
                Player owner = Bukkit.getPlayer(ownerUUID);
                PowerUtils.damageThroughArmor(player, C2_DAMAGE, owner);
            }
            World world = this.loc.getWorld();
            if (world == null) return;
            world.spawnParticle(Particle.EXPLOSION_HUGE, this.loc, 1);
            this.remove();
        }

        public boolean wasRemoved() {return this.wasRemoved;}
    }
}
