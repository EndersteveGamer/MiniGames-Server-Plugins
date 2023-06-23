package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class FuPower extends DurationPower {
    public FuPower() {
        super(
                60000,
                20000,
                Role.FU,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Chomei",
                        "chomei",
                        List.of(
                                "Allows flying for 20 seconds",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {

    }

    @Override
    protected void onEnd(Player player) {
        player.setAllowFlight(false);
        player.sendMessage(ChatColor.RED + "You can't fly anymore!");
    }

    @Override
    public boolean onActivation(Player player) {
        player.setAllowFlight(true);
        player.sendMessage(ChatColor.GREEN + "You can now fly!");
        return true;
    }
}
