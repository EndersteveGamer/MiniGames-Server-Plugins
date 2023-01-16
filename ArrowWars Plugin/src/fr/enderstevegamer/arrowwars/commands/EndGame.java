package fr.enderstevegamer.arrowwars.commands;

import fr.enderstevegamer.arrowwars.Main;
import fr.enderstevegamer.arrowwars.utils.ArrowWarsUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class EndGame implements CommandExecutor {
    @Override
    public boolean onCommand(@Nullable CommandSender commandSender, @Nullable Command command, @Nullable String s, @Nullable String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!player.isOp()) {player.sendMessage(ChatColor.RED + "You don't have permission to do this!"); return true;}
        if (!Main.gameStarted || Main.announcingResults) {player.sendMessage(ChatColor.RED + "The game is not started!"); return true;}
        ArrowWarsUtils.endGame();
        player.sendMessage(ChatColor.RED + "Game ended!");
        return true;
    }
}
