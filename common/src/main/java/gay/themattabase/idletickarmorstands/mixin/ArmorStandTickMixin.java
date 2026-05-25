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

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)
    private void idleTick$skipStationaryArmorStandTick(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (!(self instanceof ArmorStand) || self.level().isClientSide()) return;

        IdleTickArmorStandsConfig cfg = IdleTickArmorStandsConfig.get();
        ServerLevel level = (ServerLevel) self.level();
        GameRules rules = level.getGameRules();

        if (!cfg.enabled || (IdleTickArmorStandsGameRules.ENABLED != null && !rules.get(IdleTickArmorStandsGameRules.ENABLED.get()))) {
            return;
        }

        boolean grounded = (cfg.skipWhenOnGround && (IdleTickArmorStandsGameRules.SKIP_WHEN_ON_GROUND == null || rules.get(IdleTickArmorStandsGameRules.SKIP_WHEN_ON_GROUND.get()))) && self.onGround()
                || (cfg.skipWhenNoGravity && (IdleTickArmorStandsGameRules.SKIP_WHEN_NO_GRAVITY == null || rules.get(IdleTickArmorStandsGameRules.SKIP_WHEN_NO_GRAVITY.get()))) && self.isNoGravity();

        if (!grounded) {
            idleTick$skipCounter = 0;
            return;
        }

        if (cfg.dontSkipIfMoving || (IdleTickArmorStandsGameRules.DONT_SKIP_IF_MOVING != null && rules.get(IdleTickArmorStandsGameRules.DONT_SKIP_IF_MOVING.get()))) {
            Vec3 vel = self.getDeltaMovement();
            if (vel.x * vel.x + vel.z * vel.z > 1.0E-6) {
                idleTick$skipCounter = 0;
                return;
            }
        }

        if ((cfg.dontSkipIfPassenger || (IdleTickArmorStandsGameRules.DONT_SKIP_IF_PASSENGER != null && rules.get(IdleTickArmorStandsGameRules.DONT_SKIP_IF_PASSENGER.get()))) && self.isPassenger()) {
            idleTick$skipCounter = 0;
            return;
        }

        if ((cfg.dontSkipIfHasPassengers || (IdleTickArmorStandsGameRules.DONT_SKIP_IF_HAS_PASSENGERS != null && rules.get(IdleTickArmorStandsGameRules.DONT_SKIP_IF_HAS_PASSENGERS.get()))) && !self.getPassengers().isEmpty()) {
            idleTick$skipCounter = 0;
            return;
        }

        if ((cfg.dontSkipIfOnFire || (IdleTickArmorStandsGameRules.DONT_SKIP_IF_ON_FIRE != null && rules.get(IdleTickArmorStandsGameRules.DONT_SKIP_IF_ON_FIRE.get()))) && self.isOnFire()) {
            idleTick$skipCounter = 0;
            return;
        }

        if ((cfg.dontSkipIfHurtMarked || (IdleTickArmorStandsGameRules.DONT_SKIP_IF_HURT_MARKED != null && rules.get(IdleTickArmorStandsGameRules.DONT_SKIP_IF_HURT_MARKED.get()))) && self.hurtMarked) {
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
