package gg.meza.supporters;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import static gg.meza.core.MezaCore.MOD_ID;


@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class SupportersTestMod {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModList.get()
                .getModContainerById(MOD_ID)
                .ifPresent(container -> container.registerExtensionPoint(
                        IConfigScreenFactory.class,
                        (modContainer, parent) -> ConfigScreen.getConfigScreen(parent)
                ));
    }
}
