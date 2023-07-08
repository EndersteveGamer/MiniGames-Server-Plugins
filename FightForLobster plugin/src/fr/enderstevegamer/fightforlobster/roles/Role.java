package fr.enderstevegamer.fightforlobster.roles;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public enum Role {
    GIYU(
            "Giyu Tomioka",
            List.of(
                    new ShortPotionEffect(
                            PotionEffectType.WATER_BREATHING,
                            1
                    ),
                    new ShortPotionEffect(
                            PotionEffectType.DOLPHINS_GRACE,
                            1
                    )
            ),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7)
            ),
            1.4f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    SHINOBU(
            "Shinobu Kocho",
            List.of(),
            List.of(),
            1.7f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    KYOJURO(
            "Kyojuro Rengoku",
            List.of(
                    new ShortPotionEffect(PotionEffectType.FIRE_RESISTANCE)
            ),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    TENGEN(
            "Tengen Uzui",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3)
            ),
            1.4f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    MUICHIRO(
            "Muichiro Tokito",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3)
            ),
            1.3f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    MITSURI(
            "Mitsuri Kanroji",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.5),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    GYOMEI(
            "Gyomei Himejima",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.5),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    SANEMI(
            "Sanemi Shinazugawa",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3)
            ),
            1.5f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    OBANAI(
            "Obanai Iguro",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            ),
            1.4f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    RUI(
            "Rui",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    ENMU(
            "Enmu",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8)
            ),
            1.2f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    DAKI(
            "Daki",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4)
            ),
            1.2f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    GYUTARO(
            "Gyutaro",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    GYOKKO(
            "Gyokko",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    ZOHAKUTEN(
            "Zohakuten",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.9)
            ),
            1.2f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    AKAZA(
            "Akaza",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    DOMA(
            "Doma",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.DEMON_SLAYER
    ),
    KOKUSHIBO(
            "Kokushibo",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.8f,
            RoleSelectorPage.DEMON_SLAYER
    ),
    GAARA(
            "Gaara",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.NARUTO
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
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    YAGURA(
            "Yagura",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.6),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.3),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            RoleSelectorPage.NARUTO
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
            ),
            RoleSelectorPage.NARUTO
    ),
    HAN(
            "Han",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.5f,
            RoleSelectorPage.NARUTO
    ),
    UTAKATA(
            "Utakata",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    FU(
            "Fu",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    KILLER_BEE(
            "Killer Bee",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.7),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    NARUTO(
            "Naruto",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.4),
                    new RoleModifier(RoleModifierType.HEALTH, 4)
            ),
            1.3f,
            RoleSelectorPage.NARUTO
    ),
    HIDAN(
            "Hidan",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.HEALTH, 20)
            ),
            0.8f,
            RoleSelectorPage.NARUTO
    ),
    KAKUZU(
            "Kakuzu",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2),
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.HEALTH, 10)
            ),
            RoleSelectorPage.NARUTO
    ),
    SASORI(
            "Sasori",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2),
                    new RoleModifier(RoleModifierType.HEALTH, 2)
            ),
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    DEIDARA(
            "Deidara",
            List.of(),
            List.of(
                    new RoleModifier(RoleModifierType.RESISTANCE, 0.8),
                    new RoleModifier(RoleModifierType.STRENGTH, 1.2)
            ),
            1.4f,
            RoleSelectorPage.NARUTO
    ),
    NORMAL_PLAYER("A normal player");

    private final String DISPLAY_NAME;
    private final List<ShortPotionEffect> ROLE_EFFECTS;
    private final List<RoleModifier> ROLE_MODIFIERS;
    private final float WALK_SPEED;
    private final RoleSelectorPage PAGE;

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers,
         float speedMultiplier, RoleSelectorPage page) {
        this.DISPLAY_NAME = displayName;
        this.ROLE_EFFECTS = roleEffects;
        this.ROLE_MODIFIERS = roleModifiers;
        this.WALK_SPEED = 0.2f * speedMultiplier;
        this.PAGE = page;
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers,
         RoleSelectorPage page) {
        this(displayName, roleEffects, roleModifiers, 1, page);
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers,
         float speedMultiplier) {
        this(displayName, roleEffects, roleModifiers, speedMultiplier, RoleSelectorPage.OTHER);
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects, List<RoleModifier> roleModifiers) {
        this(displayName, roleEffects, roleModifiers, 1);
    }

    Role(String displayName, List<ShortPotionEffect> roleEffects) {
        this(displayName, roleEffects, List.of());
    }

    @SuppressWarnings("SameParameterValue")
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

    public RoleSelectorPage getSelectorPage() {return this.PAGE;}
}
