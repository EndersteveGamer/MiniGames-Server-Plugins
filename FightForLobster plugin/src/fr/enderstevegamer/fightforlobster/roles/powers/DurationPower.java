package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class DurationPower extends Power {
    private final double POWER_DURATION;
    private boolean wasActive = false;
    protected DurationPower(double cooldown, double duration, Role role, PowerItem powerItem) {
        super(cooldown, role, powerItem);
        this.POWER_DURATION = duration;
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack item = new ItemStack(getPowerItem().getMaterial());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(ChatColor.GOLD + getPowerItem().getDisplayName());
        meta.setLocalizedName(getPowerItem().getId());
        if (getPowerItem().getLore() != null) meta.setLore(getPowerItem().getLore());
        item.setItemMeta(meta);
        if (player == null) return item;
        if (isPowerActive(player)) addTimeLeftIndication(item, player);
        else if (getCooldownLeft(player) > 0) addCooldownIndication(item, player);
        return item;
    }

    private void addTimeLeftIndication(ItemStack item, Player player) {
        if (item == null) return;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.setDisplayName(ChatColor.GREEN + getPowerItem().getDisplayName() +
                " (" + getPowerLeft(player) + "s left)");
        item.setItemMeta(meta);
    }

    private boolean isPowerActive(Player player) {
        if (!lastActivation.containsKey(player.getUniqueId())) return false;
        return System.currentTimeMillis() < lastActivation.get(player.getUniqueId()) + POWER_DURATION;
    }

    private int getPowerLeft(Player player) {
        if (!lastActivation.containsKey(player.getUniqueId())) return -1;
        long powerLeft = (long) (lastActivation.get(player.getUniqueId()) + POWER_DURATION - System.currentTimeMillis());
        if (powerLeft < 0) return -1;
        return (int) (powerLeft / 1000);
    }

    @Override
    protected int getCooldownLeft(Player player) {
        if (!lastActivation.containsKey(player.getUniqueId())) return -1;
        long cooldownLeft = (long) (lastActivation.get(player.getUniqueId()) + POWER_COOLDOWN + POWER_DURATION
                - System.currentTimeMillis());
        if (cooldownLeft < 0) return -1;
        return (int)(cooldownLeft / 1000);
    }

    @Override
    public boolean isCooldownFinished(Player player) {
        if (isPowerActive(player)) return false;
        return super.isCooldownFinished(player);
    }

    protected abstract void tickActivated(Player player);

    protected abstract void onEnd(Player player);

    @Override
    public final void tick(Player player) {
        if (isPowerActive(player)) {
            wasActive = true;
            tickActivated(player);
        }
        else if (wasActive) {
            onEnd(player);
            wasActive = false;
        }
        tickAlways(player);
    }

    protected void tickAlways(Player player) {}
}