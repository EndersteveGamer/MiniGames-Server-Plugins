package fr.enderstevegamer.fightforlobster.commands.tabcompleters;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveRoleCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            ArrayList<String> result = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                result.add(player.getName());
            }
            if (commandSender instanceof Player) {
                result.add("@s");
            }
            result.add("@a");
            return result;
        }
        if (strings.length == 2) {
            ArrayList<String> result = new ArrayList<>();
            for (Role role : Role.values()) {
                result.add(role.name().toLowerCase());
            }
            return result;
        }
        return List.of();
    }
}
