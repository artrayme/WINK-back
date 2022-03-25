package org.wink.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class Config {
    private static Map<String, String> data = null;

    private Config() {
    }

    public static String getProperty(String configName) {
        return data.get(configName);
    }

    public static void init(String pathToConfigFile) throws IOException {
        if (data == null) {
            data = new HashMap<>();
            InputStream input = new FileInputStream(pathToConfigFile);
            Properties prop = new Properties();
            prop.load(input);
            prop.forEach((key, value) -> data.put((String) key, (String) value));
        }
    }
}
