package timbertally.tail;

import org.junit.jupiter.api.BeforeEach;
import patiently.Patiently;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TestTailReader {

    static class CharCollector implements TailHandler {
        private final List<Character> characters = new ArrayList<>();

        @Override
        public void handle(char x) {
            this.characters().add(x);
        }

            public synchronized List<Character> characters() {
            return this.characters;
        }

        @Override
        public void exception(Exception exception) {

        }
    }

    private final int testRetryIntervalMs = 5;
    private final int testRetryMaxMs = 100;
    private final int tailReaderPollInterval = 10;
    private final Path testPath = Path.of("/tmp/test");

    private List<Character> toCharList(String string) {
        return string.chars().mapToObj(x -> (char) x).collect(Collectors.toList());
    }

    @BeforeEach
    public void setUp() throws IOException {
        rmFile();
    }

    @Test
    public void testReadStatic() throws Exception {
        String fileContents = "abc";
        writeToFile(fileContents);
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, tailReaderPollInterval);
        reader.start();
        Patiently.retry(() ->
                assertThat(handler.characters())
                        .containsExactlyElementsOf(toCharList(fileContents))
        ).every(testRetryIntervalMs).until(testRetryMaxMs);
        reader.stop();
    }

    @Test
    public void testReadAppended() throws Exception {
        testTailReaderWithUpdate("abc", "def");
    }

    @Test
    public void testReadFromEmpty() throws Exception {
        testTailReaderWithUpdate("", "abc");
    }

    @Test
    public void testPathRotationSmaller() throws Exception {
        testTailReaderWithOverwrite("abc", "de");
    }

    @Test
    public void testPathRotationSameSize() throws Exception {
        testTailReaderWithOverwrite("abc", "def");
    }

    @Test
    public void testFileGetsRemoved() throws Exception {
        writeToFile("abc");
        TailHandler handler = mock(TailHandler.class);
        TailReader reader = new TailReader(this.testPath, handler, tailReaderPollInterval);
        reader.start();
        rmFile();
        Patiently.retry(() ->
                verify(handler, times(1))
                        .exception(any(NoSuchFileException.class))
        ).every(testRetryIntervalMs).until(testRetryMaxMs);
    }

    private void testTailReaderWithUpdate(
            String startFileContents, String updateFileContents
    ) throws Exception {
        testTailRreader(startFileContents, updateFileContents, false);
    }

    private void testTailReaderWithOverwrite(
            String startFileContents, String updateFileContents
    ) throws Exception {
        testTailRreader(startFileContents, updateFileContents, true);
    }

    private void testTailRreader(
            String startFileContents, String updateFileContents, boolean overwrite
    ) throws IOException {
        writeToFile(startFileContents);
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, tailReaderPollInterval);
        reader.start();
        Patiently.retry(() ->
                assertThat(handler.characters())
                        .containsExactlyElementsOf(toCharList(startFileContents))
        ).every(testRetryIntervalMs).until(testRetryMaxMs);
        if (overwrite) {
            writeToFile(updateFileContents);
        } else {
            appendToFile(updateFileContents);
        }
        Patiently.retry(() ->
                assertThat(handler.characters())
                        .containsExactlyElementsOf(
                                toCharList(startFileContents + updateFileContents)
                        )
        ).every(testRetryIntervalMs).until(testRetryMaxMs);
        reader.stop();
    }

    private void rmFile() throws IOException {
        Files.deleteIfExists(this.testPath);
    }

    private void writeToFile(String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.testPath.toFile()))) {
            writer.write(text);
        }
    }

    private void appendToFile(String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.testPath.toFile(), true))) {
            writer.write(text);
        }
    }
}

