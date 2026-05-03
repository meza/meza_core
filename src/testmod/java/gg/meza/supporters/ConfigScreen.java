package gg.meza.supporters;

import gg.meza.supporters.clothconfig.SupportCategory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("your config title"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        SupportCategory.add(builder, entryBuilder);
        return builder.build();
    }
}
