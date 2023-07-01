package fr.enderstevegamer.fightforlobster.roles;

import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class RoleSelector {
    private static final HashMap<UUID, Boolean> isRandom = new HashMap<>();
    private static final String INVENTORY_NAME = ChatColor.GOLD + "Role Selector";
    public static Inventory getSelectorInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6*9, INVENTORY_NAME);
        for (Role role : Role.values()) {
            ItemStack item = new ItemStack(Material.GRASS_BLOCK);
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;
            meta.setDisplayName(role.toString());
            meta.setLore(buildLore(role));
            meta.addItemFlags(ItemFlag.values());
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
        inventory.setItem(53, getRandomItem(player));
        return inventory;
    }

    private static ItemStack getRandomItem(Player player) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName((isRandomEnabled(player)) ? ChatColor.GREEN + "Random Role: Enabled"
                : ChatColor.RED + "Random Role: Disabled");
        meta.setLocalizedName("random_role_toggle");
        meta.addItemFlags(ItemFlag.values());
        meta.setLore(List.of(
                "Gives you a random role",
                "after each death"
        ));
        item.setItemMeta(meta);
        return item;
    }

    private static boolean isRandomEnabled(Player player) {
        return isRandom.containsKey(player.getUniqueId()) && isRandom.get(player.getUniqueId());
    }

    private static void toggleRandom(Player player) {
        if (!isRandom.containsKey(player.getUniqueId())) isRandom.put(player.getUniqueId(), true);
        else isRandom.put(player.getUniqueId(), !isRandom.get(player.getUniqueId()));
    }

    private static List<String> buildLore(Role role) {
        List<String> lore = new ArrayList<>();
        List<RoleModifier> modifiers = role.getRoleModifiers();
        RoleModifier strength = getRoleModifier(RoleModifierType.STRENGTH, modifiers);
        if (strength != null) {
            lore.add(
                    ((strength.modifierStrength() > 1) ? ChatColor.GREEN + "+" : ChatColor.RED).toString()
                            + (int) Math.round(((strength.modifierStrength() - 1)*100)) + "% Strength"
            );
        }
        RoleModifier resistance = getRoleModifier(RoleModifierType.RESISTANCE, modifiers);
        if (resistance != null) {
            lore.add(
                    ((resistance.modifierStrength() > 1) ? ChatColor.RED : ChatColor.GREEN + "+").toString()
                            + (int) Math.round(((1 - resistance.modifierStrength())*100)) + "% Resistance"
            );
        }
        float speed = role.getRoleWalkSpeed();
        double ratio = speed / 0.2f;
        if (ratio != 1) {
            lore.add(
                    ((ratio > 1) ? ChatColor.GREEN + "+" : ChatColor.RED).toString() +
                            (int) Math.round(((ratio - 1) * 100)) + "% Speed"
            );
        }
        RoleModifier health = getRoleModifier(RoleModifierType.HEALTH, modifiers);
        if (health != null) {
            lore.add(
                    ((health.modifierStrength() > 0) ? ChatColor.GREEN + "+" : ChatColor.RED).toString() +
                            (int) health.modifierStrength()/2 + " Hearts"
            );
        }
        lore.add("");
        List<String> powerLore = null;
        for (Power power : Powers.getRolePowers(role)) {powerLore = power.getPowerItem().lore(); break;}
        if (powerLore == null) {
            lore.add("Power: None");
        }
        else {
            lore.add("Power:");
            lore.addAll(powerLore);
        }
        return lore;
    }

    private static @Nullable RoleModifier getRoleModifier(RoleModifierType type, List<RoleModifier> modifiers) {
        for (RoleModifier modifier : modifiers) {
            if (modifier.modifierType().equals(type)) return modifier;
        }
        return null;
    }

    public static void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(INVENTORY_NAME)) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (meta.getLocalizedName().equals("random_role_toggle")) {
            toggleRandom(player);
            player.openInventory(getSelectorInventory(player));
            return;
        }
        String name = meta.getDisplayName();
        for (Role role : Role.values()) {
            if (role.toString().equals(name)) {
                Roles.setPlayerRole(player, role);
                player.sendMessage(ChatColor.GREEN + "Gave you the role " + role);
                player.closeInventory();
                return;
            }
        }
    }

    public static void onPlayerDeath(PlayerDeathEvent event) {
        Role role = Role.values()[(int) (Math.random() * Role.values().length)];
        if (isRandomEnabled(event.getEntity())) {
            Roles.setPlayerRole(event.getEntity(), role);
            event.getEntity().sendMessage(ChatColor.GREEN + "You received the role " + role.toString() + "!");
        }
    }
}
