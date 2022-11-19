package logalytics.config;

import logalytics.model.schema.LogSchema;
import org.junit.jupiter.api.Test;


public class TestLogSchemaBuilder {
    @Test
    public void testLogSchemaBuilder() {
        String filePath = "filePath";
        String regex = "regex";
        LogSchema schema = LogSchema.builder()
                .withFilePath(filePath)
                .withRegex(regex)
                .build();
    }
}
