package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class TengenPower extends Power {

    protected TengenPower() {
        super(
                60000,
                Role.TENGEN_UZUI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Sound",
                        "sound_breath",
                        List.of(
                                "Teleports on the targeted player",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        Player targetedPlayer = PowerUtils.getTargetedPlayer(player);
        if (targetedPlayer == null) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                    ChatColor.RED + "No player targeted!"
            ));
            return false;
        }
        Location loc = targetedPlayer.getLocation();
        for (int i = -3; i <= -1; i++) {
            Location teleportLoc = loc.clone().add(
                    loc.getDirection().multiply(new Vector(1, 0, 1)).multiply(i)
            );
            if (!PowerUtils.canTeleportHere(teleportLoc)) continue;
            player.teleport(teleportLoc);
            return true;
        }
        player.teleport(targetedPlayer.getLocation());
        return true;
    }
}
