package gg.meza.supporters;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.List;

public class Supporters {
    public List<TierEntry> tiers = List.of();
    public List<Member> joined7Days = List.of();
    private static final String[] DELIMITERS = {" ⋄ ", " · "};

    public static Text asRainbowList(List<Text> names) {
        MutableText finalText = Text.empty();
        float hueStep = 1.0f / Math.max(names.size(), 1);

        for (int i = 0; i < names.size(); i++) {
            float hue = i * hueStep;
            int rgb = Color.HSBtoRGB(hue, 0.6f, 1.0f);

            finalText.append(names.get(i).copy()
                    .styled(style -> style.withColor(TextColor.fromRgb(rgb))));

            if (i < names.size() - 1) {
                String delimiter = DELIMITERS[i % DELIMITERS.length];
                finalText.append(Text.literal(delimiter)
                        .styled(style -> style.withColor(Formatting.GRAY)));
            }
        }

        return finalText;
    }

    public static Text asDistinguishedList(List<MutableText> names) {
        MutableText finalText = Text.empty();
        String delimiter = " · ";

        for (int i = 0; i < names.size(); i++) {
            finalText.append(names.get(i)
                    .styled(style -> style.withColor(Formatting.GOLD)));

            if (i < names.size() - 1) {
                finalText.append(Text.literal(delimiter)
                        .styled(style -> style.withColor(Formatting.GRAY)));
            }
        }

        return finalText;
    }
}
