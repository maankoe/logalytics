package logalytics;

import logalytics.engine.LogReader;
import logalytics.model.Entry;
import logalytics.parsing.NoParser;
import logalytics.parsing.LogParser;
import logalytics.parsing.RegexParser;
import logalytics.parsing.RegexSchema;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static logalytics.parsing.LogSchema.MESSAGE;
import static logalytics.parsing.LogSchema.TIMESTAMP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestLogReader {
    public void testReadEntry() throws IOException {
        String logA = "A";
        String logB = "B";
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(logA).thenReturn(logB);
        LogReader logReader = new LogReader(bufferedReader, new NoParser());
        assertThat(logReader.readEntry().raw()).isEqualTo(logA);
        assertThat(logReader.readEntry().raw()).isEqualTo(logB);
    }

    @Test
    public void testParseEntry() throws IOException {
        String timestampString = "ts";
        String messageString = "msg";
        String logLine = String.format("%s/%s", timestampString, messageString);
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(logLine);
        RegexSchema schema = new RegexSchema("(.*)/(.*)", Lists.newArrayList(TIMESTAMP, MESSAGE));
        LogParser parser = new RegexParser(schema);
        LogReader logReader = new LogReader(bufferedReader, parser);
        Entry entry = logReader.readEntry();
        assertThat(entry.raw()).isEqualTo(logLine);
        assertThat(entry.get(TIMESTAMP)).isEqualTo(timestampString);
        assertThat(entry.get(MESSAGE)).isEqualTo(messageString);
    }
}
