package net.nitroless.xppercent.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.rule.GameRules;
import net.nitroless.xppercent.XPPercent;
import net.nitroless.xppercent.XPPercentGameRule;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getExperienceToDrop", at = @At("HEAD"), cancellable = true)
    public void xppercent$getXpToDrop(CallbackInfoReturnable<Integer> cir) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (self.getEntityWorld() instanceof ServerWorld serverWorld) {
            double percentage = (double)(serverWorld.getGameRules().getValue(XPPercentGameRule.XP_PERCENTAGE)) / 100;
            boolean canDrop = !serverWorld.getGameRules().getValue(GameRules.KEEP_INVENTORY) && !self.isSpectator();
            int experience = self.getScore();
            boolean debug = XPPercent.DEBUG;
            int dropAmount = 0;

            if (canDrop) {
                // im rounding twice because im swag
                dropAmount = Math.toIntExact(Math.round(experience * (percentage)));
                if (debug) {
                    XPPercent.LOGGER.info("dropping "+ dropAmount + " experience");
                }
            }
            cir.setReturnValue(dropAmount);
        }
    }
}
