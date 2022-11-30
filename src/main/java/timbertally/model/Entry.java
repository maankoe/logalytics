package timbertally.model;

import java.util.Map;

public class Entry {

    private final Map<String, String> grouped;
    private final String raw;

    public Entry(Map<String, String> grouped, String raw) {
        this.grouped = grouped;
        this.raw = raw;
    }

    public String raw() {
        return this.raw;
    }

    public String get(String group) {
        return this.grouped.get(group);
    }
}
