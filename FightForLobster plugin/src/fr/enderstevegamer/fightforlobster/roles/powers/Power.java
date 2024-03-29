package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class Power {
    protected final HashMap<UUID, Long> lastActivation = new HashMap<>();
    protected final double POWER_COOLDOWN;
    private final Role ROLE;
    private final PowerItem POWER_ITEM;

    public Power(double cooldown, Role role, PowerItem powerItem) {
        this.POWER_COOLDOWN = cooldown;
        this.ROLE = role;
        this.POWER_ITEM = powerItem;
    }

    public final Role getRole() {return this.ROLE;}

    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(POWER_ITEM.material());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(ChatColor.GOLD + POWER_ITEM.displayName());
        meta.setLocalizedName(POWER_ITEM.id());
        if (POWER_ITEM.lore() != null) meta.setLore(POWER_ITEM.lore());
        item.setItemMeta(meta);
        if (player == null) return item;
        if (getCooldownLeft(player) != -1) addCooldownIndication(item, player);
        return item;
    }

    void addCooldownIndication(ItemStack item, Player player) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(ChatColor.RED + getPowerItem().displayName() +
                " (" + getCooldownLeft(player) + "s)");
        item.setItemMeta(meta);
    }

    protected int getCooldownLeft(Player player) {
        if (!lastActivation.containsKey(player.getUniqueId())) return -1;
        long cooldownLeft = (long) (lastActivation.get(player.getUniqueId()) + POWER_COOLDOWN
                - System.currentTimeMillis());
        if (cooldownLeft < 0) return -1;
        return (int)(cooldownLeft / 1000);
    }

    public boolean isCooldownFinished(Player player) {
        return getCooldownLeft(player) == -1;
    }

    public abstract boolean onActivation(Player player);

    public final void tryActivating(PlayerInteractEvent event) {
        if (!isPowerItem(event.getItem())) return;
        Player player = event.getPlayer();
        if (this.ROLE != null && !Roles.getPlayerRole(player).equals(this.ROLE)) return;
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

    public final boolean isPowerItem(ItemStack item) {
        if (item == null) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getLocalizedName().equals(POWER_ITEM.id());
    }

    public final PowerItem getPowerItem() {
        return POWER_ITEM;
    }

    public void tick(Player player) {}

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {}

    private void resetCooldown(Player player) {lastActivation.remove(player.getUniqueId());}

    public void onPlayerDeath(Player player) {resetCooldown(player);}

    public record PowerItem(Material material, String displayName, String id, List<String> lore) {
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
        }
}
