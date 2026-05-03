package gg.meza.supporters.clothconfig;

import gg.meza.supporters.SupportersCore;
import gg.meza.supporters.Supporters;
import gg.meza.supporters.TierEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

import java.util.List;

public class SupportCategory {
    private static void renderSupporters(ConfigCategory supportCategory, ConfigEntryBuilder entryBuilder) {
        List<TierEntry> tiers = SupportersCore.getTiers();

        Component newSupportersText = SupportersCore.getNewSupportersText();
        if (!newSupportersText.getString().isBlank()) {
            supportCategory.addEntry(entryBuilder
                    .startTextDescription(Component.translatable("meza_core.config.support.newcomers"))
                    .build());
            supportCategory.addEntry(new SupporterListEntry(newSupportersText));
            supportCategory.addEntry(entryBuilder.startTextDescription(Component.literal(" ")).build());

        }
        if (!tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Component.translatable("meza_core.config.support.list")).build());
        }
        for (TierEntry tier : tiers) {
            Component title = tier.emoji != null ? Component.literal(tier.emoji).copy().append(Component.literal(" ")).append(Component.literal(tier.name)) : Component.literal(tier.name);

            SubCategoryBuilder subCategory = entryBuilder.startSubCategory(title)
                    .setExpanded(true);

            subCategory.add(new SupporterListEntry(Supporters.asRainbowList(tier.members.stream().map(m -> Component.literal(m.name)).toList())));
            supportCategory.addEntry(subCategory.build());
        }

        if (tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Component.translatable("meza_core.config.support.empty")).build());
        }
    }

    public static void add(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        Component description =Component.translatable("meza_core.config.support.description1")
                .append("\n")
                .append(Component.translatable("meza_core.config.support.description2"));

        Component category = Component.literal("❤ ").append(Component.translatable("meza_core.config.category.support"));



        ConfigCategory supportCategory = builder.getOrCreateCategory(category)
                .addEntry(entryBuilder.startTextDescription(description).build())

                .addEntry(new HeartTextEntry(Component.translatable("meza_core.config.support.cta")
                        .withStyle(style -> style
                                        .withColor(ChatFormatting.AQUA)
                                        .withUnderlined(true)
                                        .withBold(true)

                                        .withClickEvent(new ClickEvent.OpenUrl(SupportersCore.getSponsorUrl()))
                                        .withHoverEvent(new HoverEvent.ShowText(Component.translatable("meza_core.config.support.cta.tooltip")))

                                )));
        renderSupporters(supportCategory, entryBuilder);
    }
}
