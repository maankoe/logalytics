package timbertally.model.schema;

import timbertally.config.LogSchemaBuilder;
import timbertally.engine.LogReader;
import timbertally.model.Entry;
import timbertally.model.parsing.LogParser;
import reactor.core.publisher.Flux;

public class LogSchema {
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String MESSAGE = "MESSAGE";

    private final LogReader reader;
    private final LogParser parser;

    public static LogSchemaBuilder builder() {
        return new LogSchemaBuilder();
    }

    public LogSchema(LogReader reader, LogParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public Entry parse(String raw) {
        return this.parser.parse(raw);
    }

    public Flux<String> producer() {
        return this.reader.producer();
    }
}
