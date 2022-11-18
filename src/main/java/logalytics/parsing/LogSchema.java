package logalytics.parsing;

import logalytics.config.LogSchemaBuilder;

import java.util.List;
import java.util.regex.Pattern;

public class LogSchema {
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String MESSAGE = "MESSAGE";

    private final String filePath;
    private final Pattern regex;
    private final List<String> groups;

    public static LogSchemaBuilder builder() {
        return new LogSchemaBuilder();
    }

    public LogSchema(String filePath, Pattern regex, List<String> groups) {
        this.filePath = filePath;
        this.regex = regex;
        this.groups = groups;
    }
}
