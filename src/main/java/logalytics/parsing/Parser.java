package logalytics.parsing;

import logalytics.model.Entry;


public class Parser {
    private final LogSchema logSchema;

    public Parser(LogSchema logSchema) {
        this.logSchema = logSchema;
    }

    public Entry parse(String logLine) {
        return new Entry(logLine);
    }
}
