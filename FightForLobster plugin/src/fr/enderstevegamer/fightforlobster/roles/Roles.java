package fr.enderstevegamer.fightforlobster.roles;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class Roles {
    private static final HashMap<UUID, Role> playerRoles = new HashMap<>();
    private static final List<Consumer<Player>> playerFunctions = List.of(
            Roles::enchantItems,
            Roles::tickObanai
    );

    public static Role getPlayerRole(UUID uuid) {
        if (!playerRoles.containsKey(uuid)) return Role.NORMAL_PLAYER;
        return playerRoles.get(uuid);
    }

    public static Role getPlayerRole(Player player) {
        return getPlayerRole(player.getUniqueId());
    }

    public static void setPlayerRole(UUID uuid, Role role) {
        playerRoles.put(uuid, role);
    }

    public static void setPlayerRole(Player player, Role role) {
        setPlayerRole(player.getUniqueId(), role);
    }

    public static void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        for (RoleModifier modifier : Roles.getPlayerRole(damager).getRoleModifiers()) {
            if (!modifier.modifierType().equals(RoleModifierType.STRENGTH)) continue;
            event.setDamage(event.getDamage() * modifier.modifierStrength());
        }
        if (!(event.getEntity() instanceof Player damaged)) return;
        for (RoleModifier modifier : Roles.getPlayerRole(damaged).getRoleModifiers()) {
            if (!modifier.modifierType().equals(RoleModifierType.RESISTANCE)) continue;
            event.setDamage(event.getDamage() * modifier.modifierStrength());
        }
    }

    public static void tickRoles() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Consumer<Player> playerFunction : playerFunctions) {
                playerFunction.accept(player);
            }
            Role role = getPlayerRole(player);
            player.setWalkSpeed(role.getRoleWalkSpeed());
            AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute == null) continue;
            attribute.setBaseValue(20);
            for (RoleModifier modifier : role.getRoleModifiers()) {
                if (!modifier.modifierType().equals(RoleModifierType.HEALTH)) continue;
                attribute.setBaseValue(attribute.getBaseValue() + modifier.modifierStrength());
            }
            player.addPotionEffects(role.getPotionEffects());
        }
    }

    private static void enchantItems(Player player) {
        PlayerInventory inventory = player.getInventory();
        switch (Roles.getPlayerRole(player)) {
            case GIYU_TOMIOKA -> {
                ItemStack item = inventory.getBoots();
                if (item == null) break;
                item.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 3);
            }
            case KYOJURO_RENGOKU -> {
                for (ItemStack item : inventory) {
                    if (item == null) continue;
                    if (!isSword(item)) continue;
                    item.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                }
            }
        }
    }

    private static boolean isSword(ItemStack item) {
        final List<Material> swordMaterials = List.of(
                Material.WOODEN_SWORD,
                Material.STONE_SWORD,
                Material.GOLDEN_SWORD,
                Material.IRON_SWORD,
                Material.DIAMOND_SWORD,
                Material.NETHERITE_SWORD
        );
        return swordMaterials.contains(item.getType());
    }

    private static void tickObanai(Player player) {
        if (!getPlayerRole(player).equals(Role.OBANAI_IGURO)) return;
        PlayerInventory inventory = player.getInventory();
        for (ItemStack item : inventory.getArmorContents()) {
            if (item != null) return;
        }
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 10, 0, false, false
        ));
    }
}
