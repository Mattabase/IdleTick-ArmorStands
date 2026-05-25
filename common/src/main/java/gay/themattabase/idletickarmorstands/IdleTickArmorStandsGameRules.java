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
