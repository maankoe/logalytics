package logalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import logalytics.config.ConfigParseException;
import logalytics.config.LogWatcherConfig;
import logalytics.config.LogWatcherConfigLoader;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static logalytics.config.LogWatcherConfigLoader.FILE_PATH;
import static org.assertj.core.api.Assertions.*;

public class TestLogWatcherConfig {
    @Test
    public void testJsonLogWatcherConfig() throws ConfigParseException {
        String theFilePath = "the-file-path.log";
        String jsonString = String.format("[{\"%s\": \"%s\"}]", FILE_PATH, theFilePath);
        LogWatcherConfigLoader parser = new LogWatcherConfigLoader();
        LogWatcherConfig config = parser.load(jsonString);
        assertThat(config.schemas().get(0).fileName()).isEqualTo("the-file-path.log");
    }
    
    @Test
    public void testJsonLogWatcherConfigParseException() {
        String jsonString = String.format("[{\"file-\": \"asdf\"}]");
        LogWatcherConfigLoader parser = new LogWatcherConfigLoader();
        ConfigParseException exception = catchThrowableOfType(() -> parser.load(jsonString), ConfigParseException.class);
        assertThat(exception.getMessage())
                .contains(FILE_PATH)
                .contains("asdf");
    }
}
