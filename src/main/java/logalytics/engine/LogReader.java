package logalytics.engine;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.Buffer;
import java.util.stream.BaseStream;

public class LogReader {
    private final BufferedReader reader;

    public LogReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Flux<String> producer() {
        return Flux.using(
                this.reader::lines,
                Flux::fromStream,
                BaseStream::close
        );
    }
}
