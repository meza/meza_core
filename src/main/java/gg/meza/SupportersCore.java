package gg.meza;

import gg.meza.supporters.SupporterLoader;
import gg.meza.supporters.TierEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/*? if fabric {*/
/*import net.fabricmc.api.ModInitializer;
*//*?}*/

/*? if neoforge {*/
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
/*?}*/

/*? if fabric {*/
/*public class SupportersCore implements ModInitializer {
*//*?}*/
/*? if forgeLike {*/
@Mod(SupportersCore.MOD_ID)
public class SupportersCore {
/*?}*/

    public static final String MOD_ID = "supporter-core";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final SupporterLoader LOADER = new SupporterLoader();


    /*? if fabric {*/
    /*@Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + " Initializing");
        LOADER.preload();
    }
    *//*?}*/

    /*? if forgeLike {*/
    public SupportersCore() {
        LOGGER.info(MOD_ID + " Initializing");
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD ,modid = SupportersCore.MOD_ID)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOADER.preload();
        }
    }
    /*?}*/

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static SupporterLoader getLoader() {
        return LOADER;
    }

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static List<TierEntry> getTiers() {
        return LOADER.getTiers();
    }

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static Text getNewSupportersText() {
        return LOADER.getNewSupportersText();
    }
}
