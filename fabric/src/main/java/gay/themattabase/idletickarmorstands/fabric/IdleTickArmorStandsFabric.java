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
        IdleTickArmorStandsGameRules.SKIP_WHEN_ON_GROUND = regBool("skip_when_on_ground", true);
        IdleTickArmorStandsGameRules.SKIP_WHEN_NO_GRAVITY = regBool("skip_when_no_gravity", true);
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_PASSENGER = regBool("dont_skip_if_passenger", true);
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_HAS_PASSENGERS = regBool("dont_skip_if_has_passengers", true);
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_ON_FIRE = regBool("dont_skip_if_on_fire", true);
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_HURT_MARKED = regBool("dont_skip_if_hurt_marked", true);
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_MOVING = regBool("dont_skip_if_moving", true);
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
