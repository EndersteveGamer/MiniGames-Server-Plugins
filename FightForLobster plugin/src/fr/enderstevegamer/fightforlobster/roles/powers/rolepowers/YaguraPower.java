package fr.enderstevegamer.fightforlobster.roles.powers.rolepowers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.DurationPower;
import fr.enderstevegamer.fightforlobster.utils.PowerUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class YaguraPower extends DurationPower {
    private final HashMap<UUID, PlayerPos> immobilized = new HashMap<>();
    public YaguraPower() {
        super(
                60000,
                5000,
                Role.YAGURA,
                new PowerItem(
                        Material.NETHER_STAR,
                        "Isobu",
                        "isobu",
                        List.of(
                                "Maked the targeted player unable",
                                "to move for 5 seconds",
                                "(1 min cooldown)"
                        )
                )
        );
    }

    @Override
    protected void tickActivated(Player player) {
        for (PlayerPos playerPos : immobilized.values()) {
            Player player1 = Bukkit.getPlayer(playerPos.getUniqueId());
            if (player1 == null) continue;
            player1.teleport(playerPos.getLoc());
        }
    }

    @Override
    protected void onEnd(Player player) {
        PlayerPos playerPos = immobilized.get(player.getUniqueId());
        immobilized.remove(player.getUniqueId());
        if (playerPos == null) return;
        Player freed = Bukkit.getPlayer(playerPos.getUniqueId());
        if (freed == null) return;
        freed.sendMessage(ChatColor.GREEN + "You are not stunned anymore!");
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
        immobilized.put(player.getUniqueId(), new PlayerPos(targeted));
        targeted.sendMessage(ChatColor.RED + "You were stunned by " + player.getName());
        return true;
    }

    private record PlayerPos(UUID uuid, Location loc) {
        public PlayerPos(Player player) {
            this(player.getUniqueId(), player.getLocation());
        }
        public UUID getUniqueId() {return uuid;}
        public Location getLoc() {return loc;}
    }
}
