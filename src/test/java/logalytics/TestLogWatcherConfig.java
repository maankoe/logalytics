package logalytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import logalytics.config.*;
import org.junit.jupiter.api.Test;

import static logalytics.config.LogSchemaConfigParser.*;
import static org.assertj.core.api.Assertions.*;

public class TestLogWatcherConfig {
    @Test
    public void testJsonLogWatcherConfig() throws ConfigParseException, JsonProcessingException {
        String theFilePath = "the-file-path.log";
        String theRegex = "regex";
        String groupA = "group_a";
        String groupB = "group_b";
        String fileJson = String.format("\"%s\": \"%s\"", FILE_PATH, theFilePath);
        String regexJson = String.format("\"%s\": \"%s\"", REGEX, theRegex);
        String groupsJson = String.format("\"%s\": [\"%s\", \"%s\"]", GROUPS, groupA, groupB);
        String jsonString = String.format("{%s, %s, %s}", fileJson, regexJson, groupsJson);
        LogSchemaConfigParser parser = new LogSchemaConfigParser();
        LogSchema schema = parser.parse(new ObjectMapper().readTree(jsonString));
        assertThat(schema.fileName()).isEqualTo(theFilePath);
        assertThat(schema.regex().pattern()).isEqualTo(theRegex);
        assertThat(schema.groups()).containsExactly(groupA, groupB);
    }

    @Test
    public void testJsonLogWatcherConfigParseException() throws JsonProcessingException {
        String jsonString = String.format("[{\"file-\": \"asdf\"}]");
        LogSchemaConfigParser parser = new LogSchemaConfigParser();
        JsonNode node = new ObjectMapper().readTree(jsonString);
        ConfigParseException exception = catchThrowableOfType(() -> parser.parse(node), ConfigParseException.class);
        assertThat(exception.getMessage())
                .contains(FILE_PATH)
                .contains("\"file-\" : \"asdf\"");
    }
}
