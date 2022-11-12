package logalytics.parsing;

import logalytics.model.Entry;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;


public class RegexParser implements LogParser {
    private final RegexSchema schema;

    public RegexParser(RegexSchema schema) {
        this.schema = schema;
    }

    public Entry parse(String line) {
        Map<String, String> grouped = new HashMap<>();
        Matcher matcher = this.schema.matcher(line);
        if (matcher.matches()) {
            for (int i = 0; i < schema.numGroups(); i++) {
                grouped.put(schema.group(i), matcher.group(i + 1));
            }
        }
        return new Entry(grouped, line);
    }
}
