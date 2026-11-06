package net.nitroless.xppercent.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
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
    public void getXpToDrop(CallbackInfoReturnable<Integer> cir) {
        Player player = (Player) (Object) this;
        if (player.getCommandSenderWorld() instanceof ServerLevel serverWorld) {
            double percentage = (double)(serverWorld.getGameRules().getRule(XPPercentGameRule.XP_PERCENTAGE).getCommandResult()) / 100;
            boolean canDrop = !serverWorld.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) && !player.isSpectator();
            int experience = player.getScore();
            int dropAmount = 0;

            if (canDrop) {
                dropAmount = Math.toIntExact(Math.round(experience * (percentage)));
                XPPercent.LOGGER.debug("dropping "+ dropAmount + " experience from " + player.getName().tryCollapseToString());
            }
            cir.setReturnValue(dropAmount);
        }
    }
}
