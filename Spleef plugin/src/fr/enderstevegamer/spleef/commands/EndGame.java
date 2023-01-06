package fr.enderstevegamer.spleef.commands;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndGame implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.isOp()) {
            if (Main.getStartedForDebug()) {
                SpleefUtils.announceWinner();
            }
            else {
                player.sendMessage(ChatColor.RED + "The game was not started for debug!");
            }
        }
        else {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
        }
        return true;
    }
}
