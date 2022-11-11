package logalytics.parsing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestLogSchema {
    @Test
    public void testPattern() {
        String patternStr = "[a-z]/[A-Z]";
        LogSchema logSchema = new LogSchema(patternStr);
        assertThat(logSchema.pattern().pattern()).isEqualTo(patternStr);
    }
}
