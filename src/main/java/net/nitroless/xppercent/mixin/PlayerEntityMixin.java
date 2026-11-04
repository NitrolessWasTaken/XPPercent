package net.nitroless.xppercent.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.nitroless.xppercent.XPPercent;
import net.nitroless.xppercent.XPPercentGameRule;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getXpToDrop", at = @At("HEAD"), cancellable = true)
    public void xppercent$getXpToDrop(CallbackInfoReturnable<Integer> cir) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (self.getWorld() instanceof ServerWorld serverWorld) {
            double percentage = (double)(serverWorld.getGameRules().get(XPPercentGameRule.XP_PERCENTAGE).get()) / 100;
            boolean canDrop = !self.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && !self.isSpectator();
            int experience = self.getScore();
            boolean debug = XPPercent.DEBUG;
            int dropAmount = 0;

            if (canDrop) {
                dropAmount = Math.toIntExact(Math.round(experience * (percentage)));
                if (debug) {
                    XPPercent.LOGGER.info("dropping "+ dropAmount + " experience");
                }
            }
            cir.setReturnValue(dropAmount);
        }
    }
}
