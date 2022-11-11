package logalytics;

import logalytics.engine.LogReader;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestLogReader {
    @Test
    public void testReadEntry() throws IOException {
        String logA = "A";
        String logB = "B";
        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(logA).thenReturn(logB);
        LogReader logReader = new LogReader(bufferedReader);
        assertThat(logReader.readEntry().raw()).isEqualTo(logA);
        assertThat(logReader.readEntry().raw()).isEqualTo(logB);
    }
}
