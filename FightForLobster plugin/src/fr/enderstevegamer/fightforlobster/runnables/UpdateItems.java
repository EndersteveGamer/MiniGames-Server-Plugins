package fr.enderstevegamer.fightforlobster.runnables;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class UpdateItems extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Role role = Roles.getPlayerRole(player);
            ArrayList<Power> powers = Powers.getRolePowers(role);
            for (Power power : powers) {
                ItemStack[] items = player.getInventory().getContents();
                for (int i = 0; i < items.length; i++) {
                    if (!power.isPowerItem(items[i])) continue;
                    items[i] = power.getItem(player);
                }
                player.getInventory().setContents(items);
            }
        }
    }
}
