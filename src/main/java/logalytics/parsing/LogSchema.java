package logalytics.parsing;

import java.util.regex.Pattern;

public class LogSchema {
    private final Pattern pattern;

    public LogSchema(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern pattern() {
        return this.pattern;
    }
}
