package gg.meza.telemetry;

import com.posthog.java.PostHog;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Telemetry {
    private final String OS_NAME = System.getProperty("os.name");
    private final String JAVA_VERSION = System.getProperty("java.version");

    // Replaced by Stonecutter for the active Minecraft version
    private final String MC_VERSION = "CURRENT_VERSION";

    // Replaced by Stonecutter for the active mod loader
    private final String LOADER = "CURRENT_LOADER";

    private final PostHog posthog;
    private final String modVersion;
    private final String sessionId = String.valueOf(System.currentTimeMillis());

    Telemetry(String apiKey, String modVersion, Logger logger) {

        this.modVersion = modVersion;
        String POSTHOG_HOST = "https://eu.posthog.com";

        this.posthog = new PostHog.Builder(apiKey)
                .host(POSTHOG_HOST)
                .logger(new MinecraftLogger(logger))
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
    }

    public void sendEvent(String username, String event, Map<String, Object> details) {
        Map<String, Object> baseProps = new HashMap<>(Map.of(
                "Minecraft Version", MC_VERSION,
                "OS", OS_NAME,
                "ModVersion", modVersion,
                "Local Time", new java.util.Date().toString(),
                "Java Version", JAVA_VERSION,
                "Loader", LOADER,
                "Session ID", sessionId
        ));

        baseProps.putAll(details);
        this.posthog.capture(getUUID(username), event, baseProps);
    }

    public static String getUUID(String username) {
        return DigestUtils.sha256Hex(username);
    }

    private void shutdown() {
        posthog.shutdown();
    }
}
