package fr.enderstevegamer.fightforlobster;

import fr.enderstevegamer.fightforlobster.commands.GiveRole;
import fr.enderstevegamer.fightforlobster.commands.GiveRoleItem;
import fr.enderstevegamer.fightforlobster.commands.tabcompleters.GiveRoleCompleter;
import fr.enderstevegamer.fightforlobster.listeners.*;
import fr.enderstevegamer.fightforlobster.runnables.TickPowers;
import fr.enderstevegamer.fightforlobster.runnables.TickRoles;
import fr.enderstevegamer.fightforlobster.runnables.UpdateItems;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Main extends JavaPlugin {
    private static Main INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        // Register listeners
        List<Listener> listeners = List.of(
                new OnPlayerDamage(),
                new OnInteract(),
                new OnLiquidFlow(),
                new OnEntityDamage(),
                new OnPlayerMove()
        );

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }

        // Start loops
        List<BukkitRunnable> runnables = List.of(
                new TickRoles(),
                new UpdateItems(),
                new TickPowers()
        );

        for (BukkitRunnable runnable : runnables) {
            runnable.runTaskTimer(this, 0, 0);
        }

        // Register command
        registerCommand("giverole", new GiveRole(), new GiveRoleCompleter());
        registerCommand("giveroleitem", new GiveRoleItem(), new GiveRoleCompleter());

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
