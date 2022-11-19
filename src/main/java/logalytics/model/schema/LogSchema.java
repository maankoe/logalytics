package logalytics.model.schema;

import logalytics.config.LogSchemaBuilder;
import logalytics.engine.LogReader;
import logalytics.model.Entry;
import logalytics.model.parsing.LogParser;
import reactor.core.publisher.Flux;

import java.io.*;

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
