package fr.enderstevegamer.fightforlobster.utils;

import org.bukkit.Bukkit;

import java.util.ArrayList;

public class PluginProfiler {
    private static final Long DISPLAY_COOLDOWN = 1000L;
    private static Long lastDisplay = null;
    private static final ArrayList<Long> times = new ArrayList<>();
    private static Long startTime = null;

    public static void tickProfiler() {
        if (times.size() == 0) return;
        if (lastDisplay == null || System.currentTimeMillis() >= lastDisplay + DISPLAY_COOLDOWN) {
            displayProfiling();
            lastDisplay = System.currentTimeMillis();
        }
    }

    public static void resetProfiler() {startTime = null;}

    public static void startProfiling() {
        if (startTime != null) throw new IllegalStateException("Profiler was started while already being started!");
        startTime = System.currentTimeMillis();
    }

    public static void stopProfiling() {
        if (startTime == null) throw new IllegalStateException("Profiler was stopped while not being started!");
        times.add(System.currentTimeMillis() - startTime);
        resetProfiler();
    }



    private static void displayProfiling() {
        long average = 0L;
        for (Long time : times) average += time / times.size();
        Bukkit.getLogger().info("[PROFILER] Average time: " + average + "ms");
        times.clear();
    }
}
