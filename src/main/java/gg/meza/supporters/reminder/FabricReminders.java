/*? if fabric {*/
package gg.meza.supporters.reminder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.ObjectShare;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static gg.meza.supporters.reminder.Reminders.*;

@Environment(EnvType.CLIENT)
public class FabricReminders {

    public static void initialize() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ObjectShare share = FabricLoader.getInstance().getObjectShare();
            if (share.putIfAbsent(SENT_KEY, Boolean.TRUE) != null) return;
            Path stampFile = FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
            LocalDate last = readDate(stampFile);
            if (last != null && last.isAfter(LocalDate.now().minusDays(30))) return;

            EXEC.schedule(() -> MinecraftClient.getInstance().execute(() -> {
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(Reminders.reminder(findMezaMods()), false);
                    writeDate(stampFile, LocalDate.now());
                }
            }), 30, TimeUnit.SECONDS);

        });
    }

    public static List<String> findMezaMods() {
        return FabricLoader.getInstance()
                .getAllMods()
                .stream()
                .filter(FabricReminders::isMeza)
                .map(ModContainer::getMetadata)
                .map(ModMetadata::getName)
                .collect(Collectors.toList());
    }

    private static boolean isMeza(ModContainer c) {
        ModMetadata meta = c.getMetadata();

        if (mezaModIds.contains(meta.getId())) {
            return true;
        }

        return false;
    }

}

/*?}*/
