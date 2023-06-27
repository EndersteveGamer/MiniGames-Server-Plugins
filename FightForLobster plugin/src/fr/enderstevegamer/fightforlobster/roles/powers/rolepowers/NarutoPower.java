package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NarutoPower extends DurationPower {
    private final HashMap<UUID, UUID> affectedPlayer = new HashMap<>();
    public NarutoPower() {
        super(
                60000,
                20000,
                Role.NARUTO,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Kurama",
                        "kurama",
                        List.of(
                                "Inflicts 4 hearts of true damage,",
                                "Slowness 20% and Weakness 20%",
                                "for 20 seconds",
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
        Player player1 = Bukkit.getPlayer(affectedPlayer.get(player.getUniqueId()));
        affectedPlayer.remove(player.getUniqueId());
        if (player1 == null) return;
        player1.setWalkSpeed(player1.getWalkSpeed() * (1 / 0.8f));
        player1.sendMessage(ChatColor.GREEN + "The wave of chakra ended!");
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
        affectedPlayer.put(player.getUniqueId(), targeted.getUniqueId());
        targeted.setWalkSpeed(targeted.getWalkSpeed() * 0.8f);
        PowerUtils.damageThroughArmor(targeted, 8, player);
        targeted.sendMessage(ChatColor.RED + player.getName() + " sent you a wave of chakra!");
        BlockUtils.forEachLineLocBetweenPos(
                player.getLocation(),
                targeted.getLocation(),
                0.5,
                (l) -> {
                    player.getWorld().spawnParticle(Particle.SQUID_INK, l, 1);
                }
        );
        return true;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        if (!affectedPlayer.containsValue(damager.getUniqueId())) return;
        event.setDamage(event.getDamage() * 0.8);
    }
}
