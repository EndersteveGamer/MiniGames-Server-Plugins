package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KakuzuPower extends DurationPower {
    private static final double ACTIVATION_CHANCE = 0.1;
    private static final double POS_VERTICAL_MULT = 1;
    private static final double POS_HORIZ_MULT = 1.5;
    private static final double POS_VERTICAL_OFFSET = -1.5;
    private static final double DISPLAY_NAME_VERTICAL_OFFSET = 2;
    private static final double DISTANCE_MULT = 1.5;
    private static final double BEHIND_PLAYER_MULTIPLIER = 1;
    private static final double MASK_ACTIVATION_CHANCE = 0.1;
    private static final double MASK_WITHER_TIME = 1000;
    private static final double KB_STRENGTH = 2;
    private static final int FIRE_TICKS = 10*20;
    private static final double CRIT_STRENGTH = 1.2;
    private static final List<String> MASKS_NAMES = List.of(
            ChatColor.GOLD + "Raiton",
            ChatColor.WHITE + "Futon",
            ChatColor.BLUE + "Suiton",
            ChatColor.RED + "Katon"
    );
    private final HashMap<UUID, MaskState> maskStates = new HashMap<>();
    private final HashMap<UUID, MaskState> displayMaskStates = new HashMap<>();
    private final HashMap<UUID, Long> withered = new HashMap<>();

    public KakuzuPower() {
        super(
                60000,
                60000,
                Role.KAKUZU,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Mask Invocation",
                        "mask_invocation",
                        List.of(
                                "Invokes masks behind you",
                                "Each mask has a " + (int)(ACTIVATION_CHANCE*100) + "% chance",
                                "of activating",
                                "Raiton: Strikes the enemy with lighting",
                                "Futon: Knockbacks the enemy",
                                "Suiton: 20% Strength on this hit",
                                "Katon: Sets the enemy on fire",
                                "The masks only stay for 1 minute",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        if (!maskStates.containsKey(player.getUniqueId())) maskStates.put(player.getUniqueId(), new MaskState());
        if (!displayMaskStates.containsKey(player.getUniqueId())) {
            displayMaskStates.put(player.getUniqueId(), new MaskState());
        }
        MaskState state = maskStates.get(player.getUniqueId());
        MaskState displayState = displayMaskStates.get(player.getUniqueId());
        if (state.isFull()) return false;
        for (int i = 0; i < state.getMasks().length; i++) {
            if (state.getMask(i) != null) continue;
            state.setMask(i, buildArmorStand(player, i, false));
            displayState.setMask(i, buildArmorStand(player, i, true));
        }
        return true;
    }

    @Override
    protected void tickActivated(Player player) {
        unwitherMasks();
        MaskState state = maskStates.get(player.getUniqueId());
        MaskState displayState = displayMaskStates.get(player.getUniqueId());
        if (state == null || displayState == null) return;
        for (int i = 0; i < state.getMasks().length; i++) {
            UUID uuid = state.getMasks()[i];
            if (uuid == null) continue;
            Entity stand = Bukkit.getEntity(uuid);
            if (stand == null) continue;
            stand.teleport(getArmorStandLocation(player, i, false));
        }
        for (int i = 0; i < displayState.getMasks().length; i++) {
            UUID uuid = displayState.getMasks()[i];
            if (uuid == null) continue;
            Entity stand = Bukkit.getEntity(uuid);
            if (stand == null) continue;
            stand.teleport(getArmorStandLocation(player, i, true));
        }
    }

    private void unwitherMasks() {
        ArrayList<UUID> toRemove = new ArrayList<>();
        for (UUID uuid : withered.keySet()) {
            if (System.currentTimeMillis() <= withered.get(uuid) + MASK_WITHER_TIME) continue;
            toRemove.add(uuid);
            Entity entity = Bukkit.getEntity(uuid);
            if (entity == null) continue;
            ArmorStand stand = (ArmorStand) entity;
            EntityEquipment equipment = stand.getEquipment();
            if (equipment == null) continue;
            equipment.setHelmet(new ItemStack(Material.SKELETON_SKULL));
        }
        for (UUID uuid : toRemove) withered.remove(uuid);
    }

    @Override
    protected void onEnd(Player player) {
        MaskState state = maskStates.get(player.getUniqueId());
        MaskState displayState = displayMaskStates.get(player.getUniqueId());
        if (state == null || displayState == null) {
            maskStates.remove(player.getUniqueId());
            displayMaskStates.remove(player.getUniqueId());
            return;
        }
        for (int i = 0; i < state.getMasks().length; i++) {
            if (state.getMask(i) == null) continue;
            Entity entity = Bukkit.getEntity(state.getMasks()[i]);
            state.setMask(i, null);
            if (entity == null) continue;
            entity.remove();
        }
        for (int i = 0; i < displayState.getMasks().length; i++) {
            if (displayState.getMask(i) == null) continue;
            Entity entity = Bukkit.getEntity(displayState.getMasks()[i]);
            displayState.setMask(i, null);
            if (entity == null) continue;
            entity.remove();
            World world = entity.getWorld();
            world.spawnParticle(Particle.SQUID_INK, entity.getLocation(), 10, 0, 0, 0, 0.25);
        }
    }

    private UUID buildArmorStand(Player player, int id, boolean isNameStand) {
        Entity entity = player.getWorld().spawnEntity(getArmorStandLocation(player, id, isNameStand),
                EntityType.ARMOR_STAND);
        ArmorStand stand = (ArmorStand) entity;
        stand.setInvisible(true);
        stand.setMarker(true);
        stand.setInvulnerable(true);
        stand.setCanPickupItems(false);
        stand.setCollidable(false);
        if (isNameStand) {
            stand.setCustomNameVisible(true);
            stand.setCustomName(MASKS_NAMES.get(id));
            World world = player.getWorld();
            world.spawnParticle(Particle.SQUID_INK, stand.getLocation(), 10, 0, 0, 0, -0.25);
        }
        else {
            EntityEquipment equipment = stand.getEquipment();
            if (equipment != null) {
                equipment.setHelmet(new ItemStack(Material.SKELETON_SKULL));
            }
        }
        return stand.getUniqueId();
    }

    private Location getArmorStandLocation(Player player, int id, boolean isNameStand) {
        Vector vect = player.getLocation().getDirection();
        vect.multiply(-DISTANCE_MULT);
        Location loc = player.getEyeLocation().clone().add(vect);
        vect.multiply(1 / DISTANCE_MULT);
        vect.setY(0);
        vect.normalize();
        vect.rotateAroundY(id <= 1 ? 90 : -90);
        vect.multiply(POS_HORIZ_MULT);
        loc.add(vect);
        vect.multiply(1/POS_VERTICAL_MULT);
        loc.add(0, (id % 2 == 0 ? 1 : -1) * POS_VERTICAL_MULT, 0);
        loc.add(0, POS_VERTICAL_OFFSET + (isNameStand ? DISPLAY_NAME_VERTICAL_OFFSET : 0), 0);
        loc.add(player.getLocation().getDirection().clone().multiply(new Vector(1, 0, 1))
                .normalize().multiply(-1).multiply(BEHIND_PLAYER_MULTIPLIER));
        loc.setDirection(getArmorStandDirection(player, loc));
        return loc;
    }

    private Vector getArmorStandDirection(Player player, Location armorStandLocation) {
        Location targetedLocation = PowerUtils.getTargetedLocation(player, 10);
        if (targetedLocation == null) return player.getLocation().getDirection();
        return PowerUtils.vectorFromLocations(armorStandLocation, targetedLocation.clone().add(0, 1, 0));
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        if (!Roles.getPlayerRole(damager).equals(getRole())) return;
        if (!(event.getEntity() instanceof Player damaged)) return;
        MaskState state = maskStates.get(damager.getUniqueId());
        if (state == null) return;
        for (int i = 0; i < state.getMasks().length; i++) {
            if (state.getMask(i) == null) continue;
            if (Math.random() > MASK_ACTIVATION_CHANCE) continue;
            maskTriggered(damaged, damager, state.getMask(i), i, event);
        }
    }

    private void maskTriggered(Player damaged, Player damager, UUID mask, int maskId, EntityDamageByEntityEvent event) {
        Entity entity = Bukkit.getEntity(mask);
        if (entity == null) return;
        ArmorStand stand = (ArmorStand) entity;
        EntityEquipment equipment = stand.getEquipment();
        if (equipment == null) return;
        equipment.setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));
        withered.put(mask, System.currentTimeMillis());
        Location origin = stand.getLocation().clone().add(new Vector(0, DISPLAY_NAME_VERTICAL_OFFSET, 0));
        switch (maskId) {
            case 0 -> triggerRaiton(damaged, damager, origin);
            case 1 -> triggerFuton(damaged, damager, origin);
            case 2 -> triggerSuiton(damaged, damager, origin, event);
            case 3 -> triggerKaton(damaged, damager, origin);
        }
    }

    private void spawnTriggerParticles(Player damaged, Location origin, Particle particle) {
        BlockUtils.forEachLineLocBetweenPos(origin, damaged.getLocation(), 0.25, (l) -> {
            World world = l.getWorld();
            if (world == null) return;
            world.spawnParticle(particle, l, 1, 0, 0, 0, 0);
        });

    }

    private void triggerRaiton(Player damaged, Player damager, Location origin) {
        damaged.getWorld().strikeLightning(damaged.getLocation());
        spawnTriggerParticles(damaged, origin, Particle.ELECTRIC_SPARK);
        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.GOLD + "Raiton activated!"
        ));
    }

    private void triggerFuton(Player damaged, Player damager, Location origin) {
        damaged.setVelocity(
                PowerUtils.vectorFromLocations(damager.getLocation(), damaged.getLocation()).multiply(KB_STRENGTH)
        );
        spawnTriggerParticles(damaged, origin, Particle.CRIT);
        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.WHITE + "Futon activated!"
        ));
    }

    private void triggerSuiton(Player damaged, Player damager, Location origin, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * CRIT_STRENGTH);
        spawnTriggerParticles(damaged, origin, Particle.CRIT_MAGIC);
        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.BLUE + "Suiton activated!"
        ));
    }

    private void triggerKaton(Player damaged, Player damager, Location origin) {
        damaged.setFireTicks(FIRE_TICKS);
        spawnTriggerParticles(damaged, origin, Particle.FLAME);
        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                ChatColor.RED + "Katon activated!"
        ));
    }

    private static class MaskState {
        private final UUID[] masks;

        public MaskState() {
            masks = new UUID[]{null, null, null, null};
        }

        private void verifyId(int maskId) {
            if (maskId < 0 || maskId > 3) {
                throw new IllegalArgumentException("maskId must be between 0 and 3 (entered " + maskId + ")");
            }
        }

        public UUID getMask(int maskId) {
            verifyId(maskId);
            return masks[maskId];
        }

        public void setMask(int maskId, UUID mask) {
            verifyId(maskId);
            masks[maskId] = mask;
        }

        public UUID[] getMasks() {return masks;}

        public boolean isFull() {for (UUID uuid : masks) if (uuid == null) return false; return true;}
    }
}
