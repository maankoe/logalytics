package logalytics.config;

import java.util.List;
import java.util.regex.Pattern;

public class LogSchema {
    private final String fileName;
    private final String regex;
    private final List<String> groups;

    public LogSchema(String fileName, String regex, List<String> groups) {
        this.fileName = fileName;
        this.regex = regex;
        this.groups = groups;
    }

    public String fileName() {
        return fileName;
    }

    public Pattern regex() {
        return Pattern.compile(this.regex);
    }

    public List<String> groups() {
        return this.groups;
    }
}
