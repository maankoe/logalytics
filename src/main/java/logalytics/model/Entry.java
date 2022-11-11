package logalytics.model;

public class Entry {

    private final String raw;

    public Entry(String raw) {
        this.raw = raw;
    }

    public String raw() {
        return this.raw;
    }
}
