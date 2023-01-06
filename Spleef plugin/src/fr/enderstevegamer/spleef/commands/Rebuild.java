package fr.enderstevegamer.spleef.commands;

import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Rebuild implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.isOp()) {
            SpleefUtils.buildArena();
        }
        else {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
        }
        return true;
    }
}
