package gg.meza;
/*? if fabric {*/
import gg.meza.supporters.SupporterLoader;
import gg.meza.supporters.TierEntry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/*?}*/

/*? if fabric {*/
public class SupportersCore implements ModInitializer {
/*?}*/
    public static final String MOD_ID = "supporter-core";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final SupporterLoader LOADER = new SupporterLoader();


    /*? if fabric {*/
    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + " Initializing");
        LOADER.preload();
    }
    /*?}*/

    public static SupporterLoader getLoader() {
        return LOADER;
    }

    public static List<TierEntry> getTiers() {
        return LOADER.getTiers();
    }

    public static Text getNewSupportersText() {
        return LOADER.getNewSupportersText();
    }
}
