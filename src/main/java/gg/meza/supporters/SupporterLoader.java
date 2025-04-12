package gg.meza.supporters;

import com.google.gson.Gson;
import net.minecraft.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.mojang.text2speech.Narrator.LOGGER;

public class SupporterLoader {
    private static final Gson GSON = new Gson().newBuilder().create();
    private static final long REFRESH_INTERVAL_MS = 5 * 60 * 1000; // 60 minutes

    private final Timer refreshTimer = new Timer("Supporter-Refresh", true);


    private Supporters supporters = new Supporters();

    public void preload() {
        new Thread(this::fetchSupporters, "Supporter-Fetcher").start();

        refreshTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Thread(SupporterLoader.this::fetchSupporters, "Supporter-Refresh-Fetch").start();
            }
        }, REFRESH_INTERVAL_MS, REFRESH_INTERVAL_MS);
    }

    private void fetchSupporters() {
        String url = "https://vsbmeza3.com/supporters.json";
        try (InputStream input = new java.net.URL(url).openStream();
             InputStreamReader reader = new InputStreamReader(input)) {

            supporters = GSON.fromJson(reader, Supporters.class);

        } catch (IOException e) {
            LOGGER.error("Failed to fetch supporter list: {}", e.getMessage());
            supporters = new Supporters(); // fallback to empty
        }
    }

    public List<TierEntry> getTiers() {
        return supporters.tiers;
    }

    public Text getNewSupportersText() {
        return Supporters.asDistinguishedList((supporters.joined7Days.stream().map(m -> m.name + " " + m.emoji).toList()));
    }
}
