package core.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsManager {
    private static final File FILE = new File("settings.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Settings settings;

    public static Settings get() {
        return settings;
    }

    private static Settings defaults() {
        Settings defaults = new Settings();
        defaults.highlight = "Monokai";
        defaults.general = "Monokai";
        return defaults;
    }

    public static class Settings {
        public String highlight;
        public String general;

        public Settings() {}
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(settings, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!FILE.exists()) {
            settings = defaults();
        }
        try (FileReader reader = new FileReader(FILE)) {
            settings = GSON.fromJson(reader, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            settings = defaults();
        }
    }
}
