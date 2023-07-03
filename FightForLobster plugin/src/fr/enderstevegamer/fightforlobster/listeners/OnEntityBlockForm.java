package fr.enderstevegamer.fightforlobster.listeners;

import fr.enderstevegamer.fightforlobster.roles.powers.Powers;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.DeidaraPower;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class OnEntityBlockForm implements Listener {
    @EventHandler
    public static void onEntityBlockForm(EntityBlockFormEvent event) {
        Powers.forEachPowerType(DeidaraPower.class, (p) -> p.onBlockForm(event));
    }
}
