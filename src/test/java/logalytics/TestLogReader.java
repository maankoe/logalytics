package logalytics;

import logalytics.engine.LogReader;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestLogReader {
    @Test
    public void testReadLineDelimitedEntry() throws IOException {
        String logA = "A";
        String logB = "B";
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(logA).thenReturn(logB);
        LogReader logReader = new LogReader(bufferedReader);
        StepVerifier.create(logReader.producer())
                .expectNext(logA, logB);
    }
//
//    @Test
//    public void testParseEntry() throws IOException {
//        String timestampString = "ts";
//        String messageString = "msg";
//        String logLine = String.format("%s/%s", timestampString, messageString);
//        BufferedReader bufferedReader = mock(BufferedReader.class);
//        when(bufferedReader.readLine()).thenReturn(logLine);
//        LogParser parser = new RegexParser(Pattern.compile("(.*)/(.*)"), Lists.newArrayList(TIMESTAMP, MESSAGE));
//        LogReader logReader = new LogReader(bufferedReader, parser);
//        Entry entry = logReader.readEntry();
//        assertThat(entry.raw()).isEqualTo(logLine);
//        assertThat(entry.get(TIMESTAMP)).isEqualTo(timestampString);
//        assertThat(entry.get(MESSAGE)).isEqualTo(messageString);
//    }
}
