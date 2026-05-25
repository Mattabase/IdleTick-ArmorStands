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

        boolean enabled;
        int skipInterval;
        if (IdleTickArmorStandsGameRules.ENABLED != null) {
            ServerLevel serverLevel = (ServerLevel) self.level();
            GameRules rules = serverLevel.getGameRules();
            enabled = rules.get(IdleTickArmorStandsGameRules.ENABLED.get());
            skipInterval = rules.get(IdleTickArmorStandsGameRules.SKIP_INTERVAL.get());
        } else {
            IdleTickArmorStandsConfig cfg = IdleTickArmorStandsConfig.get();
            enabled = cfg.enabled;
            skipInterval = cfg.skipInterval;
        }

        if (!enabled) return;

        IdleTickArmorStandsConfig cfg = IdleTickArmorStandsConfig.get();

        boolean grounded = (cfg.skipWhenOnGround && self.onGround())
                || (cfg.skipWhenNoGravity && self.isNoGravity());

        boolean moving = false;
        if (cfg.dontSkipIfMoving) {
            Vec3 vel = self.getDeltaMovement();
            moving = vel.x * vel.x + vel.z * vel.z > 1.0E-6;
        }

        boolean blocked = moving
                || (cfg.dontSkipIfPassenger && self.isPassenger())
                || (cfg.dontSkipIfHasPassengers && !self.getPassengers().isEmpty())
                || (cfg.dontSkipIfOnFire && self.isOnFire())
                || (cfg.dontSkipIfHurtMarked && self.hurtMarked);

        boolean canSkip = grounded && !blocked;

        if (canSkip) {
            if (++idleTick$skipCounter < skipInterval) {
                self.tickCount++;
                ci.cancel();
                return;
            }
            idleTick$skipCounter = 0;
        } else {
            idleTick$skipCounter = 0;
        }
    }
}
