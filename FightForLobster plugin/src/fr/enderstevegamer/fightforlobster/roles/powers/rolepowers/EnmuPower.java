package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.roles.powers.Power;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EnmuPower extends DurationPower {
    private final HashMap<UUID, UUID> sleeping = new HashMap<>();

    public EnmuPower() {
        super(
                60000,
                10000,
                Role.ENMU,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Nightmare",
                        "nightmare",
                        List.of(
                                "Makes the targeted player sleep,",
                                "making him unable to hit, be damaged",
                                "or move, and gives him Blindness",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
        for (UUID uuid : sleeping.values()) {
            Player player1 = Bukkit.getPlayer(uuid);
            if (player1 == null) continue;
            player1.setGliding(true);
        }
    }

    @Override
    protected void onEnd(Player player) {
        Player sleepingPlayer = Bukkit.getPlayer(sleeping.get(player.getUniqueId()));
        if (sleepingPlayer != null) {
            sleepingPlayer.sendMessage(ChatColor.GREEN + "Hey, you, you're finally awake?");
        }
        sleeping.remove(player.getUniqueId());
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
        sleeping.put(player.getUniqueId(), targeted.getUniqueId());
        targeted.sendMessage(ChatColor.RED + "You were put to sleep by " + player.getName() + "!");
        targeted.addPotionEffect(new PotionEffect(
                PotionEffectType.BLINDNESS, 10*20, 2, false, false
        ));
        Vector playerDirection = targeted.getLocation().getDirection();
        playerDirection.multiply(new Vector(1, 0, 1));
        Location playerLocation = targeted.getLocation();
        playerLocation.setDirection(playerDirection);
        targeted.teleport(playerLocation);
        return true;
    }

    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player damaged)) return;
        if (sleeping.containsValue(damaged.getUniqueId())) event.setCancelled(true);
    }

    public boolean onPlayerInteract(PlayerInteractEvent event) {
        return sleeping.containsValue(event.getPlayer().getUniqueId());
    }

    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player damager)) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (!sleeping.containsValue(damager.getUniqueId())) return;
        event.setCancelled(true);
    }

    public void onPlayerMove(PlayerMoveEvent event) {
        if (!sleeping.containsValue(event.getPlayer().getUniqueId())) return;
        event.setCancelled(true);
    }
}
