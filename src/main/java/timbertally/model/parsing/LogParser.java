package timbertally.model.parsing;

import timbertally.model.Entry;

public interface LogParser {
    Entry parse(String line);
}
