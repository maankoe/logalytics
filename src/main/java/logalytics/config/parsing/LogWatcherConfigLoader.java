package logalytics.config.parsing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logalytics.config.LogSchemaBuilder;
import logalytics.config.LogWatcherConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogWatcherConfigLoader implements ConfigLoader<LogWatcherConfig> {
    private final LogSchemaConfigParser logSchemaConfigParser;

    public LogWatcherConfigLoader(LogSchemaConfigParser logSchemaConfigParser) {
        this.logSchemaConfigParser = logSchemaConfigParser;
    }

    public LogWatcherConfig load(InputStream inputStream) throws IOException, ConfigParseException {
        return this.fromJson(new ObjectMapper().readTree(inputStream));
    }

    public LogWatcherConfig load(String jsonString) throws ConfigParseException {
        try {
            return this.fromJson(new ObjectMapper().readTree(jsonString));
        } catch (JsonProcessingException ex) {
            throw new ConfigParseException(ex);
        }
    }

    private LogWatcherConfig fromJson(JsonNode root) throws ConfigParseException {
        Iterator<JsonNode> schemaNodeIterator = root.elements();
        List<LogSchemaBuilder> schemas = new ArrayList<>();
        while (schemaNodeIterator.hasNext()) {
            schemas.add(this.logSchemaConfigParser.parse(schemaNodeIterator.next()));
        }
        return new LogWatcherConfig(schemas);
    }
}
