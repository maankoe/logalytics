package timbertally.tail;

import timbertally.tail.utils.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

    private final int readerWaitMs = 10;
    private final Path testPath = Path.of("/tmp/test");;

    @Test
    public void testReadStatic() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c')
        ).test();
        reader.stop();
    }

    @Test
    public void testReadAppended() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c')
        ).test();
        appendToFile("def");
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e', 'f')
        ).test();
        reader.stop();
    }

    @Test
    public void testReadFromEmpty() throws Exception {
        writeToFile("");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        appendToFile("abc");
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c')
        ).test();
        reader.stop();
    }

    @Test
    public void testPathRotationSmaller() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c')
        );
        writeToFile("de");
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e')
        );
        reader.stop();
    }

    @Test
    public void testPathRotationSameSize() throws Exception {
        writeToFile("abc");
        CharCollector handler = new CharCollector();
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c')
        ).test();
        writeToFile("def");
        Assertions.delay(() -> assertThat(handler.characters())
                .containsExactlyInAnyOrder('a', 'b', 'c', 'd', 'e', 'f')
        ).test();
        reader.stop();
    }

    @Test
    public void testFileGetsRemoved() throws Exception {
        writeToFile("abc");
        TailHandler handler = mock(TailHandler.class);
        TailReader reader = new TailReader(this.testPath, handler, readerWaitMs);
        reader.start();
        rmFile();
        Assertions.delay(() -> verify(handler, times(1))
                .exception(any(NoSuchFileException.class))
        ).test();
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

