package fr.enderstevegamer.arrowwars.commands;

import fr.enderstevegamer.arrowwars.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ready implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // Handle not player and game already started
        if (!(commandSender instanceof Player player)) return false;
        if (Main.isGameStarted()) {player.sendMessage(ChatColor.RED + "Game already started!"); return true;}

        // Set or remove ready
        if (Main.getPlayersReady().get(player.getUniqueId())) {
            Main.getPlayersReady().put(player.getUniqueId(), false);
            player.sendMessage(ChatColor.RED + "You are no longer ready!");
        }
        else {
            if (Main.getPlayersSpectating().get(player.getUniqueId())) {
                Main.getPlayersSpectating().put(player.getUniqueId(), false);
                player.sendMessage(ChatColor.RED + "You are no longer spectating!");
            }
            Main.getPlayersReady().put(player.getUniqueId(), true);
            player.sendMessage(ChatColor.GREEN + "You are now ready!");
        }
        return true;
    }
}
