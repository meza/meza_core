package gg.meza.supporters;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Date;

public class SupportersTestMod implements ClientModInitializer {

    /*? if >= 1.21.9 {*/
    private static final KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("supporters_test", "keybinds"));
    /*?}*/

    public static final KeyBinding openConfig = new KeyBinding(
            "supporters_test.config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            /*? if >= 1.21.9 {*/
            category
            /*?} else {*/
            /*"supporters_test.keybinds"
             *//*?}*/
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openConfig.wasPressed()) {
                client.setScreen(ConfigScreen.getConfigScreen(null));
            }
        });
    }
}
