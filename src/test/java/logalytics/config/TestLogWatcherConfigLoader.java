package logalytics.config;

import com.fasterxml.jackson.core.JsonParseException;
import logalytics.config.parsing.ConfigParseException;
import logalytics.config.parsing.LogSchemaParser;
import logalytics.config.parsing.LogWatcherConfigLoader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLogWatcherConfigLoader {
    @Test
    public void testLoadWatcherConfigFromString() throws ConfigParseException {
        LogSchemaParser schemaParser = mock(LogSchemaParser.class);
        LogSchema schemaA = mock(LogSchema.class);
        LogSchema schemaB = mock(LogSchema.class);
        when(schemaParser.parse(any())).thenReturn(schemaA).thenReturn(schemaB);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        LogWatcherConfig config = loader.load("[{}, {}]");
        assertThat(config.schemas()).containsExactlyInAnyOrder(schemaA, schemaB);
    }

    @Test
    public void testLoadWatcherConfigFromFile() throws ConfigParseException, IOException {
        LogSchemaParser schemaParser = mock(LogSchemaParser.class);
        LogSchema schemaA = mock(LogSchema.class);
        LogSchema schemaB = mock(LogSchema.class);
        when(schemaParser.parse(any())).thenReturn(schemaA).thenReturn(schemaB);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        InputStream configStream = configInputStream("[{}, {}]");
        LogWatcherConfig config = loader.load(configStream);
        assertThat(config.schemas()).containsExactlyInAnyOrder(schemaA, schemaB);
    }

    @Test
    public void testLoadBadJson() throws ConfigParseException {
        LogSchemaParser schemaParser = mock(LogSchemaParser.class);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        Exception exception = catchException(() -> loader.load("[{]"));
        assertThat(exception).isInstanceOf(ConfigParseException.class);
        assertThat(exception.getCause()).isInstanceOf(JsonParseException.class);
        assertThat(exception.getMessage()).contains("Unexpected close");
    }

    private InputStream configInputStream(String configString) {
        return new ByteArrayInputStream("[{}, {}]".getBytes(StandardCharsets.UTF_8));
    }
}
