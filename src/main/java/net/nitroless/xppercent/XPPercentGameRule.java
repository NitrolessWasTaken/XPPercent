package net.nitroless.xppercent;

import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public final class XPPercentGameRule {
    public static final GameRule<Integer> XP_PERCENTAGE = GameRuleBuilder
            .forInteger(50)
            .category(GameRuleCategory.PLAYER)
            .buildAndRegister(Identifier.fromNamespaceAndPath(XPPercent.MOD_ID, "drop_xp_percentage"));
    static void init() {}
}
