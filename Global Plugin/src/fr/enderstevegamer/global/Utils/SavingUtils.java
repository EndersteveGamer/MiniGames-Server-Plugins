package fr.enderstevegamer.global.Utils;

import fr.enderstevegamer.global.Main;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class SavingUtils {
    public static class BestTimesHashMap {
        public static void save() {
            File file = new File("LobbyBestTimes.txt");
            try {
                FileUtils.writeStringToFile(file, DecodingUtils.DurationHashMap.toString.hashMap(Main.getLobby().getParkourBestTimes()), "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void load() {
            File file = new File("LobbyBestTimes.txt");

            if (file.exists()) {
                try {
                    String string = FileUtils.readFileToString(file, "UTF-8");
                    if (string.equals("")) return;
                    Main.getLobby().setParkourBestTimes(DecodingUtils.DurationHashMap.fromString.hashMap(string));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    boolean bool = file.createNewFile();
                    if (!bool) {
                        throw new RuntimeException("Could not create file!");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
