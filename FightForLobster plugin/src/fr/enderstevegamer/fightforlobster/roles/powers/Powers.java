package fr.enderstevegamer.fightforlobster.roles.powers;

import java.util.List;
import java.util.function.Consumer;

public class Powers {
    private static final List<Power> powers = List.of(
            new ShinobuPower()
    );

    public static void forEach(Consumer<Power> consumer) {
        for (Power power : powers) {
            consumer.accept(power);
        }
    }

    public static List<Power> getPowers() {return powers;}
}
