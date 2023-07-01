package fr.enderstevegamer.fightforlobster.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class BlockUtils {
    private static final HashMap<Block, Long> liquidsFrozen = new HashMap<>();
    public static ArrayList<Block> getSphereBlocks(Location center, double radius) {
        ArrayList<Block> result = new ArrayList<>();
        for (int x = (int) (-radius - 1); x < radius + 1; x++) {
            for (int y = (int) (-radius - 1); y < radius + 1; y++) {
                for (int z = (int) (-radius - 1); z < radius + 1; z++) {
                    Block block = center.getBlock().getRelative(x, y, z);
                    if (center.distance(block.getLocation()) > radius) continue;
                    result.add(block);
                }
            }
        }
        return result;
    }

    public static void forEachSphereBlock(Location center, double radius, Consumer<Block> consumer) {
        ArrayList<Block> blocks = getSphereBlocks(center, radius);
        for (Block block : blocks) {
            consumer.accept(block);
        }
    }

    public static boolean isInSphere(Location center, double radius, Block block) {
        return block.getLocation().distance(center) <= radius;
    }

    public static Location randomPointInSphere(Location center, double radius) {
        double xMult;
        double yMult;
        double zMult;
        Location result;
        do {
            xMult = (Math.random() - 0.5) * 2;
            yMult = (Math.random() - 0.5) * 2;
            zMult = (Math.random() - 0.5) * 2;
            result = new Location(
                    center.getWorld(),
                    center.getX() + xMult * radius,
                    center.getY() + yMult * radius,
                    center.getZ() + zMult * radius
            );
        } while (result.distance(center) > radius);
        return result;
    }

    public static List<Block> getBlocksInCenteredCube(Location center, Vector size) {
        ArrayList<Block> result = new ArrayList<>();
        for (int x = -(size.getBlockX() / 2); x <= size.getBlockX() - size.getBlockX() / 2; x++) {
            for (int y = -(size.getBlockY() / 2); y <= size.getBlockY() - size.getBlockY() / 2; y++) {
                for (int z = -(size.getBlockZ() / 2); z <= size.getBlockZ() - size.getBlockZ() / 2; z++) {
                    result.add(center.getBlock().getRelative(x, y, z));
                }
            }
        }
        return result;
    }

    public static List<Block> getBlocksInCenteredCube(Location center, int size) {
        return getBlocksInCenteredCube(center, new Vector(size, size, size));
    }

    @SuppressWarnings("unused")
    public static void forEachBlockInCenteredCube(Location center, Vector size, Consumer<Block> consumer) {
        getBlocksInCenteredCube(center, size).forEach(consumer);
    }

    public static void forEachBlockInCenteredCube(Location center, int size, Consumer<Block> consumer) {
        getBlocksInCenteredCube(center, size).forEach(consumer);
    }

    public static List<Location> lineLocsBetweenPos(Location start, Location end, double quality) {
        ArrayList<Location> result = new ArrayList<>();
        Vector vect = new Vector(
                end.getX() - start.getX(),
                end.getY() - start.getY(),
                end.getZ() - start.getZ()
        );
        vect.normalize().multiply(quality);
        for (int i = 0; i < start.distance(end) / quality; i++) {
            result.add(start.clone().add(vect.clone().multiply(i)));
        }
        return result;
    }

    public static void forEachLineLocBetweenPos(Location start, Location end, double quality,
                                                Consumer<Location> consumer) {
        for (Location loc : lineLocsBetweenPos(start, end, quality)) {
            consumer.accept(loc);
        }
    }

    public static void dontUpdateLiquid(@NotNull Block block, Long time) {
        if (!block.isLiquid()) return;
        liquidsFrozen.put(block, (time == null) ? null : System.currentTimeMillis() + time);
    }

    public static void dontUpdateLiquid(Block block) {
        dontUpdateLiquid(block, null);
    }

    public static void tickFrozenLiquids() {
        ArrayList<Block> toRemove = new ArrayList<>();
        for (Block block : liquidsFrozen.keySet()) {
            if (!block.getLocation().getBlock().isLiquid()) toRemove.add(block);
            if (liquidsFrozen.get(block) != null
                    && System.currentTimeMillis() > liquidsFrozen.get(block)) toRemove.add(block);
        }
        for (Block block : toRemove) liquidsFrozen.remove(block);
    }

    public static void onLiquidFlow(BlockFromToEvent event) {
        for (Block block : liquidsFrozen.keySet()) {
            if (block.getLocation().equals(event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
