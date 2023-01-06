package fr.enderstevegamer.spleef.commands;

import fr.enderstevegamer.spleef.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ready implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (!Main.gameStarted() && !Main.getAnnouncingResults()) {
                if (Main.getPlayersReady().contains(player.getUniqueId())) {
                    Main.getPlayersReady().remove(player.getUniqueId());
                    player.sendMessage(ChatColor.RED + "You are no longer ready!");
                } else {
                    Main.getPlayersReady().add(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "You are now ready!");
                    if (Main.getPlayersSpectating().contains(player.getUniqueId())) {
                        Main.getPlayersSpectating().remove(player.getUniqueId());
                        player.sendMessage(ChatColor.RED + "You are no longer spectating the game!");
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "The game has already started!");
            }
            return true;
        }
        return false;
    }
}
