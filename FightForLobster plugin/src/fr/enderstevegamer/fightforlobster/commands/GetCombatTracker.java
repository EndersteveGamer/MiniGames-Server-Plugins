package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.utils.combattracker.CombatTrackerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GetCombatTracker implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;
        CombatTrackerUtils.openTracker(player);
        return true;
    }
}
