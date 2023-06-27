package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class GyutaroPower extends Power {
    private static final double RADIUS = 10;
    public GyutaroPower() {
        super(
                60000,
                Role.GYUTARO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Stale Blood",
                        "stale_blood",
                        List.of(
                                "Inflicts Wither II for 10 seconds to players",
                                "that are less than " + (int) RADIUS + " blocks away",
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
            player1.addPotionEffect(new PotionEffect(
                    PotionEffectType.WITHER, 10*20, 1, true, true
            ));
            player1.sendMessage(ChatColor.RED + "You were withered by " + player.getName());
            activated = true;
        }
        return activated;
    }
}
