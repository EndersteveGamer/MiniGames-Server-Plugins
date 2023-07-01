package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.BiFunction;

public class CommonCommands {
    public static boolean onPlayerRoleSelect(CommandSender commandSender, Command ignoredCommand, String ignoredS, String[] strings,
                                             BiFunction<Player, Role, String> function) {
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
            if (role.name().toLowerCase().equals(strings[1])) {
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
            String result = function.apply(player, selectedRole);
            if (!result.equals("")) commandSender.sendMessage(result);
            return true;
        }
        else {
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                String result = function.apply(player1, selectedRole);
                if (!result.equals("")) commandSender.sendMessage(result);
                return true;
            }
        }
        return false;
    }
}
