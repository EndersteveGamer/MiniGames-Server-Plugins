package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SasoriPower extends Power {
    private static final double SPAWN_RADIUS = 4;
    private static final double HEIGHT_TRY_THRESHOLD = 4;
    private static final int MAX_SPAWN_TRIES = 10;
    private static final int PUPPET_DAMAGE = 2;
    private static final int HIRUKO_DAMAGE = 4;
    private static final int HIRUKO_THORNS = 2;
    private static final int HIRUKO_POISON_RADIUS = 4;
    private static final int HIRUKO_PARTICLES_PER_TICK = 10;
    private static final int SANDAIME_DAMAGE = 2;
    private static final double SANDAIME_RADIUS = 5;
    private static final double SANDAIME_POWER_TIME = 10000;
    private static final double SANDAIME_IRON_DURATION = 10000;
    private static final int SANDAIME_IRON_DAMAGE_SEC = 2;
    private static final int SANDAIME_PARTICLES_PER_TICK = 20;
    private final HashMap<UUID, PuppetManager> managers = new HashMap<>();
    private final HashMap<UUID, Long> lastSandaimeLaunch = new HashMap<>();
    private final ArrayList<SandaimeIron> sandaimeIron = new ArrayList<>();
    public SasoriPower() {
        super(
                30000,
                Role.SASORI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Puppetry",
                        "puppetry",
                        List.of(
                                "Summons puppets to",
                                "fight for you",
                                "(30 sec cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(@NotNull Player player) {
        if (!managers.containsKey(player.getUniqueId())) managers.put(player.getUniqueId(), new PuppetManager());
        summonEntities(player);
        return true;
    }

    private void summonEntities(Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        double random = Math.random();
        if (random > 2/3f) {
            for (int i = 0; i < 10; i++) manager.getPuppets().add(buildPuppet(player.getWorld(),
                    getRandomSpawn(player)));
        }
        else if (random > 1/3f) {
            manager.getHiruko().add(buildHiruko(player.getWorld(), getRandomSpawn(player)));
        }
        else manager.getSandaime().add(buildSandaime(player.getWorld(), getRandomSpawn(player)));
    }

    private Location getRandomSpawn(Player player, int iter) {
        if (iter <= 0) return player.getLocation();
        double x = ((Math.random() * 2) - 0.5) * SPAWN_RADIUS;
        double z = ((Math.random() * 2) - 0.5) * SPAWN_RADIUS;
        Location loc = player.getLocation().clone();
        loc.add(x, 0, z);
        if (isValidSpawnLoc(loc)) return loc;
        loc.add(0, -HEIGHT_TRY_THRESHOLD, 0);
        for (int i = 0; i < HEIGHT_TRY_THRESHOLD*2; i++) {
            loc.add(0, 1, 0);
            if (isValidSpawnLoc(loc)) return loc;
        }
        return getRandomSpawn(player, iter - 1);
    }

    private Location getRandomSpawn(Player player) {
        return getRandomSpawn(player, MAX_SPAWN_TRIES);
    }

    private boolean isValidSpawnLoc(@NotNull Location loc) {
        return loc.getBlock().isPassable() && loc.clone().add(0, 1, 0).getBlock().isPassable();
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player puppetOwner = getPuppetOwner(event.getEntity());
        if (puppetOwner != null && puppetOwner.getUniqueId().equals(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (isSelfHarm(event)) return;
        checkHirukoThorns(event);
        if (!(event.getEntity() instanceof Player player)) return;
        Entity entity = event.getDamager();
        if (isSasoriPuppet(entity)) event.setCancelled(true);
        if (isPuppet(entity)) PowerUtils.damageThroughArmor(player, PUPPET_DAMAGE, puppetOwner);
        if (isHiruko(entity)) PowerUtils.damageThroughArmor(player, HIRUKO_DAMAGE, puppetOwner);
        if (isSandaime(entity)) PowerUtils.damageThroughArmor(player, SANDAIME_DAMAGE, puppetOwner);
    }

    private boolean isSelfHarm(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return false;
        Player puppetOwner = getPuppetOwner(event.getDamager());
        if (puppetOwner == null) return false;
        if (damaged.getUniqueId().equals(puppetOwner.getUniqueId())) {event.setCancelled(true); return true;}
        return false;
    }

    private Player getPuppetOwner(Entity entity) {
        for (UUID uuid : managers.keySet()) {
            PuppetManager manager = managers.get(uuid);
            if (manager.getAllPuppets().contains(entity.getUniqueId())) return Bukkit.getPlayer(uuid);
        }
        return null;
    }

    public void checkHirukoThorns(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;
        if (!isHiruko(event.getEntity())) return;
        PowerUtils.damageThroughArmor(player, HIRUKO_THORNS, getPuppetOwner(event.getEntity()));
    }

    public void onEntityDeath(EntityDeathEvent event) {
        if (isSasoriPuppet(event.getEntity())) event.getDrops().clear();
        event.setDroppedExp(0);
    }

    private boolean isSasoriPuppet(Entity entity) {
        return isPuppet(entity) || isHiruko(entity) || isSandaime(entity);
    }

    private boolean isPuppet(Entity entity) {
        for (UUID playerUUID : managers.keySet()) {
            PuppetManager manager = managers.get(playerUUID);
            if (manager.getPuppets().contains(entity.getUniqueId())) return true;
        }
        return false;
    }

    private boolean isHiruko(Entity entity) {
        for (UUID playerUUID : managers.keySet()) {
            PuppetManager manager = managers.get(playerUUID);
            if (manager.getHiruko().contains(entity.getUniqueId())) return true;
        }
        return false;
    }

    private boolean isSandaime(Entity entity) {
        for (UUID playerUUID : managers.keySet()) {
            PuppetManager manager = managers.get(playerUUID);
            if (manager.getSandaime().contains(entity.getUniqueId())) return true;
        }
        return false;
    }

    private @NotNull UUID buildPuppet(@NotNull World world, Location loc) {
        final double SPEED = 1.6;
        final String NAME = ChatColor.RED + "Puppet";
        Entity entity = world.spawnEntity(loc, EntityType.HUSK);
        world.spawnParticle(Particle.SQUID_INK, loc, 10, 1, 2, 1);
        Husk husk = (Husk) entity;
        AttributeInstance speed = husk.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speed == null) return husk.getUniqueId();
        speed.setBaseValue(speed.getBaseValue() * SPEED);
        husk.setCustomNameVisible(true);
        husk.setCustomName(NAME);
        husk.setPersistent(true);
        husk.setAdult();
        return husk.getUniqueId();
    }

    private @NotNull UUID buildHiruko(@NotNull World world, Location loc) {
        final double SPEED = 1.4;
        final int HEALTH = 100;
        final String NAME = ChatColor.RED + "Hiruko";
        Entity entity = world.spawnEntity(loc, EntityType.IRON_GOLEM);
        world.spawnParticle(Particle.SQUID_INK, loc, 10, 2, 4, 2);
        IronGolem golem = (IronGolem) entity;
        AttributeInstance health = golem.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health == null) return golem.getUniqueId();
        AttributeInstance speed = golem.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speed == null) return golem.getUniqueId();
        speed.setBaseValue(speed.getBaseValue() * SPEED);
        health.setBaseValue(HEALTH);
        golem.setHealth(HEALTH);
        golem.setCustomNameVisible(true);
        golem.setCustomName(NAME);
        golem.setPersistent(true);
        return golem.getUniqueId();
    }

    private @NotNull UUID buildSandaime(@NotNull World world, Location loc) {
        final int HEALTH = 60;
        final double SPEED = 2;
        final String NAME = ChatColor.RED + "Sandaime Kazekage";
        Entity entity = world.spawnEntity(loc, EntityType.HUSK);
        world.spawnParticle(Particle.SQUID_INK, loc, 10, 1, 2, 1);
        Husk husk = (Husk) entity;
        husk.setAdult();
        AttributeInstance health = husk.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (health == null) return husk.getUniqueId();
        health.setBaseValue(HEALTH);
        AttributeInstance speed = husk.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (speed == null) return husk.getUniqueId();
        speed.setBaseValue(speed.getBaseValue() * SPEED);
        husk.setHealth(HEALTH);
        husk.setCustomNameVisible(true);
        husk.setCustomName(NAME);
        husk.setAdult();
        EntityEquipment equipment = husk.getEquipment();
        if (equipment == null) return husk.getUniqueId();
        equipment.setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));
        return husk.getUniqueId();
    }

    @Override
    public void tick(Player player) {
        clearDeadEntities(player);
        setPuppetTargets(player);
        hirukoPoison(player);
        sandaimeLaunchIron(player);
        clearSandaimeIrons(player);
        tickSandaimeIrons(player);
    }

    private void hirukoPoison(Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        for (UUID uuid : manager.getHiruko()) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            for (int i = 0; i < HIRUKO_PARTICLES_PER_TICK; i++) {
                Location loc = BlockUtils.randomPointInSphere(entity.getLocation(), HIRUKO_POISON_RADIUS);
                entity.getWorld().spawnParticle(Particle.REDSTONE, loc, 1,
                        new Particle.DustOptions(Color.GREEN, 1));
            }
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.getUniqueId().equals(player.getUniqueId())) continue;
                if (entity.getLocation().distance(player1.getLocation()) > HIRUKO_POISON_RADIUS) continue;
                player1.addPotionEffect(new PotionEffect(
                        PotionEffectType.POISON, 10, 0, true, true, true
                ));
            }
        }
    }

    private void sandaimeLaunchIron(Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        for (UUID uuid : manager.getSandaime()) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            if (!lastSandaimeLaunch.containsKey(uuid)) {
                lastSandaimeLaunch.put(uuid, System.currentTimeMillis());
                sandaimeIron.add(
                        new SandaimeIron(entity.getLocation(), player.getUniqueId(), System.currentTimeMillis())
                );
                continue;
            }
            if (System.currentTimeMillis() < lastSandaimeLaunch.get(uuid) + SANDAIME_POWER_TIME) continue;
            lastSandaimeLaunch.put(uuid, System.currentTimeMillis());
            sandaimeIron.add(
                    new SandaimeIron(entity.getLocation(), player.getUniqueId(), System.currentTimeMillis())
            );
        }
    }

    private void clearSandaimeIrons(Player player) {
        sandaimeIron.removeIf((s) -> s.isEnded() && s.playerOwner.equals(player.getUniqueId()));
    }

    private void tickSandaimeIrons(Player player) {
        for (SandaimeIron iron : sandaimeIron) {
            if (!iron.playerOwner.equals(player.getUniqueId())) continue;
            World world = iron.loc().getWorld();
            if (world == null) continue;
            for (int i = 0; i < SANDAIME_PARTICLES_PER_TICK; i++) {
                Location loc = BlockUtils.randomPointInSphere(iron.loc(), SANDAIME_RADIUS);
                world.spawnParticle(Particle.ASH, loc, 1);
            }
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                if (player1.getUniqueId().equals(player.getUniqueId())) continue;
                if (player1.getLocation().distance(iron.loc()) > SANDAIME_RADIUS) continue;
                PowerUtils.damageThroughArmor(player1, SANDAIME_IRON_DAMAGE_SEC, player);
            }
        }
    }

    private void clearDeadEntities(Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        manager.getPuppets().removeIf((u) -> Bukkit.getEntity(u) == null);
        manager.getHiruko().removeIf((u) -> Bukkit.getEntity(u) == null);
        manager.getSandaime().removeIf((u) -> Bukkit.getEntity(u) == null);
        ArrayList<UUID> toRemove = new ArrayList<>();
        for (UUID uuid : lastSandaimeLaunch.keySet()) {
            if (Bukkit.getEntity(uuid) == null) toRemove.add(uuid);
        }
        for (UUID uuid : toRemove) lastSandaimeLaunch.remove(uuid);
    }

    private void setPuppetTargets(@NotNull Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        for (UUID uuid : manager.getAllPuppets()) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            if (!(entity instanceof Mob mob)) continue;
            targetEntity(player, mob);
        }
    }

    private void targetEntity(Player owner, Mob mob) {
        Double minDistance = null;
        Player player = null;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(owner.getUniqueId())) continue;
            if (minDistance == null) {
                minDistance = mob.getLocation().distance(player1.getLocation());
                player = player1;
                continue;
            }
            if (mob.getLocation().distance(player1.getLocation()) < minDistance) minDistance
                    = mob.getLocation().distance(player1.getLocation());
        }
        mob.setTarget(player);
    }

    @Override
    public void onPlayerDeath(Player player) {
        if (!managers.containsKey(player.getUniqueId())) return;
        PuppetManager manager = managers.get(player.getUniqueId());
        for (UUID uuid : manager.getAllPuppets()) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            entity.remove();
        }
        managers.remove(player.getUniqueId());
        super.onPlayerDeath(player);
    }

    private static class PuppetManager {
        private final ArrayList<UUID> puppets;
        private final ArrayList<UUID> hiruko;
        private final ArrayList<UUID> sandaime;
        public PuppetManager() {
            puppets = new ArrayList<>();
            hiruko = new ArrayList<>();
            sandaime = new ArrayList<>();
        }

        public ArrayList<UUID> getPuppets() {return puppets;}
        public ArrayList<UUID> getHiruko() {return hiruko;}
        public ArrayList<UUID> getSandaime() {return sandaime;}
        public ArrayList<UUID> getAllPuppets() {
            ArrayList<UUID> puppets = new ArrayList<>(getPuppets());
            puppets.addAll(getHiruko());
            puppets.addAll(getSandaime());
            return puppets;
        }
    }

    private record SandaimeIron(Location loc, UUID playerOwner, long spawnTime) {
        public boolean isEnded() {return System.currentTimeMillis() > spawnTime + SANDAIME_IRON_DURATION;}
    }
}
