package fr.enderstevegamer.spleef.utils;

public class SpleefMode {
    public static final String NORMAL = "normal";
    public static final String MORE_LAYERS = "more_layers";
    public static final String LESS_LAYERS = "less_layers";
    public static final String BIG_SNOWBALLS = "big_snowballs";
    public static final String SHOVEL_ONLY = "shovel_only";
    public static final String SNOWBALL_ONLY = "snowball_only";
    public static final String BIG_SNOWBALLS_MORE_LAYERS = "big_snowballs_more_layers";
    public static final String SUDDEN_DEATH = "sudden_death";
    public static final String ARMOR_PIERCING = "armor_piercing";
    public static final String ARMOR_PIERCING_BIG = "armor_piercing_big";
    public static final String HIDDEN = "hidden";
    public static final String SNOW_RAIN = "snow_rain";
    public static final String SNOW_STORM = "snow_storm";

    public static final String[] MODES = {
            NORMAL,
            MORE_LAYERS,
            LESS_LAYERS,
            BIG_SNOWBALLS,
            SHOVEL_ONLY,
            SNOWBALL_ONLY,
            BIG_SNOWBALLS_MORE_LAYERS,
            SUDDEN_DEATH,
            ARMOR_PIERCING,
            ARMOR_PIERCING_BIG,
            HIDDEN,
            SNOW_RAIN,
            SNOW_STORM
    };

    public static String[] getDescriptions(String mode) {
        return switch (mode) {
            case NORMAL -> new String[]{
                    "Normal",
                    "A normal spleef game"
            };
            case MORE_LAYERS -> new String[]{
                    "More layers",
                    "Five layers of snow"
            };
            case LESS_LAYERS -> new String[]{
                    "Less layers",
                    "Only one layer!"
            };
            case BIG_SNOWBALLS -> new String[]{
                    "Big snowballs",
                    "Snowballs dig 3x3 holes"
            };
            case SHOVEL_ONLY -> new String[]{
                    "Shovel only",
                    "You only have a shovel"
            };
            case SNOWBALL_ONLY -> new String[]{
                    "Snowball only",
                    "You only have snowballs"
            };
            case BIG_SNOWBALLS_MORE_LAYERS -> new String[]{
                    "Everything big",
                    "Snowballs dig 3x3 holes, 5 layers of snow"
            };
            case SUDDEN_DEATH -> new String[]{
                    "Sudden death",
                    "Snowballs kill players, five layers"
            };
            case ARMOR_PIERCING -> new String[]{
                    "Armor piercing",
                    "Snowballs are armor piercing"
            };
            case ARMOR_PIERCING_BIG -> new String[]{
                    "Big holes",
                    "Snowballs are armor piercing, 3x3 holes"
            };
            case HIDDEN -> new String[]{
                    "Hidden",
                    "Idk ¯\\_(ツ)_/¯"
            };
            case SNOW_RAIN -> new String[]{
                    "Snow rain",
                    "Snowballs rain from the sky!"
            };
            case SNOW_STORM -> new String[]{
                    "Snow storm",
                    "It rains heavily, survive!"
            };
            default -> new String[]{"Error", "Undefined mode"};
        };
    }
}
