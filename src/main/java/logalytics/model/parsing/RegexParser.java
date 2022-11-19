package logalytics.model.parsing;

import logalytics.model.Entry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexParser implements LogParser {
    private final Pattern pattern;
    private final List<String> groups;

    public RegexParser(Pattern pattern, List<String> groups) {
        this.pattern = pattern;
        this.groups = groups;
    }

    public Entry parse(String line) {
        Map<String, String> grouped = new HashMap<>();
        Matcher matcher = this.matcher(line);
        if (matcher.matches()) {
            for (int i = 0; i < groups.size(); i++) {
                grouped.put(groups.get(i), matcher.group(i + 1));
            }
        }
        return new Entry(grouped, line);
    }

    private Matcher matcher(String line) {
        return this.pattern.matcher(line);
    }
}
