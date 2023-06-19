package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Power {
    private static final HashMap<UUID, Long> lastActivation = new HashMap<>();
    private final double POWER_COOLDOWN;
    private final Role ROLE;
    private final PowerItem POWER_ITEM;

    protected Power(double cooldown, Role role, PowerItem powerItem) {
        this.POWER_COOLDOWN = cooldown;
        this.ROLE = role;
        this.POWER_ITEM = powerItem;
    }

    public Role getRole() {return this.ROLE;}

    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(POWER_ITEM.getMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(ChatColor.GOLD + POWER_ITEM.getDisplayName());
        meta.setLocalizedName(POWER_ITEM.getId());
        if (POWER_ITEM.getLore() != null) meta.setLore(POWER_ITEM.getLore());
        item.setItemMeta(meta);
        if (player == null) return item;
        if (getCooldownLeft(player) > 0) addCooldownIndication(item, player);
        return item;
    }

    public void addCooldownIndication(ItemStack item, Player player) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        if (meta.getDisplayName().startsWith(ChatColor.RED.toString())) {
            meta.setDisplayName(meta.getDisplayName().replaceFirst(ChatColor.RED.toString(), ""));
        }
        meta.setDisplayName(ChatColor.RED + meta.getDisplayName() +
                "(" + getCooldownLeft(player) + "s)");
        item.setItemMeta(meta);
    }

    public int getCooldownLeft(Player player) {
        if (!lastActivation.containsKey(player.getUniqueId())) return -1;
        long cooldownLeft = lastActivation.get(player.getUniqueId()) - System.currentTimeMillis();
        if (cooldownLeft < 0) return -1;
        return (int)(cooldownLeft / 1000);
    }

    public boolean isCooldownFinished(Player player) {
        return getCooldownLeft(player) == -1;
    }

    public abstract boolean onActivation(Player player);

    public void tryActivating(PlayerInteractEvent event) {
        if (!isPowerItem(event.getItem())) return;
        Player player = event.getPlayer();
        if (!Roles.getPlayerRole(player).equals(this.ROLE)) return;
        if (!lastActivation.containsKey(player.getUniqueId())) lastActivation.put(player.getUniqueId(), 0L);
        if (!isCooldownFinished(player)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.RED + "Can be used in " + getCooldownLeft(player) + " seconds"
            ));
            return;
        }
        activate(player);
    }

    private void activate(Player player) {
        if (onActivation(player)) lastActivation.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public boolean isPowerItem(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getLocalizedName().equals(POWER_ITEM.getId());
    }

    static class PowerItem {
        private final Material material;
        private final String displayName;
        private final String id;
        private final List<String> lore;

        public PowerItem(Material material, String displayName, String id, List<String> lore) {
            this.material = material;
            this.displayName = displayName;
            this.id = id;
            if (lore == null) this.lore = null;
            else {
                List<String> formattedLore = new ArrayList<>();
                for (String string : lore) {
                    formattedLore.add(ChatColor.GRAY + string);
                }
                this.lore = formattedLore;
            }
        }

        public PowerItem(Material material, String displayName, String id) {
            this(material, displayName, id, null);
        }

        public Material getMaterial() {
            return material;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getId() {
            return id;
        }

        public List<String> getLore() {
            return lore;
        }
    }
}
