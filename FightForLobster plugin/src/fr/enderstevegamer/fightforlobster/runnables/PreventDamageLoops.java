package fr.enderstevegamer.fightforlobster.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class PreventDamageLoops extends BukkitRunnable {
    public static final ArrayList<UUID> wereHit = new ArrayList<>();
    @Override
    public void run() {
        wereHit.clear();
    }
}
