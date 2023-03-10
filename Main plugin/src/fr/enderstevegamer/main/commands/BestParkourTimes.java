package fr.enderstevegamer.main.commands;

import fr.enderstevegamer.main.Main;
import fr.enderstevegamer.main.utils.GlobalCommunicationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BestParkourTimes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player && !Main.getWaitingForParkourBestTimes().contains(player.getUniqueId())) {
            Main.getWaitingForParkourBestTimes().add(player.getUniqueId());
            GlobalCommunicationUtils.sendUpdateParkourBestTime();
        }
        return true;
    }
}
