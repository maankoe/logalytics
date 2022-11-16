package logalytics.config;

public class LogSchema {
    private final String fileName;

    public LogSchema(String fileName) {
        this.fileName = fileName;
    }

    public String fileName() {
        return fileName;
    }
}
