package logalytics;

import logalytics.engine.LogReader;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.function.Predicate;


public class TestLogReader {
    private static final String NEWLINE = System.lineSeparator();
    @Test
    public void testReadNewlineDelimitedEntry() {
        String logA = "A";
        String logB = "B";
        String logContents = String.join(NEWLINE, logA, logB);
        BufferedReader bufferedReader = new BufferedReader(new StringReader(logContents));
        LogReader logReader = new LogReader(bufferedReader);
        StepVerifier.create(logReader.producer())
                .expectNext(logA, logB)
                .verifyComplete();
    }

    @Test
    public void testReadCommaDelimitedEntry() {
        String delimiter = ",";
        Predicate<String> newEntryPredicate = x -> x.startsWith(delimiter);
        String logA = "A";
        String logB = "B";
        String logC = delimiter + "C";
        String logContents = String.join(NEWLINE, logA, logB, logC);
        BufferedReader bufferedReader = new BufferedReader(new StringReader(logContents));
        LogReader logReader = new LogReader(bufferedReader, newEntryPredicate);
        StepVerifier.create(logReader.producer())
                .expectNext(String.join(NEWLINE, logA, logB), logC)
                .verifyComplete();
    }
}
