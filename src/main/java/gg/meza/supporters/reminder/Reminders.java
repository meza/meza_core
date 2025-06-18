package gg.meza.supporters.reminder;

import gg.meza.supporters.SupportersCore;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Reminders {
    public static final String SENT_KEY = "gg.meza:kofi_notice_sent";
    public static final String FILE_NAME = "meza_core.txt";
    public static final String PATH_MARKER = "/gg/meza/";
    public static final  List<String> mezaModIds = List.of(
            "achievementbooks",
            "decked-out-obs",
            "disablechristmaschests",
            "inventorysorter",
            "serverredstoneblock",
            "soundsbegone"
    );
    public static final ScheduledExecutorService EXEC =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "Meza-Notice");
                t.setDaemon(true);
                return t;
            });

    public static LocalDate readDate(Path f) {
        try {
            if (Files.isRegularFile(f))
                return LocalDate.parse(Files.readString(f, StandardCharsets.UTF_8).trim());
        } catch (Exception ignored) {}
        return null;
    }
    public static void writeDate(Path f, LocalDate d) {
        try {
            Files.createDirectories(f.getParent());
            Files.writeString(f, d.toString(), StandardCharsets.UTF_8);
        } catch (IOException ignored) {}
    }

    public static Text reminder(List<String> mezaMods)  {
        Text msg2 = Text.translatable("meza_core.config.support.cta")
                .styled(style -> style
                        .withColor(Formatting.AQUA)
                        .withUnderline(true)
                        .withBold(false)
/*? if >=1.21.5 {*/
                        .withClickEvent(new ClickEvent.OpenUrl(SupportersCore.getSponsorUrl()))
                        .withHoverEvent(new HoverEvent.ShowText(Text.translatable("meza_core.config.support.cta.tooltip")))
/*?} else {*/
                        /*.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SupportersCore.getSponsorUrl().toString()))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("meza_core.config.support.cta.tooltip")))
                       */
/*?}*/
                );

        return Text.translatable("meza_core.reminder", String.join(", ", mezaMods)).append("\n").append(msg2);
    }
}
