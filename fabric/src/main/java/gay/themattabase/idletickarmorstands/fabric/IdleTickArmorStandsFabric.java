package gay.themattabase.idletickarmorstands.fabric;

import gay.themattabase.idletickarmorstands.IdleTickArmorStands;
import gay.themattabase.idletickarmorstands.IdleTickArmorStandsGameRules;
import gay.themattabase.idletickarmorstands.config.IdleTickArmorStandsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;

import java.util.function.Supplier;

public class IdleTickArmorStandsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        registerGameRules();
        IdleTickArmorStands.init();
        IdleTickArmorStandsConfig.load(FabricLoader.getInstance().getConfigDir());
    }

    private void registerGameRules() {
        IdleTickArmorStandsGameRules.ENABLED = regBool("enabled", true);
        IdleTickArmorStandsGameRules.SKIP_INTERVAL = regInt("skip_interval", 20, 2, 100);
    }

    private static Supplier<GameRule<Boolean>> regBool(String name, boolean def) {
        GameRule<Boolean> rule = Registry.register(BuiltInRegistries.GAME_RULE,
                Identifier.fromNamespaceAndPath(IdleTickArmorStands.MOD_ID, name),
                IdleTickArmorStandsGameRules.createBool(def));
        return () -> rule;
    }

    private static Supplier<GameRule<Integer>> regInt(String name, int def, int min, int max) {
        GameRule<Integer> rule = Registry.register(BuiltInRegistries.GAME_RULE,
                Identifier.fromNamespaceAndPath(IdleTickArmorStands.MOD_ID, name),
                IdleTickArmorStandsGameRules.createInt(def, min, max));
        return () -> rule;
    }
}
