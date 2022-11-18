package logalytics.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import logalytics.config.parsing.ConfigParseException;
import logalytics.config.parsing.LogSchemaConfigParser;
import logalytics.parsing.LogSchema;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static logalytics.config.parsing.LogSchemaConfigParser.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestLogSchemaBuilderParser {
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
        LogSchemaBuilder schemaBuilder = spy(new LogSchemaBuilder());
        try (MockedStatic<LogSchema> utilities = mockStatic(LogSchema.class)) {
            utilities.when(LogSchema::builder).thenReturn(schemaBuilder);
            LogSchema schema = new LogSchemaConfigParser().parse(schemaNode).build();
            verify(schemaBuilder).withFilePath(theFilePath);
            verify(schemaBuilder).withRegex(theRegex);
            verify(schemaBuilder).withGroups(Lists.newArrayList(groupA, groupB));
        }
    }

    @Test
    public void testLogSchemaBadNodeException() {
        ArrayNode badNode = new ObjectMapper().createArrayNode();
        LogSchemaConfigParser parser = new LogSchemaConfigParser();
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
        LogSchemaConfigParser parser = new LogSchemaConfigParser();
        Exception exception = catchException(() -> parser.parse(schemaNode));
        assertThat(exception).isInstanceOf(ConfigParseException.class);
        assertThat(exception.getMessage())
                .contains("Missing")
                .contains(schemaNode.toPrettyString());
    }
}
