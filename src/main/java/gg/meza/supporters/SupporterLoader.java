package gg.meza.supporters;

import com.google.gson.Gson;
import net.minecraft.network.chat.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.mojang.text2speech.Narrator.LOGGER;

public class SupporterLoader {
    private static final Gson GSON = new Gson().newBuilder().create();
    private static final long REFRESH_INTERVAL_MS = 5 * 60 * 1000; // 5 minutes
    private static final long MAX_REFRESH_BACKOFF_MS = 5 * 60 * 60 * 1000; // 5 hours

    private final Timer refreshTimer = new Timer("Supporter-Refresh", true);

    private Supporters supporters = new Supporters();
    private long nextRefreshDelayMs = REFRESH_INTERVAL_MS;
    private boolean refreshScheduled = false;

    public void preload() {
        new Thread(this::fetchAndScheduleNextRefresh, "Supporter-Fetcher").start();
    }

    private void fetchAndScheduleNextRefresh() {
        boolean refreshed = fetchSupporters();
        updateNextRefreshDelay(refreshed);
        scheduleRefresh();
    }

    private synchronized void updateNextRefreshDelay(boolean refreshed) {
        if (refreshed) {
            nextRefreshDelayMs = REFRESH_INTERVAL_MS;
            return;
        }

        nextRefreshDelayMs = Math.min(nextRefreshDelayMs * 2, MAX_REFRESH_BACKOFF_MS);
    }

    private synchronized void scheduleRefresh() {
        if (refreshScheduled) {
            return;
        }

        refreshScheduled = true;

        refreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (SupporterLoader.this) {
                    refreshScheduled = false;
                }

                new Thread(SupporterLoader.this::fetchAndScheduleNextRefresh, "Supporter-Refresh-Fetch").start();
            }
        }, nextRefreshDelayMs);
    }

    private boolean fetchSupporters() {
        String url = "https://vsbmeza3.com/supporters.json";
        try (InputStream input = new java.net.URL(url).openStream();
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {

            supporters = GSON.fromJson(reader, Supporters.class);
            return true;

        } catch (Exception e) {
            LOGGER.debug("Failed to fetch supporter list: {}. Retrying in {} minutes.",
                    e.getMessage(),
                    (long) Math.ceil((double) nextRefreshDelayMs / 60_000)
            );
            supporters = new Supporters(); // fallback to empty
            return false;
        }
    }

    public List<TierEntry> getTiers() {
        return supporters.tiers;
    }

    public Component getNewSupportersText() {
        return Supporters.asDistinguishedList((supporters.joined7Days.stream().map(m -> Component.literal(m.name).append(" ").append(Component.literal(m.emoji))).toList()));
    }
}
