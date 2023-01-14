package fr.enderstevegamer.arrowwars.commands;

import fr.enderstevegamer.arrowwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spectate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (Main.isGameStarted()) {player.sendMessage(ChatColor.RED + "Game already started!"); return true;}

        if (Main.getPlayersSpectating().get(player.getUniqueId())) {
            Main.getPlayersSpectating().put(player.getUniqueId(), false);
            player.sendMessage(ChatColor.RED + "You are no longer spectating!");
        }
        else {
            if (Main.getPlayersReady().get(player.getUniqueId())) {
                Main.getPlayersReady().put(player.getUniqueId(), false);
                player.sendMessage(ChatColor.RED + "You are no longer ready!");
            }
            Main.getPlayersSpectating().put(player.getUniqueId(), true);
            player.sendMessage(ChatColor.GREEN + "You are now spectating!");
        }
        return true;
    }
}
