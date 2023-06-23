package fr.enderstevegamer.fightforlobster.roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    GIYU_TOMIOKA(
            "Giyu Tomioka",
            List.of(
                    new ShortPotionEffect(
                            PotionEffectType.WATER_BREATHING,
                            1
                    )
            ),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8)
            ),
            1.2f
    ),
    SHINOBU_KOCHO(
            "Shinobu Kocho",
            List.of(),
            List.of(),
            1.4f
    ),
    KYOJURO_RENGOKU(
            "Kyojuro Rengoku",
            List.of(
                    new ShortPotionEffect(PotionEffectType.FIRE_RESISTANCE)
            ),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8)
            )
    ),
    TENGEN_UZUI(
            "Tengen Uzui",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.15)
            ),
            1.3f
    ),
    MUICHIRO_TOKITO(
            "Muichiro Tokito",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            ),
            1.2f
    ),
    MITSURI_KANROJI(
            "Mitsuri Kanroji",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4)
            )
    ),
    GYOMEI_HIMEJIMA(
            "Gyomei Himejima",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            )
    ),
    SANEMI_SHINAZUGAWA(
            "Sanemi Shinazugawa",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            ),
            1.35f
    ),
    OBANAI_IGURO(
            "Obanai Iguro",
            List.of(),
            List.of(),
            1.25f
    ),
    RUI(
            "Rui",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8)
            )
    ),
    ENMU(
            "Enmu",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            )
    ),
    DAKI(
            "Daki",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.9),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.1)
            ),
            1.1f
    ),
    GYUTARO(
            "Gyutaro",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8)
            )
    ),
    GYOKKO(
            "Gyokko",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            )
    ),
    GAARA(
            "Gaara",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            )
    ),
    YUGITO(
            "Yugito",
            List.of(
                    new ShortPotionEffect(
                            PotionEffectType.FIRE_RESISTANCE,
                            1
                    )
            ),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f
    ),
    YAGURA(
            "YaguraPower",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.6),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            )
    ),
    ROSHI(
            "Roshi",
            List.of(
                    new ShortPotionEffect(
                            PotionEffectType.FIRE_RESISTANCE,
                            1
                    )
            ),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.5),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            )
    ),
    HAN(
            "Han",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.5f
    ),
    UTAKATA(
            "Utakata",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f
    ),
    FU(
            "Fu",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f
    ),
    KILLER_BEE(
            "Killer Bee",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.6),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.3f
    ),
    NARUTO(
            "Naruto",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.5),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.2f
    ),
    NORMAL_PLAYER("A normal player");

    private final String DISPLAY_NAME;
    private final List<ShortPotionEffect> ROLE_EFFECTS;
    private final List<RoleModifier> ROLE_MODIFIERS;
    private final float WALK_SPEED;

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers, float speedMultiplier) {
        this.DISPLAY_NAME = displayName;
        this.ROLE_EFFECTS = roleEffects;
        this.ROLE_MODIFIERS = roleModifiers;
        this.WALK_SPEED = 0.2f * speedMultiplier;
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers) {
        this(displayName, roleEffects, roleModifiers, 1);
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects) {
        this(displayName, roleEffects, List.of());
    }

    Role(String displayName) {
        this(displayName, List.of());
    }


    @Override
    public String toString() {
        return this.DISPLAY_NAME;
    }

    public List<PotionEffect> getPotionEffects() {
        List<PotionEffect> effects = new ArrayList<>();
        for (ShortPotionEffect shortPotionEffect : ROLE_EFFECTS) {
            effects.add(shortPotionEffect.getPotionEffect());
        }
        return effects;
    }

    public List<RoleModifier> getRoleModifiers() {return this.ROLE_MODIFIERS;}

    public float getRoleWalkSpeed() {return this.WALK_SPEED;}
}
