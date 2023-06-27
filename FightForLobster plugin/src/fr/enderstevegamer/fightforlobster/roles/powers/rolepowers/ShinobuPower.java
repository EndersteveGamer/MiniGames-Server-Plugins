package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.List;

public class ShinobuPower extends Power {
    public ShinobuPower() {
        super(
                60000,
                Role.SHINOBU,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of insect",
                        "insect_breath",
                        List.of(
                                "Removes 1 heart of max health to the",
                                "targeted player",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        Player selectedPlayer = PowerUtils.getTargetedPlayer(player);
        if (selectedPlayer == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.RED + "No player targeted!"
            ));
            return false;
        }
        AttributeInstance attribute = selectedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attribute == null) return false;
        attribute.setBaseValue(attribute.getBaseValue() - 2);
        selectedPlayer.sendMessage(ChatColor.RED + player.getName() + " made you lose a heart with the "
                + getPowerItem().getDisplayName());
        return true;
    }
}
