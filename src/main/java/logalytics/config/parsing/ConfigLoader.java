package logalytics.config.parsing;

import java.io.IOException;
import java.io.InputStream;

public interface ConfigLoader<T> {
    T load(InputStream inputStream) throws IOException, ConfigParseException;

    T load(String configString) throws ConfigParseException;
}
