package gg.meza.supporters.reminder;

import gg.meza.supporters.SupportersCore;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;

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

    public static Component reminder(List<String> mezaMods)  {
        Component msg2 = Component.translatable("meza_core.config.support.cta")
                .withStyle(style -> style
                        .withColor(ChatFormatting.AQUA)
                        .withUnderlined(true)
                        .withBold(false)
                        .withClickEvent(new ClickEvent.OpenUrl(SupportersCore.getSponsorUrl()))
                        .withHoverEvent(new HoverEvent.ShowText(Component.translatable("meza_core.config.support.cta.tooltip")))

                );

        return Component.translatable("meza_core.reminder", String.join(", ", mezaMods)).append("\n").append(msg2);
    }
}
