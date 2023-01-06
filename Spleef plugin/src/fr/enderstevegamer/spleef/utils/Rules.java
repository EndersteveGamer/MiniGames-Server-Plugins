package fr.enderstevegamer.spleef.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Rules {
    static ArrayList<String> en = new ArrayList<>();
    static ArrayList<String> fr = new ArrayList<>();
    public static void defineRules() {
        // English
        en.add(ChatColor.GOLD + "________________________________________________");
        en.add(ChatColor.GOLD + "Rules:");
        en.add(ChatColor.GOLD + "The goal of the game is to make your opponents fall at the bottom of the map.");
        en.add(ChatColor.GOLD + "You will have a shovel to break blocks.");
        en.add(ChatColor.GOLD + "You will also have snowballs that will break blocks on impact.");
        en.add(ChatColor.GOLD + "Good luck!");
        en.add(ChatColor.GOLD + "________________________________________________");

        // Français
        fr.add(ChatColor.GOLD + "________________________________________________");
        fr.add(ChatColor.GOLD + "Règles:");
        fr.add(ChatColor.GOLD + "Le but du jeu est de faire tomber vos adversaires en bas de la zone de jeu.");
        fr.add(ChatColor.GOLD + "Vous aurez une pelle qui vous permettra de casser des blocs");
        fr.add(ChatColor.GOLD + "Vous aurez également des boules de neige qui casseront les blocs à l'impact.");
        fr.add(ChatColor.GOLD + "Bonne chance!");
        fr.add(ChatColor.GOLD + "________________________________________________");
    }

    public static ArrayList<String> getRules(String language) {
        defineRules();
        return switch (language) {
            case "fr" -> fr;
            default -> en;
        };
    }
}
