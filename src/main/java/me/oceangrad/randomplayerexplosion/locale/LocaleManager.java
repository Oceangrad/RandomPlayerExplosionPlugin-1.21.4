package me.oceangrad.randomplayerexplosion.locale;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class LocaleManager {
    private static final String LOCALIZATION_PATH = "./explosion_locale/";
    private static final String DEFAULT_LOC = "{\n\"explosionTimerMessage\": \"You will explode in $timer$ seconds.\"\n}";

    private static final Gson GSON = new Gson();
    private static final HashMap<String, Locale> LOCALE_MAP = new HashMap<>();

    public static void loadLocalization() {
        File dir = Path.of(LOCALIZATION_PATH).toFile();

        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File[] files = dir.listFiles();

            if (files.length == 0) {
                File defaultLocale = new File(LOCALIZATION_PATH+"en_US.json");
                defaultLocale.createNewFile();

                FileWriter fw = new FileWriter(defaultLocale);
                fw.write(DEFAULT_LOC);
                fw.close();

                files = new File[] { defaultLocale };
            }

            for (File file : files) {
                JsonReader rd = new JsonReader(new FileReader(file));
                Locale locale = GSON.fromJson(rd, Locale.class);
                LOCALE_MAP.put(file.getName().split(".json")[0].toLowerCase(), locale);
                rd.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Locale getLocale(String localeCode) {
        return LOCALE_MAP.getOrDefault(localeCode.toLowerCase(), getDefaultLocale());
    }

    private static Locale getDefaultLocale() {
        return LOCALE_MAP.get("en_us");
    }
}
