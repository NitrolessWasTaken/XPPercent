package net.nitroless.xppercent;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XPPercent implements ModInitializer {
    public static final String MOD_ID = "xppercent";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Boolean DEBUG = false;

    @Override
    public void onInitialize() {
        XPPercentGameRule.init();
        LOGGER.info("Hey guys! It's me! XP!");
    }
}
