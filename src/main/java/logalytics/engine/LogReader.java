package logalytics.engine;

import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.util.function.Predicate;
import java.util.stream.BaseStream;

public class LogReader {

    private static final String NEWLINE = System.lineSeparator();
    private final BufferedReader reader;
    private final Predicate<String> delimiterPredicate;

    public LogReader(BufferedReader reader) {
        this.reader = reader;
        this.delimiterPredicate = x -> true;
    }

    public LogReader(BufferedReader reader, Predicate<String> delimiterPredicate) {
        this.reader = reader;
        this.delimiterPredicate = delimiterPredicate;
    }

    public Flux<String> producer() {
        return Flux.using(
                        this.reader::lines,
                        Flux::fromStream,
                        BaseStream::close
                )
                .windowUntil(this.delimiterPredicate, true)
                .flatMap(window -> window.reduce((x, y) -> String.join(NEWLINE, x, y)));
    }
}
