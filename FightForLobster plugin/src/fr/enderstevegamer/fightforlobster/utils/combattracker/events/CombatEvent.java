package fr.enderstevegamer.fightforlobster.utils.combattracker.events;

import javax.annotation.Nullable;
import java.util.UUID;

public class CombatEvent {
    private final CombatEventType eventType;
    private final long eventTime;
    private final UUID eventPlayer;

    public CombatEvent(CombatEventType eventType, @Nullable UUID eventPlayer) {
        this.eventType = eventType;
        this.eventTime = System.currentTimeMillis();
        this.eventPlayer = eventPlayer;
    }

    public CombatEvent(CombatEventType eventType) {
        this(eventType, null);
    }

    public CombatEventType getEventType() {return this.eventType;}
    public @Nullable UUID getEventPlayer() {return this.eventPlayer;}

    public long getTime() {return this.eventTime;}
}
