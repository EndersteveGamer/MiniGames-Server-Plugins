package fr.enderstevegamer.fightforlobster;

import fr.enderstevegamer.fightforlobster.listeners.OnPlayerDamage;
import fr.enderstevegamer.fightforlobster.runnables.TickRoles;
import org.bukkit.Bukkit;
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

        Bukkit.getLogger().info("The plugin " + this.getName() + " was enabled sucessfully!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("The plugin " + this.getName() + " was disabled sucessfully!");
    }

    public static Main getInstance() {return INSTANCE;}
}
