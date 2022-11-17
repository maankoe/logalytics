package logalytics.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogWatcherConfigLoader implements ConfigLoader<LogWatcherConfig> {

    public LogWatcherConfig load(File jsonFile) throws IOException, ConfigParseException {
        return this.fromJson(new ObjectMapper().readTree(jsonFile));
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
        List<LogSchema> schemas = extractLogSchemas(schemaNodeIterator);
        return new LogWatcherConfig(schemas);
    }

    private List<LogSchema> extractLogSchemas(Iterator<JsonNode> schemaNodeIterator) throws ConfigParseException {
        List<LogSchema> schemas = new ArrayList<>();
        while (schemaNodeIterator.hasNext()) {
            JsonNode node = schemaNodeIterator.next();

        }
        return schemas;
    }


}
