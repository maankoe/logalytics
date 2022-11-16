package logalytics.config;

public class ConfigParseException extends Exception {
    public ConfigParseException(Exception cause) {
        super(cause);
    }

    public ConfigParseException(String message) {
        super(message);
    }
}
