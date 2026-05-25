package gay.themattabase.idletickarmorstands.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IdleTickArmorStandsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static IdleTickArmorStandsConfig INSTANCE = new IdleTickArmorStandsConfig();
    private static Path configPath;

    public boolean enabled = true;

    public boolean skipWhenOnGround = true;
    public boolean skipWhenNoGravity = true;

    public boolean dontSkipIfPassenger = true;
    public boolean dontSkipIfHasPassengers = true;
    public boolean dontSkipIfOnFire = true;
    public boolean dontSkipIfHurtMarked = true;
    public boolean dontSkipIfMoving = true;

    public int skipInterval = 20;

    public static IdleTickArmorStandsConfig get() {
        return INSTANCE;
    }

    public static void load(Path configDir) {
        configPath = configDir.resolve("idletick_armor_stands.json");
        if (Files.exists(configPath)) {
            try {
                String json = Files.readString(configPath);
                INSTANCE = GSON.fromJson(json, IdleTickArmorStandsConfig.class);
                if (INSTANCE == null) INSTANCE = new IdleTickArmorStandsConfig();
                INSTANCE.clampValues();
            } catch (IOException e) {
                INSTANCE = new IdleTickArmorStandsConfig();
            }
        } else {
            INSTANCE = new IdleTickArmorStandsConfig();
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
