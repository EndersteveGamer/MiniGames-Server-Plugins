package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GyokkoPower extends DurationPower {
    ArrayList<UUID> thorned = new ArrayList<>();
    public GyokkoPower() {
        super(
                60000,
                30000,
                Role.GYOKKO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Sharp Scales",
                        "sharp_scales",
                        List.of(
                                "Gives you a more aggressive",
                                "equivalent of Thorns III for ",
                                "30 seconds",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {}

    @Override
    protected void onEnd(Player player) {
        thorned.remove(player.getUniqueId());
    }

    @Override
    public boolean onActivation(Player player) {
        thorned.add(player.getUniqueId());
        return true;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (event.getDamager() instanceof Projectile projectile) onArrowDamage(damaged, projectile);
        if (event.getDamager() instanceof Player damager) onPlayerDamage(damaged, damager);
    }

    private void onPlayerDamage(Player damaged, Player damager) {
        if (!thorned.contains(damaged.getUniqueId())) return;
        damager.damage((int)(Math.random() * 4) + 3);
    }

    private void onArrowDamage(Player damaged, Projectile projectile) {
        if (projectile.getShooter() instanceof Player damager) onPlayerDamage(damaged, damager);
    }
}
