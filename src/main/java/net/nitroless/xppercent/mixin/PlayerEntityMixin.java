package net.nitroless.xppercent.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRules;
import net.nitroless.xppercent.XPPercent;
import net.nitroless.xppercent.XPPercentGameRule;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "getBaseExperienceReward", at = @At("HEAD"), cancellable = true)
    public void xppercent$getXpToDrop(CallbackInfoReturnable<Integer> cir) {
        Player self = (Player)(Object)this;
        if (self.level() instanceof ServerLevel serverWorld) {
            double percentage = (double)(serverWorld.getGameRules().get(XPPercentGameRule.XP_PERCENTAGE)) / 100;
            boolean canDrop = !serverWorld.getGameRules().get(GameRules.KEEP_INVENTORY) && !self.isSpectator();
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
