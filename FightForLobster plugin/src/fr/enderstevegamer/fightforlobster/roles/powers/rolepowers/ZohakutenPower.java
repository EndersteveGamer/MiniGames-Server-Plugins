package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class ZohakutenPower extends Power {
    private static final double RADIUS = 15;
    private static final int DAMAGE = 4;
    private static final Material MATERIAL = Material.OAK_LOG;
    public ZohakutenPower() {
        super(
                60000,
                Role.ZOHAKUTEN,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Hate Avatar",
                        "hate_avatar",
                        List.of(
                                "Traps players in a " + (int) RADIUS + " blocks",
                                "radius, spawning a 3x4x3 wood cage,",
                                "striking them with lightning,",
                                "and dealing " + DAMAGE/2 + " hearts of damage",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        boolean activated = false;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            if (player1.getLocation().distance(player.getLocation()) > RADIUS) continue;
            activated = true;
            trapPlayer(player1, player);
        }
        return activated;
    }

    private void trapPlayer(Player player, Player damager) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    Block block = player.getLocation().getBlock().getRelative(x, y, z);
                    if (block.isPassable()) block.setType(MATERIAL);
                }
            }
        }
        player.getWorld().strikeLightning(player.getLocation());
        PowerUtils.damageThroughArmor(player, DAMAGE, damager);
    }
}
