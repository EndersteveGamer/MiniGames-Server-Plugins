package fr.enderstevegamer.fightforlobster.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.function.Consumer;

public class BlockUtils {
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
}
