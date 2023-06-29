package fr.enderstevegamer.fightforlobster.commands;

import fr.enderstevegamer.fightforlobster.roles.RoleSelector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SelectRole implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;
        player.openInventory(RoleSelector.getSelectorInventory(player));
        return true;
    }
}
