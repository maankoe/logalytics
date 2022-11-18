package logalytics.config.parsing;

import com.fasterxml.jackson.databind.JsonNode;
import logalytics.config.LogSchemaBuilder;
import logalytics.parsing.LogSchema;

import java.util.ArrayList;
import java.util.List;

public class LogSchemaConfigParser implements JsonNodeParser<LogSchemaBuilder> {
    public static final String FILE_PATH = "file-path";
    public static final String REGEX = "regex";
    public static final String GROUPS = "groups";

    public LogSchemaBuilder parse(JsonNode node) throws ConfigParseException {
        return LogSchema.builder()
                .withFilePath(getValueNode(node, FILE_PATH).asText())
                .withRegex(getValueNode(node, REGEX).asText())
                .withGroups(getStringList(getValueNode(node, GROUPS)));
    }

    private List<String> getStringList(JsonNode node) {
        List<String> values = new ArrayList<>();
        node.iterator().forEachRemaining(x -> values.add(x.asText()));
        return values;
    }

    private JsonNode getValueNode(JsonNode node, String key) throws ConfigParseException {
        JsonNode valueNode = node.findValue(key);
        if (valueNode != null) {
            return node.findValue(key);
        } else {
            throw new ConfigParseException(String.format("Missing %s in %s", key, node.toPrettyString()));
        }
    }
}
