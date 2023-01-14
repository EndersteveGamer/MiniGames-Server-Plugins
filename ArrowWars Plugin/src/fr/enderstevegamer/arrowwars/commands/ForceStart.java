package fr.enderstevegamer.arrowwars.commands;

import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!player.isOp()) {player.sendMessage(ChatColor.RED + "You don't have permission to do this!"); return true;}
        ArrowWarsUtils.startGame();
        return true;
    }
}
