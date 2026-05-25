package gay.themattabase.idletickarmorstands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;

import java.util.function.Supplier;

public final class IdleTickArmorStandsGameRules {

    public static Supplier<GameRule<Boolean>> ENABLED;

    public static Supplier<GameRule<Boolean>> SKIP_WHEN_ON_GROUND;
    public static Supplier<GameRule<Boolean>> SKIP_WHEN_NO_GRAVITY;

    public static Supplier<GameRule<Boolean>> DONT_SKIP_IF_PASSENGER;
    public static Supplier<GameRule<Boolean>> DONT_SKIP_IF_HAS_PASSENGERS;
    public static Supplier<GameRule<Boolean>> DONT_SKIP_IF_ON_FIRE;
    public static Supplier<GameRule<Boolean>> DONT_SKIP_IF_HURT_MARKED;
    public static Supplier<GameRule<Boolean>> DONT_SKIP_IF_MOVING;

    public static Supplier<GameRule<Integer>> SKIP_INTERVAL;

    private IdleTickArmorStandsGameRules() {}

    public static GameRule<Boolean> createBool(boolean defaultValue) {
        return new GameRule<>(
                GameRuleCategory.MOBS,
                GameRuleType.BOOL,
                BoolArgumentType.bool(),
                GameRuleTypeVisitor::visitBoolean,
                Codec.BOOL,
                b -> b ? 1 : 0,
                defaultValue,
                FeatureFlagSet.of()
        );
    }

    public static GameRule<Integer> createInt(int defaultValue, int min, int max) {
        return new GameRule<>(
                GameRuleCategory.MOBS,
                GameRuleType.INT,
                IntegerArgumentType.integer(min, max),
                GameRuleTypeVisitor::visitInteger,
                Codec.intRange(min, max),
                i -> i,
                defaultValue,
                FeatureFlagSet.of()
        );
    }
}
