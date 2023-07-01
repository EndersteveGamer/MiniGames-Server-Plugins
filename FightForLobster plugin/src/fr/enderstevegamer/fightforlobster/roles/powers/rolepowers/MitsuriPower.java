package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MitsuriPower extends DurationPower {
    private final HashMap<UUID, UUID> links = new HashMap<>();
    public MitsuriPower() {
        super(
                60000,
                10000,
                Role.MITSURI,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Breath of Love",
                        "love_breath",
                        List.of(
                                "Makes the player targeted unable to hit you",
                                "for 10 seconds",
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
        Player linked = Bukkit.getPlayer(links.get(player.getUniqueId()));
        if (linked != null) {
            linked.sendMessage(ChatColor.GREEN + "You love with " + player.getName() + " ended!");
        }
        links.remove(player.getUniqueId());
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
        links.put(player.getUniqueId(), targeted.getUniqueId());
        targeted.sendMessage(ChatColor.LIGHT_PURPLE + "You fell in love with " + player.getName() + "!");
        return true;
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (event.getDamager() instanceof Arrow arrow) onDamageByArrow(damaged, arrow, event);
        if (event.getDamager() instanceof Player damager) onDamageByPlayer(damaged, damager, event);
    }

    private void onDamageByPlayer(Player damaged, Player damager, EntityDamageByEntityEvent event) {
        if (!links.containsKey(damaged.getUniqueId())) return;
        if (links.get(damaged.getUniqueId()).equals(damager.getUniqueId())) event.setCancelled(true);
    }

    private void onDamageByArrow(Player damaged, Arrow arrow, EntityDamageByEntityEvent event) {
        if (!(arrow.getShooter() instanceof Player damager)) return;
        onDamageByPlayer(damaged, damager, event);
    }
}
