package logalytics.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import logalytics.config.*;
import logalytics.config.parsing.ConfigParseException;
import logalytics.config.parsing.LogSchemaParser;
import org.junit.jupiter.api.Test;

import static logalytics.config.parsing.LogSchemaParser.*;
import static org.assertj.core.api.Assertions.*;

public class TestLogSchemaParser {
    @Test
    public void testLogSchemaParse() throws ConfigParseException {
        String theFilePath = "the-file-path.log";
        String theRegex = "regex";
        String groupA = "group_a";
        String groupB = "group_b";
        ObjectNode schemaNode = new ObjectMapper().createObjectNode()
                .put(FILE_PATH, theFilePath)
                .put(REGEX, theRegex);
        schemaNode.putArray(GROUPS).add(groupA).add(groupB);
        LogSchema schema = new LogSchemaParser().parse(schemaNode);
        assertThat(schema.fileName()).isEqualTo(theFilePath);
        assertThat(schema.regex().pattern()).isEqualTo(theRegex);
        assertThat(schema.groups()).containsExactly(groupA, groupB);
    }

    @Test
    public void testLogSchemaBadNodeException() {
        ArrayNode badNode = new ObjectMapper().createArrayNode();
        LogSchemaParser parser = new LogSchemaParser();
        Exception exception = catchException(() -> parser.parse(badNode));
        assertThat(exception).isInstanceOf(ConfigParseException.class);
        assertThat(exception.getMessage())
                .contains("Missing")
                .contains(badNode.toPrettyString());
    }

    @Test
    public void testLogSchemaBadKeyException() throws JsonProcessingException {
        ObjectNode schemaNode = new ObjectMapper().createObjectNode();
        schemaNode.put("file-", "asdf");
        LogSchemaParser parser = new LogSchemaParser();
        Exception exception = catchException(() -> parser.parse(schemaNode));
        assertThat(exception).isInstanceOf(ConfigParseException.class);
        assertThat(exception.getMessage())
                .contains("Missing")
                .contains(schemaNode.toPrettyString());
    }
}
