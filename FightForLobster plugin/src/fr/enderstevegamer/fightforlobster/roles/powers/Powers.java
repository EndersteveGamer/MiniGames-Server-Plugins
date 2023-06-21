package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Powers {
    private static final List<Power> powers = List.of(
            new ShinobuPower(),
            new TengenPower(),
            new SanemiPower(),
            new ObanaiPower()
    );

    public static void forEach(Consumer<Power> consumer) {
        for (Power power : powers) {
            consumer.accept(power);
        }
    }

    public static ArrayList<Power> getRolePowers(Role role) {
        ArrayList<Power> result = new ArrayList<>();
        for (Power power : powers) {
            if (!power.getRole().equals(role)) continue;
            result.add(power);
        }
        return result;
    }

    public static List<Power> getPowers() {return powers;}
}
