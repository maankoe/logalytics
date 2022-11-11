package logalytics.engine;

import logalytics.model.Entry;

import java.io.BufferedReader;
import java.io.IOException;

public class LogReader {
    private final BufferedReader reader;

    public LogReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Entry readEntry() throws IOException {
        return new Entry(this.reader.readLine());
    }
}
