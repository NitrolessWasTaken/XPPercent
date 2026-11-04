package net.nitroless.xppercent;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public final class XPPercentGameRule {
    public static final GameRules.Key<GameRules.IntRule> XP_PERCENTAGE = GameRuleRegistry.register("dropXPPercentage", GameRules.Category.PLAYER, GameRuleFactory.createIntRule(50));
    static void init() {}
}
