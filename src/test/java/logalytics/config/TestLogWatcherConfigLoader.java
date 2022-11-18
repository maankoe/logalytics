package logalytics.config;

import com.fasterxml.jackson.core.JsonParseException;
import logalytics.config.parsing.ConfigParseException;
import logalytics.config.parsing.LogSchemaConfigParser;
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
        LogSchemaConfigParser schemaParser = mock(LogSchemaConfigParser.class);
        LogSchemaBuilder schemaA = mock(LogSchemaBuilder.class);
        LogSchemaBuilder schemaB = mock(LogSchemaBuilder.class);
        when(schemaParser.parse(any())).thenReturn(schemaA).thenReturn(schemaB);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        LogWatcherConfig config = loader.load("[{}, {}]");
        assertThat(config.schemas()).containsExactlyInAnyOrder(schemaA, schemaB);
    }

    @Test
    public void testLoadWatcherConfigFromFile() throws ConfigParseException, IOException {
        LogSchemaConfigParser schemaParser = mock(LogSchemaConfigParser.class);
        LogSchemaBuilder schemaA = mock(LogSchemaBuilder.class);
        LogSchemaBuilder schemaB = mock(LogSchemaBuilder.class);
        when(schemaParser.parse(any())).thenReturn(schemaA).thenReturn(schemaB);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        InputStream configStream = configInputStream("[{}, {}]");
        LogWatcherConfig config = loader.load(configStream);
        assertThat(config.schemas()).containsExactlyInAnyOrder(schemaA, schemaB);
    }

    @Test
    public void testLoadBadJson() {
        LogSchemaConfigParser schemaParser = mock(LogSchemaConfigParser.class);
        LogWatcherConfigLoader loader = new LogWatcherConfigLoader(schemaParser);
        Exception exception = catchException(() -> loader.load("[{]"));
        assertThat(exception).isInstanceOf(ConfigParseException.class);
        assertThat(exception.getCause()).isInstanceOf(JsonParseException.class);
        assertThat(exception.getMessage()).contains("Unexpected close");
    }

    private InputStream configInputStream(String configString) {
        return new ByteArrayInputStream(configString.getBytes(StandardCharsets.UTF_8));
    }
}
