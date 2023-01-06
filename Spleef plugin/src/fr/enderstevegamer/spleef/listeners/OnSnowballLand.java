package fr.enderstevegamer.spleef.listeners;

import fr.enderstevegamer.spleef.Main;
import fr.enderstevegamer.spleef.utils.SpleefMode;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

import java.util.Objects;

public class OnSnowballLand implements Listener {
    @EventHandler
    public static void onSnowballLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(),
                    event.getEntity().getLocation().toVector(),
                    event.getEntity().getVelocity().normalize(),
                    0.0D,
                    4);
            Block hitBlock = null;

            while (iterator.hasNext()) {
                hitBlock = iterator.next();
                if (hitBlock.getType() != Material.AIR) {
                    break;
                }
            }

            assert hitBlock != null;
            // Get current gamemode
            String gamemode = Main.getCurrentGamemode();

            if (hitBlock.getType() == Material.SNOW_BLOCK) {
                // Break 3x3 if gamemode is big snowballs, else only break one
                if (!gamemode.equals(SpleefMode.BIG_SNOWBALLS)
                        && !gamemode.equals(SpleefMode.BIG_SNOWBALLS_MORE_LAYERS)
                        && !gamemode.equals(SpleefMode.ARMOR_PIERCING_BIG)) {
                    hitBlock.getWorld().playEffect(hitBlock.getLocation(), Effect.STEP_SOUND, hitBlock.getType());
                    hitBlock.setType(Material.AIR);
                }
                else {
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            for (int z = -1; z <= 1; z++) {
                                Block block = hitBlock.getRelative(x, y, z);
                                if (block.getType() == Material.SNOW_BLOCK) {
                                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                                    block.setType(Material.AIR);
                                }
                            }
                        }
                    }
                }

                // Make snowball go through blocks
                if (gamemode.equals(SpleefMode.ARMOR_PIERCING) || gamemode.equals(SpleefMode.ARMOR_PIERCING_BIG)) {
                    // Spawn snowball
                    Snowball snowball = Objects.requireNonNull(Bukkit.getWorld("world")).spawn(event.getEntity().getLocation(), Snowball.class);
                    snowball.setVelocity(event.getEntity().getVelocity());
                    snowball.setShooter(event.getEntity().getShooter());

                    // Remove old snowball
                    event.getEntity().remove();
                }
            }
        }
    }
}
