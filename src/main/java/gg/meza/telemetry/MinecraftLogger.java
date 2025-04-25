package gg.meza.telemetry;

import com.posthog.java.PostHogLogger;
import org.apache.logging.log4j.Logger;

public class MinecraftLogger implements PostHogLogger {

    private final Logger logger;

    public  MinecraftLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String message) {
        this.logger.debug(message);
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        this.logger.error(message, throwable);
    }
}
