package logalytics.parsing;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSchema {
    private final Pattern pattern;
    private final List<String> groups;

    public RegexSchema(String regex, List<String> groups) {
        this.pattern = Pattern.compile(regex);
        this.groups = groups;
    }

    public Matcher matcher(String line) {
        return this.pattern.matcher(line);
    }

    public String group(int i) {
        return this.groups.get(i);
    }

    public int numGroups() {
        return this.groups.size();
    }
}
