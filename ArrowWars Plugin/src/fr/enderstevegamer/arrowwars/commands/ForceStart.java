package fr.enderstevegamer.arrowwars.commands;

import fr.enderstevegamer.arrowwars.Main;
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
        if (Main.isGameStarted() || Main.isAnnouncingResults()) {player.sendMessage(ChatColor.RED + "The game is already started!"); return true;}
        int ready = 0;
        for (boolean b : Main.getPlayersReady().values()) {
            if (b) ready++;
        }
        if (ready == 0) {player.sendMessage(ChatColor.RED + "Nobody is ready!"); return true;}
        Main.setStartedForDebug(true);
        ArrowWarsUtils.startGame();
        return true;
    }
}
