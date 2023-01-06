package fr.enderstevegamer.spleef.commands;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetGamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player && player.isOp()) {
            if (!Main.gameStarted() && !Main.getAnnouncingResults()) {
                if (Arrays.stream(SpleefMode.MODES).toList().contains(strings[0])) {
                    Main.setGamemode(strings[0]);
                    player.sendMessage(ChatColor.GREEN + "Gamemode set to " + ChatColor.GOLD + strings[0]);
                    Main.setForcedGamemode(true);
                } else {
                    player.sendMessage(ChatColor.RED + "This gamemode does not exist!");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "The game already started!");
            }
        }
        return true;
    }
}
