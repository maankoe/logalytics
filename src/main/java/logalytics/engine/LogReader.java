package logalytics.engine;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.util.stream.BaseStream;

public class LogReader {
    private final BufferedReader reader;
    private final String delimiter;

    public LogReader(BufferedReader reader) {
        this.reader = reader;
        this.delimiter = null;
    }

    public LogReader(BufferedReader reader, String delimiter) {
        this.reader = reader;
        this.delimiter = delimiter;
    }

    public Flux<String> producer() {
        return Flux.using(
                        this.reader::lines,
                        Flux::fromStream,
                        BaseStream::close
                )
                .windowUntil(x -> this.delimiter == null || x.startsWith(this.delimiter), true)
                .flatMap(window -> window.reduce((x, y) -> String.join("\n", x, y)));
    }
}
