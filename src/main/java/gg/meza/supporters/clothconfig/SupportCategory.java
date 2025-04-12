package gg.meza.supporters.clothconfig;

import gg.meza.SupportersCore;
import gg.meza.supporters.Supporters;
import gg.meza.supporters.TierEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import static gg.meza.SupportersCore.MOD_ID;

public class SupportCategory {
    private static void renderSupporters(ConfigCategory supportCategory, ConfigEntryBuilder entryBuilder) {
        List<TierEntry> tiers = SupportersCore.getTiers();

        Text newSupportersText = SupportersCore.getNewSupportersText();
        if (!newSupportersText.getString().isBlank()) {
            supportCategory.addEntry(entryBuilder
                    .startTextDescription(Text.translatable("supporters-core.config.support.newcomers"))
                    .build());
            supportCategory.addEntry(new SupporterListEntry(newSupportersText));
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.literal(" ")).build());

        }
        if (!tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("supporters-core.config.support.list")).build());
        }
        for (TierEntry tier : tiers) {
            String title = tier.emoji != null ? tier.emoji + " " + tier.name : tier.name;

            SubCategoryBuilder subCategory = entryBuilder.startSubCategory(Text.literal(title))
                    .setExpanded(true);

            subCategory.add(new SupporterListEntry(Supporters.asRainbowList(tier.members.stream().map(m -> m.name).toList())));
            supportCategory.addEntry(subCategory.build());
        }

        if (tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("supporters-core.config.support.empty")).build());
        }
    }

    public static void add(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        ConfigCategory supportCategory = builder.getOrCreateCategory(Text.literal("\uD83D\uDC99 ").append(Text.translatable("supporters-core.config.category.support")))
                .addEntry(entryBuilder.startTextDescription(Text.translatable("supporters-core.config.support.description1").append("\n").append(Text.translatable("supporters-core.config.support.description2"))).build())

                .addEntry(new HeartTextEntry(Text.translatable("supporters-core.config.support.cta")
                        .styled(style -> style
                                        .withColor(Formatting.AQUA)
                                        .withUnderline(true)
                                        .withBold(true)
                                        /*? if >=1.21.5 {*/
                                        .withClickEvent(new ClickEvent.OpenUrl(SupportersCore.getSponsorUrl()))
                                        .withHoverEvent(new HoverEvent.ShowText(Text.translatable("supporters-core.config.support.cta.tooltip")))
                                        /*?} else {*/
                                        /*.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SupportersCore.getSponsorUrl().toString()))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("supporters-core.config.support.cta.tooltip")))
                                        *//*?}*/
                                )));
        renderSupporters(supportCategory, entryBuilder);
    }
}
