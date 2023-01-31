package net.boypika.bring_decay;

import net.boypika.bring_decay.util.Trades;
import net.fabricmc.api.ModInitializer;
import net.boypika.bring_decay.potion.ModPotions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bring_Decay implements ModInitializer {
	public static final String MOD_ID = "bring_decay";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModPotions.registerPotions();
        Trades.registermytrades();
        Bring_Decay.LOGGER.info("Working");

    }
}
