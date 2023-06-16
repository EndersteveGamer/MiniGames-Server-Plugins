package fr.enderstevegamer.fightforlobster;

import fr.enderstevegamer.fightforlobster.commands.GiveRole;
import fr.enderstevegamer.fightforlobster.commands.tabcompleters.GiveRoleCompleter;
import fr.enderstevegamer.fightforlobster.listeners.OnPlayerDamage;
import fr.enderstevegamer.fightforlobster.runnables.TickRoles;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    private static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        // Register listeners
        List<Listener> listeners = List.of(
                new OnPlayerDamage()
        );

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }

        // Start loops
        new TickRoles().runTaskTimer(this, 0, 0);

        // Register command
        registerCommand("giverole", new GiveRole(), new GiveRoleCompleter());

        Bukkit.getLogger().info("The plugin " + this.getName() + " was enabled sucessfully!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("The plugin " + this.getName() + " was disabled sucessfully!");
    }

    public static Main getInstance() {return INSTANCE;}

    private void registerCommand(String command, CommandExecutor executor, TabCompleter completer) {
        PluginCommand pluginCommand = getCommand(command);
        if (pluginCommand == null) return;
        pluginCommand.setExecutor(executor);
        if (completer == null) return;
        pluginCommand.setTabCompleter(completer);
    }

    private void registerCommand(String command, CommandExecutor executor) {
        registerCommand(command, executor, null);
    }
}
