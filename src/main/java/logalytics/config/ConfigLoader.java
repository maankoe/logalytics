package logalytics.config;

import java.io.File;
import java.io.IOException;

public interface ConfigLoader<T> {
     T load(File jsonFile) throws IOException, ConfigParseException;

    T load(String configString) throws ConfigParseException;
}
