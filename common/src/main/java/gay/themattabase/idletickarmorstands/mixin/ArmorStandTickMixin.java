package gay.themattabase.idletickarmorstands.mixin;

import gay.themattabase.idletickarmorstands.IdleTickArmorStandsGameRules;
import gay.themattabase.idletickarmorstands.config.IdleTickArmorStandsConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ArmorStandTickMixin {

    @Unique
    private int idleTick$skipCounter = 0;

    @Unique
    private boolean isFeatureActive(boolean configVal, java.util.function.Supplier<net.minecraft.world.level.gamerules.GameRule<Boolean>> ruleSupplier, GameRules rules) {
        if (!configVal) return false;
        if (ruleSupplier == null) return true;
        return rules.get(ruleSupplier.get());
    }

    @Unique
    private boolean isSafetyCheckActive(boolean configVal, java.util.function.Supplier<net.minecraft.world.level.gamerules.GameRule<Boolean>> ruleSupplier, GameRules rules) {
        if (configVal) return true;
        if (ruleSupplier == null) return false;
        return rules.get(ruleSupplier.get());
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)
    private void idleTick$skipStationaryArmorStandTick(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (!(self instanceof ArmorStand) || self.level().isClientSide()) return;

        IdleTickArmorStandsConfig cfg = IdleTickArmorStandsConfig.get();
        ServerLevel level = (ServerLevel) self.level();
        GameRules rules = level.getGameRules();

        if (!isFeatureActive(cfg.enabled, IdleTickArmorStandsGameRules.ENABLED, rules)) {
            return;
        }

        boolean grounded = (isFeatureActive(cfg.skipWhenOnGround, IdleTickArmorStandsGameRules.SKIP_WHEN_ON_GROUND, rules) && self.onGround())
                || (isFeatureActive(cfg.skipWhenNoGravity, IdleTickArmorStandsGameRules.SKIP_WHEN_NO_GRAVITY, rules) && self.isNoGravity());

        if (!grounded) {
            idleTick$skipCounter = 0;
            return;
        }

        if (isSafetyCheckActive(cfg.dontSkipIfMoving, IdleTickArmorStandsGameRules.DONT_SKIP_IF_MOVING, rules)) {
            Vec3 vel = self.getDeltaMovement();
            if (vel.x * vel.x + vel.z * vel.z > 1.0E-6) {
                idleTick$skipCounter = 0;
                return;
            }
        }

        if (isSafetyCheckActive(cfg.dontSkipIfPassenger, IdleTickArmorStandsGameRules.DONT_SKIP_IF_PASSENGER, rules) && self.isPassenger()) {
            idleTick$skipCounter = 0;
            return;
        }

        if (isSafetyCheckActive(cfg.dontSkipIfHasPassengers, IdleTickArmorStandsGameRules.DONT_SKIP_IF_HAS_PASSENGERS, rules) && !self.getPassengers().isEmpty()) {
            idleTick$skipCounter = 0;
            return;
        }

        if (isSafetyCheckActive(cfg.dontSkipIfOnFire, IdleTickArmorStandsGameRules.DONT_SKIP_IF_ON_FIRE, rules) && self.isOnFire()) {
            idleTick$skipCounter = 0;
            return;
        }

        if (isSafetyCheckActive(cfg.dontSkipIfHurtMarked, IdleTickArmorStandsGameRules.DONT_SKIP_IF_HURT_MARKED, rules) && self.hurtMarked) {
            idleTick$skipCounter = 0;
            return;
        }

        int skipInterval = cfg.skipInterval;
        if (IdleTickArmorStandsGameRules.SKIP_INTERVAL != null) {
            int ruleInterval = rules.get(IdleTickArmorStandsGameRules.SKIP_INTERVAL.get());
            if (ruleInterval >= 2) skipInterval = ruleInterval;
        }

        if (++idleTick$skipCounter < skipInterval) {
            self.tickCount++;
            ci.cancel();
        } else {
            idleTick$skipCounter = 0;
        }
    }
}
