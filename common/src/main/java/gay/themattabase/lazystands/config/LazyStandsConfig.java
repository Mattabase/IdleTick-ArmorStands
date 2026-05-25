package gay.themattabase.lazystands.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LazyStandsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static LazyStandsConfig INSTANCE = new LazyStandsConfig();
    private static Path configPath;

    // Master switch
    public boolean enabled = true;

    // Conditions that ALLOW skipping (stand must meet at least one)
    public boolean skipWhenOnGround = true;
    public boolean skipWhenNoGravity = true;

    // Conditions that PREVENT skipping (if true, these checks are active)
    public boolean dontSkipIfPassenger = true;
    public boolean dontSkipIfHasPassengers = true;
    public boolean dontSkipIfOnFire = true;
    public boolean dontSkipIfHurtMarked = true;
    public boolean dontSkipIfMoving = true;

    // How many ticks between full ticks (2-100, default 20 = skip 19/20)
    public int skipInterval = 20;

    public static LazyStandsConfig get() {
        return INSTANCE;
    }

    public static void load(Path configDir) {
        configPath = configDir.resolve("lazystands.json");
        if (Files.exists(configPath)) {
            try {
                String json = Files.readString(configPath);
                INSTANCE = GSON.fromJson(json, LazyStandsConfig.class);
                if (INSTANCE == null) INSTANCE = new LazyStandsConfig();
                INSTANCE.clampValues();
            } catch (IOException e) {
                INSTANCE = new LazyStandsConfig();
            }
        } else {
            INSTANCE = new LazyStandsConfig();
            save();
        }
    }

    public static void save() {
        if (configPath == null) return;
        INSTANCE.clampValues();
        try {
            Files.createDirectories(configPath.getParent());
            Files.writeString(configPath, GSON.toJson(INSTANCE));
        } catch (IOException e) {
            // Silent — config save failure is non-fatal
        }
    }

    private void clampValues() {
        if (skipInterval < 2) skipInterval = 2;
        if (skipInterval > 100) skipInterval = 100;
    }
}
