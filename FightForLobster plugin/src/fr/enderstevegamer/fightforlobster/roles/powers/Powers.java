package fr.enderstevegamer.fightforlobster.roles.powers;

import fr.enderstevegamer.fightforlobster.roles.Role;
import fr.enderstevegamer.fightforlobster.roles.powers.rolepowers.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Powers {
    private static final List<Power> powers = List.of(
            new ShinobuPower(),
            new TengenPower(),
            new SanemiPower(),
            new ObanaiPower(),
            new GiyuPower(),
            new KyojuroPower(),
            new MuichiroPower(),
            new MitsuriPower(),
            new RuiPower(),
            new DakiPower(),
            new GyutaroPower(),
            new GyokkoPower(),
            new EnmuPower(),
            new GaaraPower(),
            new YugitoPower(),
            new YaguraPower(),
            new RoshiPower(),
            new HanPower(),
            new FuPower(),
            new KillerBeePower(),
            new NarutoPower(),
            new UtakataPower(),
            new ZohakutenPower(),
            new AkazaPower(),
            new DomaPower(),
            new KokushiboPower(),
            new HidanPower(),
            new KakuzuPower(),
            new SasoriPower()
    );

    public static void forEach(Consumer<Power> consumer) {
        for (Power power : powers) {
            consumer.accept(power);
        }
    }

    public static <T extends Power> void forEachPowerType(Class<T> powerClass, Consumer<T> consumer) {
        for (T power : getPowerType(powerClass)) {
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

    public static <T extends Power> List<T> getPowerType(Class<T> powerClass) {
        ArrayList<T> result = new ArrayList<>();
        for (Power power : powers) {
            if (power.getClass().equals(powerClass)) result.add(powerClass.cast(power));
        }
        return result;
    }

    public static void onPlayerDeath(Player player) {
        for (Power power : powers) {
            power.onPlayerDeath(player);
        }
    }
}
