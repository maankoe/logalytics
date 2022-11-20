package logalytics.tail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TestTailReader {
    static class CharCollector implements TailHandler {
        private final List<Character> characters = new ArrayList<>();

        @Override
        public void handle(char x) {
            System.out.println(x);
            this.characters.add(x);
        }

        @Override
        public void exception(Exception exception) {

        }
    }

    private static final int TEST_SLEEP_MS = 100;
    private static final int READER_SLEEP_MS = 10;

    private Path testPath;

    @BeforeEach
    public void setUpClass() {
        this.testPath = Path.of("/tmp/test");
    }

    @Test
    public void testReadStatic() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c');
        reader.stop();
    }

    @Test
    public void testReadAppended() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c');
        appendToFile("def");
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e', 'f');
        reader.stop();
    }

    @Test
    public void testReadFromEmpty() throws Exception {
        writeToFile("");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        appendToFile("abc");
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c');
        reader.stop();
    }

    @Test
    public void testPathRotationSmaller() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c');
        writeToFile("de");
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e');
        reader.stop();
    }

    @Test
    public void testPathRotationSameSize() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c');
        writeToFile("def");
        sleep();
        assertThat(handler.characters).containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e', 'f');
        reader.stop();
    }

    @Test
    public void testFileGetsRemoved() throws Exception {
        writeToFile("abc");
        TailHandler handler = mock(TailHandler.class);
        TailReader reader = new TailReader(this.testPath, handler, READER_SLEEP_MS);
        reader.start();
        Thread.sleep(TEST_SLEEP_MS*2);
        rmFile();
        verify(handler, times(1)).exception(any(NoSuchFileException.class));
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(TEST_SLEEP_MS);
    }

    private void rmFile() throws IOException {
        Files.delete(this.testPath);
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

