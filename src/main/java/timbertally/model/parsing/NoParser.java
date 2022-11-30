package timbertally.model.parsing;

import timbertally.model.Entry;

import java.util.HashMap;

public class NoParser implements LogParser {
    @Override
    public Entry parse(String line) {
        return new Entry(new HashMap<>(), line);
    }
}
