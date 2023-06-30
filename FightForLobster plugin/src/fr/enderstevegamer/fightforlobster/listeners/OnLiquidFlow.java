package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.GiyuPower;
import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class OnLiquidFlow implements Listener {
    @EventHandler
    public static void onLiquidFlow(BlockFromToEvent event) {
        Powers.forEachPowerType(GiyuPower.class, (p) -> p.onWaterFlow(event));
        BlockUtils.onLiquidFlow(event);
    }
}
