package gg.meza.supporters;

import static gg.meza.core.MezaCore.LOGGER;

import net.minecraft.text.Text;

import java.net.URI;
import java.util.List;

/*? if fabric {*/
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import gg.meza.supporters.reminder.FabricReminders;
import net.fabricmc.api.Environment;
/*?}*/

/*? if forgeLike {*/
/*import static gg.meza.core.MezaCore.MOD_ID;
*//*?}*/

/*? if neoforge {*/
/*import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import gg.meza.supporters.reminder.NeoforgeReminders;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
*//*?}*/

/*? if fabric {*/
@Environment(EnvType.CLIENT)
public class SupportersCore implements ClientModInitializer {
/*?}*/
/*? if forgeLike {*/
/*@Mod(MOD_ID)
public class SupportersCore {
*//*?}*/

    private static final SupporterLoader LOADER = new SupporterLoader();
    private static final URI SPONSOR_URL = URI.create("https://ko-fi.com/meza");


    /*? if fabric {*/
    @Override
    public void onInitializeClient() {
        LOGGER.debug("Supporters Initializing");
        LOADER.preload();
        FabricReminders.initialize();
    }
    /*?}*/

    /*? if forgeLike {*/
    /*public SupportersCore() {
        LOGGER.debug("Supporters Initializing");
    }

    /^? if >= 1.21.9 {^/
    @EventBusSubscriber(modid = MOD_ID)
    /^?} else {^/
    /^@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD ,modid = MOD_ID)
    ^//^?}^/
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOADER.preload();
            NeoForge.EVENT_BUS.register(NeoforgeReminders.class);
        }
    }
    *//*?}*/

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static SupporterLoader getLoader() {
        return LOADER;
    }

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static List<TierEntry> getTiers() {
        return LOADER.getTiers();
    }

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static URI getSponsorUrl() {
        return SPONSOR_URL;
    }

    @SuppressWarnings({"unused"}) // exposed for consumer use
    public static Text getNewSupportersText() {
        return LOADER.getNewSupportersText();
    }
}
