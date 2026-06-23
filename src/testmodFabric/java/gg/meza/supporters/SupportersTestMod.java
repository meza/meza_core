package gg.meza.supporters;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class SupportersTestMod implements ClientModInitializer {

    private static final KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("supporters_test", "keybinds"));

    public static final KeyMapping openConfig = new KeyMapping(
            "supporters_test.config",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            category
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfig.consumeClick()) {
                //? if >= 26.2
                //client.gui.setScreen(ConfigScreen.getConfigScreen(null));

                //? if < 26.2
                client.setScreen(ConfigScreen.getConfigScreen(null));
            }
        });
    }
}
