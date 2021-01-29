package Game;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigData {
    public static HashMap<String, String> configData = new HashMap<>();

    public static void initConfig() {
        try {
            Scanner configIn = new Scanner(new File("config.cfg"));
            String line;
            while(configIn.hasNextLine()) {
                line = configIn.nextLine();
                String[] configs = line.split("=");
                configData.put(configs[0].toLowerCase(), configs[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        if(ConfigData.configData.containsKey(key.toLowerCase())) {
            return configData.get(key);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

}
