package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ShinobuPower extends Power {
    protected ShinobuPower() {
        super(
                30 * 1000,
                Role.SHINOBU_KOCHO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Souffle de l'insecte",
                        "insect_breath",
                        List.of(
                                "Removes 2 hearts of max health to the",
                                "targeted player",
                                "(30 seconds cooldown)"
                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        return false;
    }
}
