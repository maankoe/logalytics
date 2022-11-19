package logalytics.parsing;

import logalytics.model.Entry;
import logalytics.model.parsing.LogParser;
import logalytics.model.parsing.RegexParser;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static logalytics.model.schema.LogSchema.MESSAGE;
import static logalytics.model.schema.LogSchema.TIMESTAMP;
import static org.assertj.core.api.Assertions.assertThat;

public class TestRegexParser {
    @Test
    public void testParser() {
        String timestampString = "ts";
        String messageString = "msg";
        String logLine = String.format("%s/%s", timestampString, messageString);
        LogParser parser = new RegexParser(Pattern.compile("(.*)/(.*)"), Lists.newArrayList(TIMESTAMP, MESSAGE));
        Entry entry = parser.parse(logLine);
        assertThat(entry.raw()).isEqualTo(logLine);
        assertThat(entry.get(TIMESTAMP)).isEqualTo(timestampString);
        assertThat(entry.get(MESSAGE)).isEqualTo(messageString);
    }
}
