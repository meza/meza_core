package gg.meza.supporters.clothconfig;

import gg.meza.supporters.SupportersCore;
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

public class SupportCategory {
    private static void renderSupporters(ConfigCategory supportCategory, ConfigEntryBuilder entryBuilder) {
        List<TierEntry> tiers = SupportersCore.getTiers();

        Text newSupportersText = SupportersCore.getNewSupportersText();
        if (!newSupportersText.getString().isBlank()) {
            supportCategory.addEntry(entryBuilder
                    .startTextDescription(Text.translatable("supporters_core.config.support.newcomers"))
                    .build());
            supportCategory.addEntry(new SupporterListEntry(newSupportersText));
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.literal(" ")).build());

        }
        if (!tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("supporters_core.config.support.list")).build());
        }
        for (TierEntry tier : tiers) {
            Text title = tier.emoji != null ? Text.of(tier.emoji).copy().append(Text.literal(" ")).append(Text.of(tier.name)) : Text.of(tier.name);

            SubCategoryBuilder subCategory = entryBuilder.startSubCategory(title)
                    .setExpanded(true);

            subCategory.add(new SupporterListEntry(Supporters.asRainbowList(tier.members.stream().map(m -> Text.of(m.name)).toList())));
            supportCategory.addEntry(subCategory.build());
        }

        if (tiers.isEmpty()) {
            supportCategory.addEntry(entryBuilder.startTextDescription(Text.translatable("supporters_core.config.support.empty")).build());
        }
    }

    public static void add(ConfigBuilder builder, ConfigEntryBuilder entryBuilder) {
        Text description =Text.translatable("supporters_core.config.support.description1")
                .append("\n")
                .append(Text.translatable("supporters_core.config.support.description2"));

        /*? if >=1.21 {*/
        Text category = Text.literal("❤ ").append(Text.translatable("supporters_core.config.category.support"));
        /*?} else {*/
        /*Text category = Text.translatable("supporters_core.config.category.support");
        *//*?}*/


        ConfigCategory supportCategory = builder.getOrCreateCategory(category)
                .addEntry(entryBuilder.startTextDescription(description).build())

                .addEntry(new HeartTextEntry(Text.translatable("supporters_core.config.support.cta")
                        .styled(style -> style
                                        .withColor(Formatting.AQUA)
                                        .withUnderline(true)
                                        .withBold(true)
                                        /*? if >=1.21.5 {*/
                                        .withClickEvent(new ClickEvent.OpenUrl(SupportersCore.getSponsorUrl()))
                                        .withHoverEvent(new HoverEvent.ShowText(Text.translatable("supporters_core.config.support.cta.tooltip")))
                                        /*?} else {*/
                                        /*.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SupportersCore.getSponsorUrl().toString()))
                                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("supporters_core.config.support.cta.tooltip")))
                                        *//*?}*/
                                )));
        renderSupporters(supportCategory, entryBuilder);
    }
}
