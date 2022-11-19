package logalytics.config;

import logalytics.model.parsing.RegexParser;
import logalytics.model.schema.LogSchema;

import java.util.List;
import java.util.regex.Pattern;

public class LogSchemaBuilder {
    private String filePath;
    private String regex;
    private List<String> groups;

    public LogSchemaBuilder withFilePath(final String filePath) {
        this.filePath = filePath;
        return this;
    }

    public LogSchemaBuilder withRegex(final String regex) {
        this.regex = regex;
        return this;
    }

    public LogSchemaBuilder withGroups(final List<String> groups) {
        this.groups = groups;
        return this;
    }

    public LogSchema build() {
        return new LogSchema(filePath, new RegexParser(Pattern.compile(regex), groups));
    }
}
