package gg.meza.supporters;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

import java.awt.*;
import java.util.List;

public class Supporters {
    public List<TierEntry> tiers = List.of();
    public List<Member> joined7Days = List.of();
    private static final String[] DELIMITERS = {" ⋄ ", " · "};

    public static Component asRainbowList(List<MutableComponent> names) {
        MutableComponent finalText = Component.empty();
        float hueStep = 1.0f / Math.max(names.size(), 1);

        for (int i = 0; i < names.size(); i++) {
            float hue = i * hueStep;
            int rgb = Color.HSBtoRGB(hue, 0.6f, 1.0f);

            finalText.append(names.get(i).copy()
                    .withStyle(style -> style.withColor(TextColor.fromRgb(rgb))));

            if (i < names.size() - 1) {
                String delimiter = DELIMITERS[i % DELIMITERS.length];
                finalText.append(Component.literal(delimiter)
                        .withStyle(style -> style.withColor(ChatFormatting.GRAY)));
            }
        }

        return finalText;
    }

    public static Component asDistinguishedList(List<MutableComponent> names) {
        MutableComponent finalText = Component.empty();
        String delimiter = " · ";

        for (int i = 0; i < names.size(); i++) {
            finalText.append(names.get(i)
                    .withStyle(style -> style.withColor(ChatFormatting.GOLD)));

            if (i < names.size() - 1) {
                finalText.append(Component.literal(delimiter)
                        .withStyle(style -> style.withColor(ChatFormatting.GRAY)));
            }
        }

        return finalText;
    }
}
