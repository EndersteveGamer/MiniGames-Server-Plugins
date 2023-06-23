package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class RoshiPower extends Power {
    public RoshiPower() {
        super(
                60000,
                Role.ROSHI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Son Goku",
                        "son_goku",
                        List.of(
                                "Places a 3x3 square of lava",
                                "at the targeted player",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        Player targeted = PowerUtils.getTargetedPlayer(player);
        if (targeted == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.RED + "No player targeted!"
            ));
            return false;
        }
        Location loc = targeted.getLocation();
        if (!loc.getBlock().isPassable()) return false;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Block block = loc.getBlock().getRelative(x, 0, z);
                if (!block.isPassable()) continue;
                block.setType(Material.LAVA);
            }
        }
        return true;
    }
}
