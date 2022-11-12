package logalytics.engine;

import logalytics.model.Entry;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class LogReader {
    private final BufferedReader reader;

    public LogReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Entry readEntry() throws IOException {
        return new Entry(new HashMap<>(), this.reader.readLine());
    }
}
