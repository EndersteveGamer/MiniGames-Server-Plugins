package fr.enderstevegamer.fightforlobster.runnables;

import fr.enderstevegamer.fightforlobster.utils.PluginProfiler;
import org.bukkit.scheduler.BukkitRunnable;

public class TickPluginProfiler extends BukkitRunnable {
    @Override
    public void run() {
        PluginProfiler.tickProfiler();
    }
}
