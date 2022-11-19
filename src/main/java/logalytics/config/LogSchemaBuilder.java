package logalytics.config;

import logalytics.engine.LogReader;
import logalytics.model.parsing.RegexParser;
import logalytics.model.schema.LogSchema;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

    public LogSchema build() throws FileNotFoundException {
        return new LogSchema(new LogReader(new BufferedReader(new FileReader(filePath))), new RegexParser(Pattern.compile(regex), groups));
    }
}
