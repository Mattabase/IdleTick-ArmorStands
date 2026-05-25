package gay.themattabase.idletickarmorstands.neoforge;

import gay.themattabase.idletickarmorstands.IdleTickArmorStands;
import gay.themattabase.idletickarmorstands.IdleTickArmorStandsGameRules;
import gay.themattabase.idletickarmorstands.config.IdleTickArmorStandsConfig;
import gay.themattabase.idletickarmorstands.neoforge.datagen.DataGenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.gamerules.GameRule;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod("idletick_armor_stands")
public class IdleTickArmorStandsNeoForge {

    private static final DeferredRegister<GameRule<?>> GAME_RULES =
            DeferredRegister.create(Registries.GAME_RULE, IdleTickArmorStands.MOD_ID);

    static {
        IdleTickArmorStandsGameRules.ENABLED = reg("enabled",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.SKIP_WHEN_ON_GROUND = reg("skip_when_on_ground",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.SKIP_WHEN_NO_GRAVITY = reg("skip_when_no_gravity",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_PASSENGER = reg("dont_skip_if_passenger",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_HAS_PASSENGERS = reg("dont_skip_if_has_passengers",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_ON_FIRE = reg("dont_skip_if_on_fire",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_HURT_MARKED = reg("dont_skip_if_hurt_marked",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.DONT_SKIP_IF_MOVING = reg("dont_skip_if_moving",
                () -> IdleTickArmorStandsGameRules.createBool(true));
        IdleTickArmorStandsGameRules.SKIP_INTERVAL = regInt("skip_interval",
                () -> IdleTickArmorStandsGameRules.createInt(20, 2, 100));
    }

    @SuppressWarnings("unchecked")
    private static Supplier<GameRule<Boolean>> reg(String name, Supplier<GameRule<Boolean>> sup) {
        return (Supplier<GameRule<Boolean>>) (Supplier<?>) GAME_RULES.register(name, sup);
    }

    @SuppressWarnings("unchecked")
    private static Supplier<GameRule<Integer>> regInt(String name, Supplier<GameRule<Integer>> sup) {
        return (Supplier<GameRule<Integer>>) (Supplier<?>) GAME_RULES.register(name, sup);
    }

    public IdleTickArmorStandsNeoForge(IEventBus eventBus, ModContainer container) {
        GAME_RULES.register(eventBus);
        IdleTickArmorStands.init();
        IdleTickArmorStandsConfig.load(FMLPaths.CONFIGDIR.get());

        eventBus.addListener(DataGenHandler::gatherData);

        if (FMLLoader.getCurrent().getDist().isClient()) {
            ClientSetup.init(container);
        }
    }
}
