package logalytics.parsing;

import logalytics.model.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestParser {
    @Test
    public void testParser() {
        String logLine = "date/module/message";
        LogSchema logSchema = new LogSchema("(.*)/(.*)/(.*)");
        Parser parser = new Parser(logSchema);
        Entry entry = parser.parse(logLine);
        assertThat(entry.raw()).isEqualTo(logLine);
    }
}
