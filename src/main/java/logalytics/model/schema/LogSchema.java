package logalytics.model.schema;

import logalytics.config.LogSchemaBuilder;
import logalytics.model.Entry;
import logalytics.model.parsing.LogParser;

public class LogSchema {
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String MESSAGE = "MESSAGE";

    private final String filePath;
    private final LogParser parser;

    public static LogSchemaBuilder builder() {
        return new LogSchemaBuilder();
    }

    public LogSchema(String filePath, LogParser parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    public Entry parse(String raw) {
        return this.parser.parse(raw);
    }
}
