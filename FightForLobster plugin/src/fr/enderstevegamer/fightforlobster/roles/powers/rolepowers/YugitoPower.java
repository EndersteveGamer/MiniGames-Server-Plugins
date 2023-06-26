package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.List;

public class YugitoPower extends Power {
    public YugitoPower() {
        super(
                60000,
                Role.YUGITO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Matatabi",
                        "matatabi",
                        List.of(
                                "Creates soul fire in a",
                                "16 blocks sphere around the",
                                "targeted block",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        BlockIterator iterator = new BlockIterator(player, 100);
        boolean isPassable = false;
        while (iterator.hasNext()) {
            Block block = iterator.next();
            if (block.isPassable()) isPassable = true;
            else if (isPassable) {
                BlockUtils.forEachSphereBlock(block.getLocation(), 24, (b) -> {
                    if (!b.isPassable()) return;
                    b.setType(Material.FIRE);
                });
                return true;
            }
        }
        return false;
    }
}
