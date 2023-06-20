package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class ShinobuPower extends Power {
    protected ShinobuPower() {
        super(
                60000,
                Role.SHINOBU_KOCHO,
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
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (player1.equals(player)) continue;
            if (isLookingAt(player, player1)) {
                AttributeInstance attribute = player1.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (attribute == null) continue;
                attribute.setBaseValue(attribute.getBaseValue() - 2);
                player1.sendMessage(ChatColor.RED + player.getName() + " made you lose a heart with the "
                        + getPowerItem().getDisplayName());
                return true;
            }
        }
        return false;
    }

    private boolean isLookingAt(Player player, Player player1) {
        Location eye = player.getEyeLocation();
        Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.99D;
    }
}
