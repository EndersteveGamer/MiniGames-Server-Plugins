package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class DeidaraPower extends Power {
    public DeidaraPower() {
        super(
                60000,
                Role.DEIDARA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "September 11th",
                        "september11",
                        List.of(

                        )
                )
        );
    }

    @Override
    public boolean onActivation(Player player) {
        return false;
    }
}
