/*? if neoforge {*/
/*package gg.meza.supporters.reminder;

import net.minecraft.client.MinecraftClient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.moddiscovery.ModFileInfo;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.language.IModInfo;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mojang.text2speech.Narrator.LOGGER;
import static gg.meza.core.MezaCore.MOD_ID;
import static gg.meza.supporters.reminder.Reminders.*;
import static gg.meza.supporters.reminder.StoneCutterMagic.REPLACE_ORIGINAL;

public class NeoforgeReminders {
    private static volatile boolean sent;

    @SubscribeEvent
    private static void onLoggedIn(ClientPlayerNetworkEvent.LoggingIn e) {
        if (sent) return;   // once per session
        sent = true;

        Path stampFile = FMLPaths.CONFIGDIR.get().resolve(FILE_NAME);
        LocalDate last = readDate(stampFile);
        if (last != null && last.isAfter(LocalDate.now().minusDays(30))) return;

        Set<String> meza = collectMezaNames();

        if (meza.isEmpty()) return;

        EXEC.schedule(() -> MinecraftClient.getInstance().execute(() -> {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.sendMessage(Reminders.reminder(meza.stream().toList()), false);
                writeDate(stampFile, LocalDate.now());
            }
        }), 30, TimeUnit.SECONDS);
    }

    public static Set<String> collectMezaNames() {
        return ModList.get().getMods().stream()
                .filter(m -> mezaModIds.contains(m.getModId()))
                .map(IModInfo::getDisplayName)
                .collect(Collectors.toUnmodifiableSet());
    }

}

*//*?}*/

