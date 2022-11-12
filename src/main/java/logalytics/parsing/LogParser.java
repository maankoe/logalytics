package logalytics.parsing;

import logalytics.model.Entry;

public interface LogParser {
    Entry parse(String line);
}
