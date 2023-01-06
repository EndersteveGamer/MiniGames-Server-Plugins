package fr.enderstevegamer.spleef.commands;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (player.isOp()) {
            if (Main.getPlayersReady().size() >= 1) {
                if (Main.getPlayersReady().size() == 1) {
                    Main.setStartedForDebug(true);
                    player.sendMessage(ChatColor.GREEN + "Game started for debug!");
                }
                else {
                    Main.setStartedForDebug(false);
                    player.sendMessage(ChatColor.GREEN + "Game started!");
                }
                SpleefUtils.startGame();
            }
            else {
                player.sendMessage(ChatColor.RED + "No players to start the game with!");
            }
        }
        else {
            player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
        }
        return true;
    }
}
