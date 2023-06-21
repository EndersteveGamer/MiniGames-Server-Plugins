package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveRoleItem implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                             String[] strings) {
        return CommonCommands.onPlayerRoleSelect(commandSender, command, s, strings, GiveRoleItem::giveRoleItem);
    }

    public static String giveRoleItem(Player player, Role role) {
        boolean gaveItem = false;
        for (Power power : Powers.getPowers()) {
            if (!power.getRole().equals(role)) continue;
            player.getInventory().addItem(power.getItem(player));
            gaveItem = true;
            player.sendMessage(ChatColor.GREEN + "Gave you the " + power.getPowerItem().getDisplayName() + " item");
        }
        if (!gaveItem) {
            return ChatColor.RED + "There is no item for this role!";
        }
        return "";
    }
}
