package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class RuiPower extends Power {
    public RuiPower() {
        super(
                15000,
                Role.RUI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Bloody Web",
                        "blood_web",
                        List.of(
                                "Spawns a spider web on the feets",
                                "of the targeted player, making",
                                "a heart of damage",
                                "(15 sec cooldown)"
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
        targeted.getLocation().getBlock().setType(Material.COBWEB);
        targeted.damage(2);
        return true;
    }
}
