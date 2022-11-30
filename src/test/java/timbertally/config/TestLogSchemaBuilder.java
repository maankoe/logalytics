package timbertally.config;

import timbertally.model.schema.LogSchema;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;


public class TestLogSchemaBuilder {
    @Test
    public void testLogSchemaBuilderFileNotFound() {
        String filePath = "aFilePathThatDoesNotExist";
        String regex = "regex";
        LogSchemaBuilder builder = LogSchema.builder()
                .withFilePath(filePath)
                .withRegex(regex);
        Exception exception = catchException(builder::build);
        assertThat(exception)
                .isInstanceOf(FileNotFoundException.class)
                .hasMessageContaining(filePath);
    }
}
