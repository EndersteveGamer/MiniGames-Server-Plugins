package fr.enderstevegamer.fightforlobster.runnables;

import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class TickFrozenLiquids extends BukkitRunnable {
    @Override
    public void run() {
        BlockUtils.tickFrozenLiquids();
    }
}
