package logalytics.engine;

import logalytics.model.Entry;
import logalytics.model.parsing.LogParser;

import java.io.BufferedReader;
import java.io.IOException;

public class LogReader {
    private final BufferedReader reader;
    private final LogParser parser;

    public LogReader(BufferedReader reader, LogParser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public Entry readEntry() throws IOException {
        return parser.parse(this.reader.readLine());
    }
}
