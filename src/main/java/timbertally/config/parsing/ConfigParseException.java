package timbertally.config.parsing;

public class ConfigParseException extends Exception {
    public ConfigParseException(Exception cause) {
        super(cause);
    }

    public ConfigParseException(String message) {
        super(message);
    }
}
