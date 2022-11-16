package logalytics.config;

import java.util.List;

public class LogWatcherConfig {

    private final List<LogSchema> schemas;

    public LogWatcherConfig(List<LogSchema> schemas) {
        this.schemas = schemas;
    }

    public List<LogSchema> schemas() {
        return this.schemas;
    }
}
