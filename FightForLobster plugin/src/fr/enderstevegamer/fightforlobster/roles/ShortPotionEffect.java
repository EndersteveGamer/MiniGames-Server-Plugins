package fr.enderstevegamer.fightforlobster.roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShortPotionEffect {
    private final PotionEffectType effectType;
    private final int level;

    public ShortPotionEffect(PotionEffectType effectType, int level) {
        this.effectType = effectType;
        this.level = level;
    }

    public ShortPotionEffect(PotionEffectType effectType) {
        this(effectType, 1);
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(
                this.effectType, 10, this.level - 1, false, false
        );
    }
}