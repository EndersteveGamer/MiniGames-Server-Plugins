package fr.enderstevegamer.fightforlobster.utils.combattracker.events;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CombatDamage {
    private final Entity entityCause;
    private final CombatDamageCause cause;
    private final int damage;
    private final long damageTime;

    public CombatDamage(@NotNull CombatDamageCause cause, int damage, @Nullable Entity entityCause) {
        this.cause = cause;
        this.damage = damage;
        this.entityCause = entityCause;
        this.damageTime = System.currentTimeMillis();
    }

    public CombatDamage(@NotNull CombatDamageCause cause, int damage) {
        this(cause, damage, null);
    }

    public @Nullable Entity getEntityCause() {return this.entityCause;}
    public @NotNull CombatDamageCause getDamageCause() {return this.cause;}
    public int getDamageCount() {return this.damage;}

    public long getTime() {return this.damageTime;}
}
