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

public class ObanaiPower extends Power {
    public ObanaiPower() {
        super(
                30000,
                Role.OBANAI_IGURO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Snake",
                        "snake_breath",
                        List.of(
                                "Inflicts Poison III for 10 seconds to players",
                                "up to 20 blocks away",
                                "(30 sec cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        boolean activated = false;
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.getLocation().distance(player.getLocation()) > 20) continue;
            if (player1.getUniqueId().equals(player.getUniqueId())) continue;
            player1.addPotionEffect(new PotionEffect(
                    PotionEffectType.POISON, 10*20, 2, false, false
            ));
            player1.sendMessage(ChatColor.RED + "You were poisoned by " + player.getName());
            activated = true;
        }
        return activated;
    }
}
