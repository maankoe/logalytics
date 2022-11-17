package logalytics.config;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogSchemaConfigParser {
    public static final String FILE_PATH = "file-path";
    public static final String REGEX = "regex";
    public static final String GROUPS = "groups";

    public LogSchema parse(JsonNode node) throws ConfigParseException {
        return new LogSchema(
                getValueNode(node, FILE_PATH).asText(),
                getValueNode(node, REGEX).asText(),
                getStringList(getValueNode(node, GROUPS))
        );
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
