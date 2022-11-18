package logalytics.config;

import java.util.List;

public class LogWatcherConfig {

    private final List<LogSchemaBuilder> schemas;

    public LogWatcherConfig(List<LogSchemaBuilder> schemas) {
        this.schemas = schemas;
    }

    public List<LogSchemaBuilder> schemas() {
        return this.schemas;
    }
}
