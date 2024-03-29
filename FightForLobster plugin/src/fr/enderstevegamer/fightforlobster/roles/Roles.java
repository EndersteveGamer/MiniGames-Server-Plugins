package fr.enderstevegamer.fightforlobster.roles;

import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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
            Roles::tickObanai
    );

    public static Role getPlayerRole(UUID uuid) {
        if (!playerRoles.containsKey(uuid)) return Role.NORMAL_PLAYER;
        return playerRoles.get(uuid);
    }

    public static Role getPlayerRole(Player player) {
        return getPlayerRole(player.getUniqueId());
    }

    @SuppressWarnings("unused")
    public static void setPlayerRole(UUID uuid, Role role) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;
        setPlayerRole(player, role);
    }

    public static void setPlayerRole(Player player, Role role) {
        playerRoles.put(player.getUniqueId(), role);
        player.setWalkSpeed(role.getRoleWalkSpeed());
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute == null) return;
        attribute.setBaseValue(20);
        for (RoleModifier modifier : role.getRoleModifiers()) {
            if (!modifier.modifierType().equals(RoleModifierType.HEALTH)) continue;
            attribute.setBaseValue(attribute.getBaseValue() + modifier.modifierStrength());
        }
        ItemStack[] contents = player.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            boolean isPowerItem = false;
            for (Power power : Powers.getPowers()) {
                isPowerItem = isPowerItem || power.isPowerItem(contents[i]);
            }
            if (isPowerItem) contents[i] = null;
        }
        player.getInventory().setContents(contents);
        for (Power power : Powers.getPowers()) {
            if (power.getRole().equals(role)) player.getInventory().addItem(power.getItem(player));
        }
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
            Role role = Roles.getPlayerRole(player);
            player.addPotionEffects(role.getPotionEffects());
        }
    }

    private static void tickObanai(Player player) {
        if (!getPlayerRole(player).equals(Role.OBANAI)) return;
        PlayerInventory inventory = player.getInventory();
        for (ItemStack item : inventory.getArmorContents()) {
            if (item != null) return;
        }
        player.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY, 10, 0, false, false
        ));
    }
}
