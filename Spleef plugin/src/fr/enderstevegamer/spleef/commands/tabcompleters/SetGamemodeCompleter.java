package fr.enderstevegamer.spleef.commands.tabcompleters;

import fr.enderstevegamer.spleef.utils.SpleefMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class SetGamemodeCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player
                && player.isOp()
                && strings.length == 1) {
            return List.of(SpleefMode.MODES);
        }
        return null;
    }
}
