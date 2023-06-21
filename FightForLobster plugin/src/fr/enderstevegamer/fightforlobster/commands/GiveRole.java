package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveRole implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                             String[] strings) {
        return CommonCommands.onPlayerRoleSelect(commandSender, command, s, strings, GiveRole::giveRole);
    }

    private static String giveRole(Player player, Role role) {
        Roles.setPlayerRole(player, role);
        return ChatColor.GREEN + "The role " + role + " was given to " + player.getName();
    }
}
