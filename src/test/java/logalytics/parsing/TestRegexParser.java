package logalytics.parsing;

import logalytics.model.Entry;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static logalytics.parsing.LogSchema.MESSAGE;
import static logalytics.parsing.LogSchema.TIMESTAMP;
import static org.assertj.core.api.Assertions.assertThat;

public class TestRegexParser {
    @Test
    public void testParser() {
        String timestampString = "ts";
        String messageString = "msg";
        String logLine = String.format("%s/%s", timestampString, messageString);
        RegexSchema schema = new RegexSchema(
                "(.*)/(.*)", Lists.newArrayList(TIMESTAMP, MESSAGE)
        );
        LogParser parser = new RegexParser(schema);
        Entry entry = parser.parse(logLine);
        assertThat(entry.raw()).isEqualTo(logLine);
        assertThat(entry.get(TIMESTAMP)).isEqualTo(timestampString);
        assertThat(entry.get(MESSAGE)).isEqualTo(messageString);
    }
}
