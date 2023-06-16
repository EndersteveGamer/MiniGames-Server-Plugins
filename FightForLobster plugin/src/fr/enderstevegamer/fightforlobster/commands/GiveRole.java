package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.Roles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveRole implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "You must be an operator to do this!");
            return true;
        }
        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "You must specify a player!");
            return true;
        }
        Player player = null;
        if (!strings[0].equals("@a")) {
            if (strings[0].equals("@s")) {
                if (!(commandSender instanceof Player senderPlayer)) {
                    commandSender.sendMessage(ChatColor.RED + "You must be a player to do this!");
                    return true;
                }
                player = senderPlayer;
            } else {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    if (player1.getName().equals(strings[0])) {
                        player = player1;
                        break;
                    }
                }
            }
            if (player == null) {
                commandSender.sendMessage(ChatColor.RED + "There is no player with that name!");
                return true;
            }
        }
        if (strings.length < 2) {
            commandSender.sendMessage(ChatColor.RED + "You must specify a role!");
            return true;
        }
        Role selectedRole = null;
        for (Role role : Role.values()) {
            if (role.name().equals(strings[1])) {
                selectedRole = role;
                break;
            }
        }
        if (selectedRole == null) {
            commandSender.sendMessage(ChatColor.RED + "There is no role with that name!");
            return true;
        }
        if (strings.length > 2) return false;
        if (!strings[0].equals("@a")) {
            Roles.setPlayerRole(player, selectedRole);
            commandSender.sendMessage(ChatColor.GREEN + "The role " + selectedRole + " was given to " + player.getName());
            return true;
        }
        else {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                Roles.setPlayerRole(player1, selectedRole);
                commandSender.sendMessage(ChatColor.GREEN + "Gave the role " + selectedRole + "to all players");
                return true;
            }
        }
        return false;
    }
}
