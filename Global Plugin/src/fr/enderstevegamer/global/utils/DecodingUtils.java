package fr.enderstevegamer.global.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DecodingUtils {
    public static class DurationHashMap {
        public static class toString {
            public static String hashMapKey(HashMap<UUID, Duration> hashMap, UUID key) {
                return key.toString() + ":" + hashMap.get(key).toString();
            }

            public static String hashMap(HashMap<UUID, Duration> hashMap) {
                ArrayList<String> keys = new ArrayList<>();
                StringBuilder result = new StringBuilder();
                for (UUID key : hashMap.keySet()) {
                    keys.add(DurationHashMap.toString.hashMapKey(hashMap, key));
                }

                for (int i = 0; i < keys.size(); i++) {
                    if (i == keys.size() - 1) {
                        result.append(keys.get(i));
                    } else {
                        result.append(keys.get(i)).append(";");
                    }
                }

                return result.toString();
            }
        }

        public static class fromString {
            public static HashMap<UUID, Duration> hashMap(String string) {
                HashMap<UUID, Duration> result = new HashMap<>();
                String[] keys = string.split(";");
                for (String key : keys) {
                    String[] keySplit = key.split(":");
                    result.put(UUID.fromString(keySplit[0]), Duration.parse(keySplit[1]));
                }
                return result;
            }
        }
    }

    public static class UUIDStringHashMap {
        public static class toString {
            public static String hashMapKey(HashMap<UUID, String> hashMap, UUID key) {
                return key.toString() + ":" + hashMap.get(key);
            }

            public static String hashMap(HashMap<UUID, String> hashMap) {
                ArrayList<String> keys = new ArrayList<>();
                StringBuilder result = new StringBuilder();
                for (UUID uuid : hashMap.keySet()) {
                    keys.add(UUIDStringHashMap.toString.hashMapKey(hashMap, uuid));
                }

                for (int i = 0; i < keys.size(); i++) {
                    if (i == keys.size() - 1) {
                        result.append(keys.get(i));
                    } else {
                        result.append(keys.get(i)).append(";");
                    }
                }

                return result.toString();
            }
        }

        public static class fromString {
            public static HashMap<UUID, String> hashMap(String string) {
                if (string.equals("")) return new HashMap<>();
                HashMap<UUID, String> result = new HashMap<>();
                String[] keys = string.split(";");
                for (String key : keys) {
                    String[] keySplit = key.split(":");
                    result.put(UUID.fromString(keySplit[0]), keySplit[1]);
                }
                return result;
            }
        }
    }
}
