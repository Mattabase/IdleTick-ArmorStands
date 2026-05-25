package gay.themattabase.lazystands.mixin;

import gay.themattabase.lazystands.config.LazyStandsConfig;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Skips most ticks for stationary armor stands based on config.
 *
 * <p>Stands that are on the ground or have NoGravity set skip ticks.
 * Stands actively falling (not on ground, has gravity) always run
 * every tick so they land normally.</p>
 *
 * <p>Targets {@link LivingEntity#tick()} because {@link ArmorStand}
 * does not override {@code tick()} in MC 26.1.2.</p>
 */
@Mixin(LivingEntity.class)
public abstract class ArmorStandTickMixin {

    @Unique
    private int lazyStands$skipCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)
    private void lazyStands$skipStationaryArmorStandTick(CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (!(self instanceof ArmorStand) || self.level().isClientSide()) return;

        LazyStandsConfig cfg = LazyStandsConfig.get();
        if (!cfg.enabled) return;

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
            if (++lazyStands$skipCounter < cfg.skipInterval) {
                self.tickCount++;
                ci.cancel();
                return;
            }
            lazyStands$skipCounter = 0;
        } else {
            lazyStands$skipCounter = 0;
        }
    }
}
