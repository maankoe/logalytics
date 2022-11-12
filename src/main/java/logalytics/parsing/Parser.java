package logalytics.parsing;

import logalytics.model.Entry;

public interface Parser {
    Entry parse(String line);
}
