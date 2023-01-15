package fr.enderstevegamer.global.utils;

import fr.enderstevegamer.global.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.UUID;

public class Database {
    private static Connection connection;

    public Database() throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:plugins/database.db");
        }
        catch (SQLException e) {
            throw new RuntimeException("Could not connect to database!\n" + e.getMessage());
        }
    }

    public static void saveValue(String table, String key, String value) throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + table + " (key TEXT CONSTRAINT const PRIMARY KEY, value TEXT)");
        connection.createStatement().execute("INSERT OR REPLACE INTO " + table + " (key, value) VALUES ('" + key + "', '" + value + "')");
    }

    public static String loadValue(String table, String key) throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + table + " (key TEXT CONSTRAINT const PRIMARY KEY, value TEXT)");
        return connection.createStatement().executeQuery("SELECT value FROM " + table + " WHERE key = '" + key + "'").getString("value");
    }

    public static void saveParkourBestTimes() {
        try {
            for (UUID uuid : Main.getLobby().getParkourBestTimes().keySet()) {
                saveValue("parkour_best_times", uuid.toString(), Main.getLobby().getParkourBestTimes().get(uuid).toString());
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Could not save parkour best times!\n" + e.getMessage());
        }
    }

    public static void loadParkourBestTimes() {
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS parkour_best_times (key TEXT CONSTRAINT const PRIMARY KEY, value TEXT)");
            var set = connection.createStatement().executeQuery("SELECT * FROM parkour_best_times");
            while (set.next()) {
                Main.getLobby().getParkourBestTimes().put(UUID.fromString(set.getString("key")), Duration.parse(set.getString("value")));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Could not load parkour best times!\n" + e.getMessage());
        }
    }

    public static void savePlayersUsernames() {
        try {
            for (UUID uuid : Main.getPlayerUsernames().keySet()) {
                saveValue("players_usernames", uuid.toString(), Main.getPlayerUsernames().get(uuid));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Could not save players usernames!\n" + e.getMessage());
        }
    }

    public static void loadPlayersUsernames() {
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS players_usernames (key TEXT CONSTRAINT const PRIMARY KEY, value TEXT)");
            var set = connection.createStatement().executeQuery("SELECT * FROM players_usernames");
            while (set.next()) {
                Main.getPlayerUsernames().put(UUID.fromString(set.getString("key")), set.getString("value"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Could not load players usernames!\n" + e.getMessage());
        }
    }

    public void saveData() {
        saveParkourBestTimes();
        savePlayersUsernames();
    }

    public void loadData() {
        loadParkourBestTimes();
        loadPlayersUsernames();
    }
}
