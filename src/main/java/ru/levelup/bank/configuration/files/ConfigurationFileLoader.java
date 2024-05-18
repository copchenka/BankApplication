package ru.levelup.bank.configuration.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFileLoader {

    public static Map<String, String> LoadConfiguration() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(ConfiguratinFileConstants.CONFIGURATION_FILE_NAME))) {
            String line;
            Map<String, String> properties = new HashMap<>();
            while ((line = fileReader.readLine()) != null) {
                if (!line.isBlank()) { // строка не пустая и не состоит из одних пробелов
                    String[] keyValue = line.split("=");
                    properties.put(keyValue[0], keyValue[1]);
                }
            }
            return properties;
        } catch (IOException exc) {
            System.out.println("Failed to load configuration file");
            throw new RuntimeException(exc);
        }
    }
}
